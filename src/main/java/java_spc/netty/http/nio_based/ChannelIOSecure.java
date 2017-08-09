package java_spc.netty.http.nio_based;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLEngine;
import javax.net.ssl.SSLEngineResult;
import javax.net.ssl.SSLException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;

/**
 * A helper class which perform I/O use the SSLEngine API.
 * <p>
 * Each connection has a SocketChannel and SSLEngine that is
 * used through the lifetime of the Channel. We allocate byte
 * buffers for use as the outbound and inbound network buffers.
 * <p>
 * <pre>
 *               Application Data
 *               src      requestBB
 *                |           ^
 *                |     |     |
 *                v     |     |
 *           +----+-----|-----+----+
 *           |          |          |
 *           |       SSL|Engine    |
 *   wrap()  |          |          |  unwrap()
 *           | OUTBOUND | INBOUND  |
 *           |          |          |
 *           +----+-----|-----+----+
 *                |     |     ^
 *                |     |     |
 *                v           |
 *            outNetBB     inNetBB
 *                   Net data
 * <pre/>
 *
 * These buffers handle all of the intermediary data for the SSL connection.
 * To make things easy, we'll require outNetBB be completely flushed before
 * trying to wrap any more data, but we could certainly remove that restriction
 * by using larger buffers.
 *
 * There are many, many ways to handle compute and I/O strategies. What follows
 * is a relatively simple one. The reader is encouraged to develop the strategy
 * that best fits the application.
 *
 * In most of the non-blocking operations in this class, we let the Selector tell
 * us when we're ready to attempt an I/O operation (by the application repeatedly
 * calling our methods). Another option would be to attempt the operation and return
 * from the method when no forward progress can be made.
 *
 * There's lots of room for enhancements and improvement in this example.
 *
 * We are checking for SSL/TLS end-of-stream truncation attacks via sslEngine.closeInbound().
 * When you reach the end of a input stream via a read() returning -1 or an IOException, we
 * call sslEngine.closeInbound() to signal to the sslEngine that no more input will be available.
 * If the peer's close_notify message has not yet been received, this could indicate a truncation
 * attack, in which an attacker is trying to prematurely close the connection. The closeInbound()
 * will throw an exception if this condition were present.
 */
public class ChannelIOSecure extends ChannelIO {
    private SSLEngine sslEngine = null;
    private int appBBSize;
    private int netBBSize;

    /**
     * All I/O goes through these buffers.
     * <p>
     * It might be nice to use a cache of ByteBuffers so we're not alloc/dealloc'ing
     * ByteBuffer's for each new SSLEngine.
     * <p>
     * we use our superclass' requestBB for our application input buffer.
     * Outbound application data is supplied to us by our callers.
     */
    private ByteBuffer intNetBB;
    private ByteBuffer outNetBB;

    /**
     * An empty ByteBuffer for use when one isn't available, say as a source
     * buffer during initial handshake wraps or for close operations.
     */
    private static ByteBuffer hsBB = ByteBuffer.allocate(0);

    /**
     * The FileChannel we're currently transferTo'ing (reading).
     */
    private ByteBuffer fileChannelBB = null;

    /**
     * During our initial handshake, keep track of the next SSLEngine operation
     * that needs to occur:
     * NEED_WRAP/NEED_UNWRAP
     * Once the initial handshake has completed, we can short circuit handshake
     * checks with initialHSComplete.
     */
    private SSLEngineResult.HandshakeStatus initialHSStatus;
    private boolean initialHSComplete;

    /**
     * We have received the shutdown request by our caller, and have closed our
     * outbound side.
     */
    private boolean shutdown = false;

    protected ChannelIOSecure(SocketChannel sc, boolean blocking, SSLContext sslc) throws IOException {
        super(sc, blocking);
        /*
         * We're a server, so no need to use host/port variant.
         *
         * The first call for a server is a NEED_UNWRAP.
         */
        sslEngine = sslc.createSSLEngine();
        sslEngine.setUseClientMode(false);
        initialHSStatus = SSLEngineResult.HandshakeStatus.NEED_UNWRAP;
        initialHSComplete = false;

        /*
         * Create a buffer using the normal expected packet size we'll be getting.
         * This may change, depending on the peer's SSL implementation.
         */
        netBBSize = sslEngine.getSession().getPacketBufferSize();
        intNetBB = ByteBuffer.allocate(netBBSize);
        outNetBB = ByteBuffer.allocate(netBBSize);
        outNetBB.position(0);
        outNetBB.limit(0);
    }

    /**
     * Static factory method for creating a secure ChannelIO object.
     * <p>
     * We need to allocate different sized application data buffers
     * based on whether we're secure or not, We can't determine this
     * until our sslEngine is created.
     */
    static ChannelIOSecure getInstance(SocketChannel sc, boolean blocking, SSLContext sslc) throws IOException {
        ChannelIOSecure cio = new ChannelIOSecure(sc, blocking, sslc);
        cio.appBBSize = cio.sslEngine.getSession().getApplicationBufferSize();
        cio.requestBB = ByteBuffer.allocate(cio.appBBSize);
        return cio;
    }

    /**
     * Calls up to the superclass to adjust the buffer size by an
     * appropriate increment.
     */
    protected void resizeRequestBB() {
        resizeRequestBB(appBBSize);
    }

    /**
     * Adjust the inbound network buffer to an appropriate size.
     */
    private void resizeResponseBB() {
        ByteBuffer bb = ByteBuffer.allocate(netBBSize);
        intNetBB.flip();
        bb.put(intNetBB);
        intNetBB = bb;
    }

    /**
     * Write bb to the SocketChannel.
     * <p>
     * Returns true when the ByteBuffer has no remaining data.
     */
    private boolean tryFlush(ByteBuffer bb) throws IOException {
        super.write(bb);
        return !bb.hasRemaining();
    }

    boolean doHandshake() throws IOException {
        return doHandShake(null);
    }

    /**
     * Perform any handshaking processing.
     * <p>
     * If a SelectionKey is passed, register for selectable operations.
     * <p>
     * In the blocking case, our caller will keep calling us until we
     * finished the handshake. Our reads/writes will block as expected.
     * <p>
     * In the non-blocking case, we just received the selection notification
     * that this channel is ready for whatever the operation is, so give it
     * a try.
     *
     * @return true when handshake is done
     * false while handshake is in progress.
     */
    boolean doHandshake(SelectionKey sk) throws IOException {
        SSLEngineResult result;
        if (initialHSComplete) {
            return true;
        }
        //Flush out the outgoing buffer, if there's anything left in it.
        if (outNetBB.hasRemaining()) {
            if (!tryFlush(outNetBB)) {
                return false;
            }
            //See if we need to switch from write to read mode.
            switch (initialHSStatus) {
                //Is this the last buffer
                case FINISHED:
                    initialHSComplete = true;
                    //Fall-through to register need for a Read.
                case NEED_UNWRAP:
                    if (sk != null) {
                        sk.interestOps(SelectionKey.OP_READ);
                    }
                    break;
            }
            return initialHSComplete;
        }

        switch (initialHSStatus) {
            case NEED_UNWRAP:
                if (sc.read(intNetBB) == -1) {
                    sslEngine.closeInbound();
                    return initialHSComplete;
                }
                needIO:
                while (initialHSStatus == SSLEngineResult.HandshakeStatus.NEED_UNWRAP) {
                    resizeRequestBB();
                    intNetBB.flip();
                    result = sslEngine.unwrap(intNetBB, requestBB);
                    intNetBB.compact();
                    initialHSStatus = result.getHandshakeStatus();
                    switch (result.getStatus()) {
                        case OK:
                            switch (initialHSStatus) {
                                case NOT_HANDSHAKING:
                                    throw new IOException("Not handshaking during initial handshake");
                                case NEED_TASK:
                                    initialHSStatus = doTasks();
                                    break;
                                case FINISHED:
                                    initialHSComplete = true;
                                    break needIO;
                            }
                            break;
                        case BUFFER_UNDERFLOW:
                            netBBSize = sslEngine.getSession().getPacketBufferSize();
                            if (netBBSize > intNetBB.capacity()) {
                                resizeResponseBB();
                            }
                            if (sk != null) {
                                sk.interestOps(SelectionKey.OP_READ);
                            }
                            break needIO;
                        case BUFFER_OVERFLOW:
                            appBBSize = sslEngine.getSession().getApplicationBufferSize();
                            break;
                        default:
                            throw new IOException("Received " + result.getStatus() + " during initial handshaking");
                    }

                }
                if (initialHSStatus != SSLEngineResult.HandshakeStatus.NEED_WRAP) {
                    break;
                }
            case NEED_WRAP:
                outNetBB.clear();
                result = sslEngine.wrap(hsBB, outNetBB);
                outNetBB.flip();
                initialHSStatus = result.getHandshakeStatus();
                switch (result.getStatus()) {
                    case OK:
                        if (initialHSStatus == SSLEngineResult.HandshakeStatus.NEED_TASK) {
                            initialHSStatus = doTasks();
                        }
                        if (sk != null) {
                            sk.interestOps(SelectionKey.OP_WRITE);
                        }
                        break;
                    default:
                        throw new IOException("Received " + result.getStatus() + " during initial handshaking");
                }
                break;
            default:
                throw new RuntimeException("Invalid Handshaking State" + initialHSStatus);
        }
        return initialHSComplete;
    }

    /**
     * Do all the outstanding handshake tasks in the current Thread.
     */
    private SSLEngineResult.HandshakeStatus doTasks() {
        Runnable runnable;
        /*
         * We could run this in a separate thread, but do in the current for now.
         */
        while ((runnable = sslEngine.getDelegatedTask()) != null) {
            runnable.run();
        }
        return sslEngine.getHandshakeStatus();
    }

    /**
     * Read the channel for more information, then unwrap the data we get.
     * <p>
     * If we run out of data, we'll return to our caller (possible using a Selector)
     * to get notification that more is available.
     * <p>
     * Each call to this method will perform at most one underlying read().
     */
    int read() throws IOException {
        SSLEngineResult result;
        if (!initialHSComplete) {
            throw new IllegalStateException();
        }
        int pos = requestBB.position();
        if (sc.read(intNetBB) == -1) {
            sslEngine.closeInbound();
            return -1;
        }
        do {
            resizeRequestBB();
            intNetBB.flip();
            result = sslEngine.unwrap(intNetBB, requestBB);
            intNetBB.compact();
            switch (result.getStatus()) {
                case BUFFER_OVERFLOW:
                    appBBSize = sslEngine.getSession().getApplicationBufferSize();
                    break;
                case BUFFER_UNDERFLOW:
                    netBBSize = sslEngine.getSession().getPacketBufferSize();
                    if (netBBSize > intNetBB.capacity()) {
                        resizeResponseBB();
                        break;
                    }
                case OK:
                    if (result.getHandshakeStatus() == SSLEngineResult.HandshakeStatus.NEED_TASK) {
                        doTasks();
                    }
                    break;
                default:
                    throw new IOException("sslEngine error during data read: " + result.getStatus());
            }
        } while ((intNetBB.position() != 0) && result.getStatus() != SSLEngineResult.Status.BUFFER_UNDERFLOW);
        return requestBB.position() - pos;
    }

    /**
     * Try to write out as much as possible from the src buffer.
     */
    int write(ByteBuffer src) throws IOException {
        if (!initialHSComplete) {
            throw new IllegalStateException();
        }
        return doWrite(src);
    }

    private int doWrite(ByteBuffer src) throws IOException {
        int retValue = 0;
        if (outNetBB.hasRemaining() && !tryFlush(outNetBB)) {
            return retValue;
        }
        //The data buffer is empty, we can reuse the entries buffer.
        outNetBB.clear();
        SSLEngineResult result = sslEngine.wrap(src, outNetBB);
        retValue = result.bytesConsumed();
        outNetBB.flip();
        switch (result.getStatus()) {
            case OK:
                if (result.getHandshakeStatus() == SSLEngineResult.HandshakeStatus.NEED_TASK) {
                    doTasks();
                }
                break;
            default:
                throw new IOException("sslEngine error during data write: " + result.getStatus());
        }
        if (outNetBB.hasRemaining()) {
            tryFlush(outNetBB);
        }
        return retValue;
    }

    /**
     * Perform a FileChannel.TransferTo on the socket channel.
     * <p>
     * We have to copy the data into an intermediary app ByteBuffer first,
     * then send it through the SSLEngine.
     * <p>
     * We return the number of bytes actually read out of the fileChannel.
     * However, the data may actually be stuck in the fileChannelBB or the outNetBB.
     * The caller is responsible for making sure to call dataFlush() before
     * shutting down.
     *
     * @param fc  the channel of a file.
     * @param pos the start position.
     * @param len the length we need to transfer.
     */
    long transferTo(FileChannel fc, long pos, long len) throws IOException {
        if (!initialHSComplete) {
            throw new IllegalStateException();
        }
        if (fileChannelBB == null) {
            fileChannelBB = ByteBuffer.allocate(appBBSize);
            fileChannelBB.limit(0);
        }
        fileChannelBB.compact();
        int fileRead = fc.read(fileChannelBB);
        fileChannelBB.flip();
        doWrite(fileChannelBB);
        return fileRead;
    }

    /**
     * Flush any remaining data.
     *
     * @return true when the fileChannelBB and outNetBB are empty.
     */
    boolean dataFlush() throws IOException {
        boolean fileFlushed = true;
        if (fileChannelBB != null && fileChannelBB.hasRemaining()) {
            doWrite(fileChannelBB);
            fileFlushed = !fileChannelBB.hasRemaining();
        } else if (outNetBB.hasRemaining()) {
            tryFlush(outNetBB);
        }
        return fileFlushed && !outNetBB.hasRemaining();
    }

    /**
     * Begin the shutdown process
     * <p>
     * Close out the SSLEngine if not already done so, then wrap
     * our outgoing close_notify message and try to send again.
     *
     * @return true when we're done passing the shutdown messages.
     */
    boolean shutdown() throws IOException {
        if (!shutdown) {
            sslEngine.closeOutbound();
            shutdown = true;
        }
        if (outNetBB.hasRemaining() && tryFlush(outNetBB)) {
            return false;
        }
        outNetBB.clear();
        SSLEngineResult result = sslEngine.wrap(hsBB, outNetBB);
        if (result.getStatus() != SSLEngineResult.Status.CLOSED) {
            throw new SSLException("Import close state");
        }
        outNetBB.flip();
        if (outNetBB.hasRemaining()) {
            tryFlush(outNetBB);
        }
        return !outNetBB.hasRemaining() && result.getHandshakeStatus() != SSLEngineResult.HandshakeStatus.NEED_WRAP;
    }
}
