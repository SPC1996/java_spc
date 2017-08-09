package java_spc.netty.http.nio_based;

import java.io.IOException;
import java.nio.channels.SelectableChannel;

/**
 * Base class for the Dispatchers.
 * <p>
 * Servers use these to obtain ready status, and then to dispatch jobs.
 */
public interface Dispatcher extends Runnable {
    void register(SelectableChannel ch, int ops, Handler h) throws IOException;
}
