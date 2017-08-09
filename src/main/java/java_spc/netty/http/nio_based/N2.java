package java_spc.netty.http.nio_based;

/**
 * A non-blocking/dual-threaded which performs accept() in one
 * thread, and services requests in a second. Both threads use
 * select().
 */
public class N2 extends Server {
    N2(int port, int backlog, boolean secure) throws Exception {
        super(port, backlog, secure);
    }

    @Override
    void runServer() throws Exception {
        Dispatcher dispatcher = new MultiThreadDispatcher();
        Acceptor acceptor = new Acceptor(ssc, dispatcher, sslContext);
        new Thread(acceptor).start();
        dispatcher.run();
    }
}
