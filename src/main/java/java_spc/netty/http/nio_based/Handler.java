package java_spc.netty.http.nio_based;

import java.io.IOException;
import java.nio.channels.SelectionKey;

/**
 * Base class for the Handlers.
 */
public interface Handler {
    void handle(SelectionKey sk) throws IOException;
}
