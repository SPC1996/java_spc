package java_spc.netty.http.nio_based;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;

/**
 * A Content type that provides for transferring Strings.
 */
public class StringContent implements Content {
    private static final Charset ascii = Charset.forName("UTF-8");
    private String type;
    private String content;
    private ByteBuffer bb = null;

    StringContent(CharSequence c, String t) {
        content = c.toString();
        if (!content.endsWith("\n"))
            content += "\n";
        type = t + "; charset=utf-8";
    }

    StringContent(CharSequence c) {
        this(c, "text/plain");
    }

    StringContent(Exception e) {
        StringWriter sw = new StringWriter();
        e.printStackTrace(new PrintWriter(sw));
        type = "text/plain; charset=utf-8";
        content = sw.toString();
    }

    private void encode() {
        if (bb == null)
            bb = ascii.encode(CharBuffer.wrap(content));
    }

    @Override
    public String type() {
        return type;
    }

    @Override
    public long length() {
        encode();
        return bb.remaining();
    }

    @Override
    public void prepare() throws IOException {
        encode();
        bb.rewind();
    }

    @Override
    public boolean send(ChannelIO cio) throws IOException {
        if (bb == null)
            throw new IllegalStateException();
        cio.write(bb);
        return bb.hasRemaining();
    }

    @Override
    public void release() throws IOException {

    }
}
