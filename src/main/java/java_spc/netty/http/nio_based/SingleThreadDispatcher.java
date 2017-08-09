package java_spc.netty.http.nio_based;

import java.io.IOException;
import java.nio.channels.SelectableChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.util.Iterator;

/**
 * A single-threaded dispatcher.
 * <p>
 * When a SelectionKey is ready, it dispatch the job in this
 * thread.
 */
public class SingleThreadDispatcher implements Dispatcher {
    private Selector selector;

    SingleThreadDispatcher() throws IOException {
        selector = Selector.open();
    }

    @Override
    public void register(SelectableChannel ch, int ops, Handler h) throws IOException {
        ch.register(selector, ops, h);
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
    }
}
