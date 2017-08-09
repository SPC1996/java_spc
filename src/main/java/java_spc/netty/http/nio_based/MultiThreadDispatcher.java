package java_spc.netty.http.nio_based;

import java.io.IOException;
import java.nio.channels.SelectableChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.util.Iterator;

/**
 * A Multi-threaded dispatcher
 * <p>
 * In this example, one thread does accepts, and the second
 * does read/write.
 */
public class MultiThreadDispatcher implements Dispatcher {
    private Selector selector;
    private final Object gate = new Object();

    MultiThreadDispatcher() throws IOException {
        selector = Selector.open();
    }

    @Override
    public void register(SelectableChannel ch, int ops, Handler h) throws IOException {
        synchronized (gate) {
            selector.wakeup();
            ch.register(selector, ops, h);
        }
    }

    @Override
    public void run() {
        for (; ; ) {
            try {
                dispatch();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void dispatch() throws IOException {
        selector.select();
        for (Iterator<SelectionKey> i = selector.selectedKeys().iterator(); i.hasNext(); ) {
            SelectionKey sk = i.next();
            i.remove();
            Handler h = (Handler) sk.attachment();
            h.handle(sk);
        }
        synchronized (gate) {
        }
    }
}
