package java_spc.netty.http.nio_based;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;

/**
 * A helper class for properly sizing inbound byte buffers
 * and redirecting I/O calls to the proper SocketChannel call.
 * <p>
 * Many of these calls may seem unnecessary until you consider
 * that they are placeholders for the secure variant, which is much
 * more involved. See ChannelIOSecure for more information.
 */
public class ChannelIO {
    protected SocketChannel sc;

    /**
     * All of the inbound request data lives here until we determine
     * that we've read everything, then we pass that data back to the
     * caller.
     */
    protected ByteBuffer requestBB;
    private static int requestBBSize = 4096;

    protected ChannelIO(SocketChannel sc, boolean blocking) throws IOException {
        this.sc = sc;
        sc.configureBlocking(blocking);
    }

    static ChannelIO getInstance(SocketChannel sc, boolean blocking) throws IOException {
        ChannelIO cio = new ChannelIO(sc, blocking);
        cio.requestBB = ByteBuffer.allocate(requestBBSize);
        return cio;
    }

    SocketChannel getSocketChannel() {
        return sc;
    }

    /**
     * Return a ByteBuffer with "remaining" space to work.
     * If you have to reallocate the ByteBuffer, copy the existing
     * info into the new buffer
     *
     * @param remaining the remaining space of ByteBuffer that we need.
     */
    protected void resizeRequestBB(int remaining) {
        if (requestBB.remaining() < remaining) {
            ByteBuffer bb = ByteBuffer.allocate(requestBB.capacity() * 2);
            requestBB.flip();
            bb.put(requestBB);
            requestBB = bb;
        }
    }

    /**
     * Perform any handshaking processing.
     * <p>
     * This variant is for Servers without SelectionKeys.
     *
     * @return return true when we're done with handshaking.
     */
    boolean doHandShake() throws IOException {
        return true;
    }

    /**
     * Perform any handshaking processing.
     * <p>
     * This variant is for Servers with SelectionKeys, so that
     * we can register for selectable operations.
     *
     * @param sk SelectionKey
     * @return return true when we're done with handshaking.
     */
    boolean doHandShake(SelectionKey sk) throws IOException {
        return true;
    }

    /**
     * Resize (if necessary) the inbound data buffer,
     * and then read more data into the read buffer.
     */
    int read() throws IOException {
        /*
         * Allocate more space if less than 5% remains.
         */
        resizeRequestBB(requestBBSize / 20);
        return sc.read(requestBB);
    }

    /**
     * All data has been read, pass back the request into one buffer.
     */
    ByteBuffer getReadBuf() {
        return requestBB;
    }

    /**
     * Write the src buffer into the socket channel.
     *
     * @param src the buffer where data saved.
     * @return the byte size which write into.
     */
    int write(ByteBuffer src) throws IOException {
        return sc.write(src);
    }

    /**
     * Perform a FileChannel.TransferTo on the socket channel.
     *
     * @param fc  the channel of a file.
     * @param pos the start position.
     * @param len the length we need to transfer.
     * @return the length we actually transfer.
     */
    long transferTo(FileChannel fc, long pos, long len) throws IOException {
        return fc.transferTo(pos, len, sc);
    }

    /**
     * Flush any outstanding data to the net work if possible.
     * <p>
     * This isn't really necessary for the insecure variant, but needed
     * for the secure one where intermediate buffering must take place.
     *
     * @return Return true if successful.
     */
    boolean dataFlush() throws IOException {
        return true;
    }

    /**
     * Start any connection shutdown processing.
     * <p>
     * This isn't really necessary for the insecure variant, but needed
     * for the secure one where intermediate buffering must take place.
     *
     * @return Return true if successful, and the data has been flushed.
     */
    boolean shutdown() throws IOException {
        return true;
    }

    /**
     * Close the underlying connection.
     */
    void close() throws IOException {
        sc.close();
    }
}
