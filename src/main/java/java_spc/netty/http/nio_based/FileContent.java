package java_spc.netty.http.nio_based;

import java_spc.util.Resource;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.URI;
import java.nio.channels.FileChannel;

/**
 * A Content type that provides for transferring files
 */
public class FileContent implements Content {
    private static final File ROOT = new File(Resource.pathToSource("root\\"));
    private File fn;
    private String type = null;

    private FileChannel fc = null;
    private long length = -1;
    private long position = -1;

    FileContent(URI uri) {
        fn = new File(ROOT, uri.getPath().replace('/', File.separatorChar));
    }

    @Override
    public String type() {
        if (type != null)
            return type;
        String nm = fn.getName();
        if (nm.endsWith(".html"))
            type = "text/html; charset=utf-8";
        else if (nm.indexOf('.') < 0 || nm.endsWith(".txt"))
            type = "text/plain; charset=utf-8";
        else
            type = "application/octet-stream";
        return type;
    }

    @Override
    public long length() {
        return length;
    }

    @Override
    public void prepare() throws IOException {
        if (fc == null)
            fc = new RandomAccessFile(fn, "r").getChannel();
        length = fc.size();
        position = 0;
    }

    @Override
    public boolean send(ChannelIO cio) throws IOException {
        if (fc == null)
            throw new IllegalStateException();
        if (position < 0)
            throw new IllegalStateException();
        if (position >= length)
            return false;
        position += cio.transferTo(fc, position, length - position);
        return position < length;
    }

    @Override
    public void release() throws IOException {
        if (fc != null) {
            fc.close();
            fc = null;
        }
    }
}
