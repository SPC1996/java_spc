package java_spc.netty.http.nio_based;

import javax.net.ssl.SSLContext;
import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

/**
 * A Runnable class which sits in a loop accepting SocketChannels,
 * then registers the Channels with the read/write Selector.
 */
public class Acceptor implements Runnable {
    private ServerSocketChannel ssc;
    private Dispatcher d;
    private SSLContext sslContext;

    Acceptor(ServerSocketChannel ssc, Dispatcher d, SSLContext sslContext) {
        this.ssc = ssc;
        this.d = d;
        this.sslContext = sslContext;
    }

    @Override
    public void run() {
        for (; ; ) {
            try {
                SocketChannel sc = ssc.accept();
                ChannelIO cio = sslContext != null ?
                        ChannelIOSecure.getInstance(sc, false) :
                        ChannelIO.getInstance(sc, false);
                RequestHandler rh = new RequestHandler(cio);
                d.register(cio.getSocketChannel(), SelectionKey.OP_READ, rh);
            } catch (IOException e) {
                e.printStackTrace();
                break;
            }
        }
    }
}
