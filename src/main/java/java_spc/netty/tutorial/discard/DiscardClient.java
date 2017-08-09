package java_spc.netty.tutorial.discard;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.InsecureTrustManagerFactory;

import javax.net.ssl.SSLException;

/**
 * 一直向指定地址发送随机数据
 * 客户端
 *
 * @author SPC
 */
public final class DiscardClient {
    static final boolean SSL=System.getProperty("ssl")!=null;
    static final String HOST=System.getProperty("host", "127.0.0.1");
    static final int PORT=Integer.parseInt(System.getProperty("port", "8080"));
    static final int SIZE=Integer.parseInt(System.getProperty("size", "256"));

    public static void main(String[] args) throws SSLException, InterruptedException {
        //配置SSL
        final SslContext sslCtx;
        if(SSL) {
            sslCtx= SslContextBuilder.forClient().trustManager(InsecureTrustManagerFactory.INSTANCE).build();
        } else {
            sslCtx=null;
        }

        EventLoopGroup group=new NioEventLoopGroup();
        try {
            Bootstrap bootstrap=new Bootstrap();
            bootstrap.group(group)
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline p=ch.pipeline();
                            if(sslCtx!=null) {
                                p.addLast(sslCtx.newHandler(ch.alloc(), HOST, PORT));
                            }
                            p.addLast(new DiscardClientHandler());
                        }
                    });
            //尝试连接服务端
            ChannelFuture future=bootstrap.connect(HOST, PORT).sync();
            //一直等待直到连接关闭
            future.channel().closeFuture().sync();
        } finally {
            group.shutdownGracefully();
        }

    }
}
