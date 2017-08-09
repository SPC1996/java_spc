package java_spc.netty.http.nio_based;

import javax.net.ssl.SSLContext;
import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

/**
 * A single threaded handler that performs accepts SocketChannels and
 * registers the Channels with the read/write Selector.
 */
public class AcceptHandler implements Handler {
    private ServerSocketChannel channel;
    private Dispatcher dsp;
    private SSLContext sslContext;

    AcceptHandler(ServerSocketChannel channel, Dispatcher dsp, SSLContext sslContext) {
        this.channel = channel;
        this.dsp = dsp;
        this.sslContext = sslContext;
    }

    @Override
    public void handle(SelectionKey sk) throws IOException {
        if (!sk.isAcceptable()) return;
        SocketChannel sc = channel.accept();
        if (sc == null) return;
        ChannelIO cio = sslContext != null ?
                ChannelIOSecure.getInstance(sc, false, sslContext) :
                ChannelIO.getInstance(sc, false);
        RequestHandler rh = new RequestHandler(cio);
        dsp.register(cio.getSocketChannel(), SelectionKey.OP_READ, rh);
    }
}
