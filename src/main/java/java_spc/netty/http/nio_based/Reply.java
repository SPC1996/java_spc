package java_spc.netty.http.nio_based;

import java.io.IOException;
import java.nio.BufferOverflowException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;

/**
 * An object used for sending Content to the requester.
 */
public class Reply implements Sendable {
    /**
     * A helper class which define the HTTP response codes.
     */
    static class Code {
        private int number;
        private String reason;

        static Code OK = new Code(200, "0K");
        static Code BAD_REQUEST = new Code(400, "Bad Request");
        static Code NOT_FOUND = new Code(404, "Not Found");
        static Code METHOD_NOT_ALLOWED = new Code(405, "Method Not Allowed");

        private Code(int number, String reason) {
            this.number = number;
            this.reason = reason;
        }

        @Override
        public String toString() {
            return number + " " + reason;
        }
    }

    private Code code;
    private Content content;
    private boolean headersOnly;

    private static final String CRLF = "\r\n";
    private static final Charset ascii = Charset.forName("US-ASCII");
    private ByteBuffer hbb = null;

    Reply(Code code, Content content) {
        this(code, content, null);
    }

    Reply(Code code, Content content, Request.Action head) {
        this.code = code;
        this.content = content;
        this.headersOnly = head == Request.Action.HEAD;
    }

    private ByteBuffer headers() {
        CharBuffer cb = CharBuffer.allocate(1024);
        for (; ; ) {
            try {
                cb.put("HTTP/1.0 ").put(code.toString()).put(CRLF);
                cb.put("Server: niossl/0.1").put(CRLF);
                cb.put("Content-type: ").put(content.type()).put(CRLF);
                cb.put("Content-length: ").put(Long.toString(content.length())).put(CRLF);
                cb.put(CRLF);
                break;
            } catch (BufferOverflowException e) {
                assert (cb.capacity() < (1 << 16));
                cb = CharBuffer.allocate(cb.capacity() * 2);
            }
        }
        cb.flip();
        return ascii.encode(cb);
    }

    @Override
    public void prepare() throws IOException {
        content.prepare();
        hbb = headers();
    }

    @Override
    public boolean send(ChannelIO cio) throws IOException {
        if (hbb == null)
            throw new IllegalStateException();
        if (hbb.hasRemaining())
            if (cio.write(hbb) <= 0)
                return true;
        if (!headersOnly)
            if (content.send(cio))
                return true;
        return !cio.dataFlush();
    }

    @Override
    public void release() throws IOException {
        content.release();
    }
}
