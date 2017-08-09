package java_spc.netty.http.nio_based;

import java.io.IOException;

/**
 * Method definitions used for preparing, sending, and release
 * content.
 */
public interface Sendable {
    void prepare() throws IOException;

    boolean send(ChannelIO cio) throws IOException;

    void release() throws IOException;
}
