package java_spc.netty.nettyio;

import org.jboss.marshalling.MarshallerFactory;
import org.jboss.marshalling.Marshalling;
import org.jboss.marshalling.MarshallingConfiguration;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.marshalling.DefaultMarshallerProvider;
import io.netty.handler.codec.marshalling.DefaultUnmarshallerProvider;
import io.netty.handler.codec.marshalling.MarshallerProvider;
import io.netty.handler.codec.marshalling.MarshallingDecoder;
import io.netty.handler.codec.marshalling.MarshallingEncoder;
import io.netty.handler.codec.marshalling.UnmarshallerProvider;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import java_spc.netty.serialize.marshalling.RequestMsg;
import java_spc.netty.serialize.marshalling.ResponseMsg;

/**
 * @author SPC
 * 2017年7月25日
 * 计算两个数加减乘除结果
 * 服务端
 * 使用Marshalling进行编解码
 */
public class CalculateServer {
    private int port;

    public CalculateServer() {
        this(8080);
    }

    public CalculateServer(int port) {
        this.port = port;
    }

    public void run() throws Exception {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG, 100)
                    .handler(new LoggingHandler(LogLevel.INFO))
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast(MarshallingCodeCFactory.buildMarshallingDecoder());
                            ch.pipeline().addLast(MarshallingCodeCFactory.buildMarshallingEncoder());
                            ch.pipeline().addLast(new CalculateServerHandler());
                        }
                    });
            ChannelFuture future = bootstrap.bind(port).sync();
            future.channel().closeFuture().sync();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

    public static void main(String[] args) throws Exception {
        CalculateServer server = new CalculateServer();
        server.run();
    }
}

class CalculateServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        RequestMsg req = (RequestMsg) msg;
        System.out.println("Server receive request msg : " + msg);
        ResponseMsg resp = new ResponseMsg();
        if (req.getOp() == '/' && req.getNum2() == 0) {
            resp.setStatus("failed");
            resp.setRes(req + " = null");
        } else {
            resp.setStatus("succeed");
            switch (req.getOp()) {
                case '+':
                    resp.setRes(req + " = " + (req.getNum1() + req.getNum2()));
                    break;
                case '-':
                    resp.setRes(req + " = " + (req.getNum1() - req.getNum2()));
                    break;
                case '*':
                    resp.setRes(req + " = " + (req.getNum1() * req.getNum2()));
                    break;
                case '/':
                    resp.setRes(req + " = " + (req.getNum1() / req.getNum2()));
                    break;
                default:
                    resp.setStatus("failed");
                    resp.setRes(req + " = null");
                    break;
            }
        }
        ctx.writeAndFlush(resp);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }

}

/**
 * @author SPC
 * 2017年7月25日
 * 负责产生Marshalling编解码器的工厂
 */
final class MarshallingCodeCFactory {
    public static MarshallingDecoder buildMarshallingDecoder() {
        final MarshallerFactory factory = Marshalling.getProvidedMarshallerFactory("serial");
        final MarshallingConfiguration configuration = new MarshallingConfiguration();
        configuration.setVersion(5);
        UnmarshallerProvider provider = new DefaultUnmarshallerProvider(factory, configuration);
        MarshallingDecoder decoder = new MarshallingDecoder(provider, 1024);
        return decoder;
    }

    public static MarshallingEncoder buildMarshallingEncoder() {
        final MarshallerFactory factory = Marshalling.getProvidedMarshallerFactory("serial");
        final MarshallingConfiguration configuration = new MarshallingConfiguration();
        configuration.setVersion(5);
        MarshallerProvider provider = new DefaultMarshallerProvider(factory, configuration);
        MarshallingEncoder encoder = new MarshallingEncoder(provider);
        return encoder;
    }
}
