package java_spc.netty.http.nio_based;

import java.nio.channels.SelectionKey;

/**
 * A non-blocking/single-threaded server. All accept() and
 * read()/write() operations are performed by a single thread,
 * but only after being selected for those operations by a
 * Selector.
 */
public class N1 extends Server {
    N1(int port, int backlog, boolean secure) throws Exception {
        super(port, backlog, secure);
        ssc.configureBlocking(false);
    }

    @Override
    void runServer() throws Exception {
        Dispatcher dispatcher = new SingleThreadDispatcher();
        dispatcher.register(ssc, SelectionKey.OP_ACCEPT, new AcceptHandler(ssc, dispatcher, sslContext));
        dispatcher.run();
    }
}
