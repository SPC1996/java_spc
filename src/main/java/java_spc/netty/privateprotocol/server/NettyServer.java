package java_spc.netty.privateprotocol.server;

import java.io.IOException;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.ReadTimeoutHandler;
import java_spc.netty.privateprotocol.codec.NettyMessageDecoder;
import java_spc.netty.privateprotocol.codec.NettyMessageEncoder;
import java_spc.netty.privateprotocol.message.NettyConstant;

/**
 * 私有协议栈服务端
 *
 * @author SPC 2017年8月1日
 */
public class NettyServer {
    public void bind() throws Exception {
        // 配置服务端的NIO线程组
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        ServerBootstrap bootstrap = new ServerBootstrap();
        bootstrap.group(bossGroup, workerGroup)
                .channel(NioServerSocketChannel.class)
                .option(ChannelOption.SO_BACKLOG, 100)
                .handler(new LoggingHandler(LogLevel.INFO))
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    public void initChannel(SocketChannel ch) throws IOException {
                        ch.pipeline().addLast("message-decoder", new NettyMessageDecoder(1024 * 1024, 4, 4, -8, 0));
                        ch.pipeline().addLast("message-encoder", new NettyMessageEncoder());
                        ch.pipeline().addLast("readTimeout-handler", new ReadTimeoutHandler(50));
                        ch.pipeline().addLast("loginAuth-handler", new LoginAuthResponseHandler());
                        ch.pipeline().addLast("heartBeat-handler", new HeartBeatResponseHandler());
                    }
                });

        // 绑定端口，同步等待成功
        bootstrap.bind(NettyConstant.REMOTEIP, NettyConstant.PORT).sync();
        System.out.println("Netty server start ok : " + (NettyConstant.REMOTEIP + " : " + NettyConstant.PORT));
    }

    public static void main(String[] args) throws Exception {
        NettyServer server = new NettyServer();
        server.bind();
    }

}
