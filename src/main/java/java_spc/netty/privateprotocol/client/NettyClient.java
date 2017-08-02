package java_spc.netty.privateprotocol.client;

import java.net.InetSocketAddress;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.timeout.ReadTimeoutHandler;
import java_spc.netty.privateprotocol.codec.NettyMessageDecoder;
import java_spc.netty.privateprotocol.codec.NettyMessageEncoder;
import java_spc.netty.privateprotocol.message.NettyConstant;

/**
 * 私有协议栈客户端
 *
 * @author SPC
 * 2017年8月1日
 */
public class NettyClient {
    private ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);

    public void connect(String host, int port) throws Exception {
        try {
            EventLoopGroup group = new NioEventLoopGroup();
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(group)
                    .channel(NioSocketChannel.class)
                    .option(ChannelOption.TCP_NODELAY, true)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast("message-decoder", new NettyMessageDecoder(1024 * 1024, 4, 4, -8, 0));
                            ch.pipeline().addLast("message-encoder", new NettyMessageEncoder());
                            ch.pipeline().addLast("readTimeout-handler", new ReadTimeoutHandler(50));
                            ch.pipeline().addLast("loginAuth-handler", new LoginAuthRequestHandler());
                            ch.pipeline().addLast("heartBeat-handler", new HeartBeatRequestHandler());
                        }
                    });
            ChannelFuture future = bootstrap.connect(new InetSocketAddress(host, port), new InetSocketAddress(NettyConstant.LOCALIP, NettyConstant.LOCAL_PORT)).sync();
            future.channel().closeFuture().sync();
        } finally {
            executor.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        TimeUnit.SECONDS.sleep(5);
                        try {
                            connect(NettyConstant.REMOTEIP, NettyConstant.PORT);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }

    public static void main(String[] args) throws Exception {
        NettyClient client = new NettyClient();
        client.connect(NettyConstant.REMOTEIP, NettyConstant.PORT);
    }
}
