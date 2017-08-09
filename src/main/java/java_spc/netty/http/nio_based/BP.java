package java_spc.netty.http.nio_based;

import java.nio.channels.SocketChannel;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * A multi-threaded server which creates a pool of threads
 * for use by the server. The Thread pool decides how to schedule
 * those threads.
 */
public class BP extends Server {
    private static final int PORT_MULTIPLE = 4;

    BP(int port, int backlog, boolean secure) throws Exception {
        super(port, backlog, secure);
    }

    @Override
    void runServer() throws Exception {
        ExecutorService es = Executors.newFixedThreadPool(
                Runtime.getRuntime().availableProcessors() * PORT_MULTIPLE);
        for (; ; ) {
            SocketChannel sc = ssc.accept();
            ChannelIO cio = sslContext != null ?
                    ChannelIOSecure.getInstance(sc, true, sslContext) :
                    ChannelIO.getInstance(sc, true);
            RequestServicer svc = new RequestServicer(cio);
            es.execute(svc);
        }
    }
}
