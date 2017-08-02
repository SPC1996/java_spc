package java_spc.netty.nettyio;

import java.util.Date;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.FixedLengthFrameDecoder;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;

/**
 * @author SPC
 * 2017年7月21日
 * 支持TCP粘包/拆包的TimeServer
 */
public class NPSTimeServer {
    private int port;

    private class ChildHandler extends ChannelInitializer<SocketChannel> {
        private String type;

        public ChildHandler(String type) {
            this.type = type;
        }

        @Override
        protected void initChannel(SocketChannel channel) throws Exception {
            switch (type) {
                case "one":
                    //利用LineBasedFrameDecoder和StringDecoder解决TCP粘包问题
                    channel.pipeline().addLast(new LineBasedFrameDecoder(1024));
                    channel.pipeline().addLast(new StringDecoder());
                    channel.pipeline().addLast(new CaseOneHandler());
                    break;
                case "two":
                    //使用DelimiterBasedFrameDecoder和StringDecoder解决粘包问题
                    ByteBuf delimiter = Unpooled.copiedBuffer("$_".getBytes());
                    channel.pipeline().addLast(new DelimiterBasedFrameDecoder(1024, delimiter));
                    channel.pipeline().addLast(new StringDecoder());
                    channel.pipeline().addLast(new CaseTwoHandler());
                    break;
                case "three":
                    //使用FixdLengthFrameDecoder和StringDecoder解决TCP粘包问题
                    channel.pipeline().addLast(new FixedLengthFrameDecoder(20));
                    channel.pipeline().addLast(new StringDecoder());
                    channel.pipeline().addLast(new CaseThreeHandler());
                    break;
                default:
                    break;
            }
        }

    }

    public NPSTimeServer() {
        this(8080);
    }

    public NPSTimeServer(int port) {
        this.port = port;
    }

    public void bind(int port) throws Exception {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG, 1024)
                    .childHandler(new ChildHandler("three"));
            ChannelFuture future = bootstrap.bind(port).sync();
            future.channel().closeFuture().sync();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

    public void run() throws Exception {
        bind(port);
    }

    public static void main(String[] args) throws Exception {
        NPSTimeServer server = new NPSTimeServer();
        server.run();
    }
}

/**
 * @author SPC
 * 2017年7月24日
 * 处理LineBasedFrameDecoder和StringDecoder过滤后的信息
 * 收到的信息为String
 * 流程：msg->LineBasedFrameDecoder->StringDecoder->CaseOneHandler
 * LineBasedFrameDecoder:遍历ByteBuf中的可读字节，判断是否有换行符"\n"或"\r\n",
 * 如果有，从可读索引到结束位置的字符组成一行，进行下一步处理
 * StringDecoder:将接收到的对象转化为字符串
 */
class CaseOneHandler extends ChannelInboundHandlerAdapter {
    private int counter;

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        String body = (String) msg;
        System.out.println("The time server receive order : " + body + " ; the counter is " + ++counter);
        String currentTime = "QUERY TIME ORDER".equalsIgnoreCase(body) ? new Date(System.currentTimeMillis()).toString() : "BAD ORDER";
        currentTime = currentTime + System.getProperty("line.separator");
        ByteBuf resp = Unpooled.copiedBuffer(currentTime.getBytes());
        ctx.writeAndFlush(resp);
    }
}

/**
 * @author SPC
 * 2017年7月24日
 * 处理DelimiterBasedFrameDecoder和StringDecoder过滤后的信息
 * 收到的信息为String
 * 流程：msg->DelimiterBasedFrameDecoder->StringDecoder->CaseTwoHandler
 * DelimiterBasedFrameDecoder:遍历ByteBuf中的可读字节，判断是否有指定的分隔符,
 * 如果有，从可读索引到结束位置的字符组成一行，进行下一步处理
 * StringDecoder:将接收到的对象转化为字符串
 */
class CaseTwoHandler extends ChannelInboundHandlerAdapter {
    private int counter;

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        String body = (String) msg;
        System.out.println("This is " + ++counter + " times receive client : [" + body + "]");
        body += "$_";
        ByteBuf resp = Unpooled.copiedBuffer(body.getBytes());
        ctx.writeAndFlush(resp);
    }
}

/**
 * @author SPC
 * 2017年7月24日
 * 处理FixedLengthFrameDecoder和StringDecoder过滤后的信息
 * 收到的信息为String
 * 流程：msg->FixedLengthFrameDecoder->StringDecoder->CaseThreeHandler
 * DelimiterBasedFrameDecoder:遍历ByteBuf中的可读字节，截取指定长度的字符，进行下一步处理
 * StringDecoder:将接收到的对象转化为字符串
 */
class CaseThreeHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("Receive client :[" + msg + "]");
    }
}