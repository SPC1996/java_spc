package java_spc.netty.http.nio_based;

import java.nio.channels.SocketChannel;

/**
 * A Blocking/Multi-threaded Server which creates a new thread for
 * each connection. This is not efficient for large numbers of connections.
 */
public class BN extends Server {
    BN(int port, int backlog, boolean secure) throws Exception {
        super(port, backlog, secure);
    }

    @Override
    void runServer() throws Exception {
        for (; ; ) {
            SocketChannel sc = ssc.accept();
            ChannelIO cio = sslContext != null ?
                    ChannelIOSecure.getInstance(sc, true, sslContext) :
                    ChannelIO.getInstance(sc, true);
            RequestServicer svc = new RequestServicer(cio);
            Thread th = new Thread(svc);
            th.start();
        }
    }
}
