package java_spc.netty.http.xml;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpRequestEncoder;
import io.netty.handler.codec.http.HttpResponseDecoder;

/**
 * @author SPC
 * 2017年7月27日
 * Http Xml 协议栈
 * 客户端
 */
public class HttpXmlClient {
    private final String host;
    private final int port;

    public HttpXmlClient() {
        this("127.0.0.1", 8080);
    }

    public HttpXmlClient(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public void run() throws Exception {
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(group)
                    .channel(NioSocketChannel.class)
                    .option(ChannelOption.TCP_NODELAY, true)
                    .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 3000)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            //in顺序执行,out逆序执行
                            ch.pipeline().addLast("http-decoder", new HttpResponseDecoder());            //in
                            ch.pipeline().addLast("http-aggregator", new HttpObjectAggregator(65536));   //in
                            ch.pipeline().addLast("xml-decoder", new HttpXmlResponseDecoder(true));      //in
                            ch.pipeline().addLast("http-encoder", new HttpRequestEncoder());             //out
                            ch.pipeline().addLast("xml-encoder", new HttpXmlRequestEncoder());           //out
                            ch.pipeline().addLast("xml-client-handler", new HttpXmlClientHandler());     //in
                        }
                    });
            ChannelFuture future = bootstrap.connect(host, port).sync();
            future.channel().closeFuture().sync();
        } finally {
            group.shutdownGracefully();
        }
    }

    public static void main(String[] args) throws Exception {
        HttpXmlClient client = new HttpXmlClient();
        client.run();
    }
}

class HttpXmlClientHandler extends SimpleChannelInboundHandler<HttpXmlResponse> {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        HttpXmlRequest request = new HttpXmlRequest(null, Order.generate(23333));
        ctx.writeAndFlush(request);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }

    @Override
    public void channelRead0(ChannelHandlerContext ctx, HttpXmlResponse msg) throws Exception {
        System.out.println("The client receive msg http header : " + msg.getResponse().headers().names());
        System.out.println("The client receive msg http body : " + msg.getBody());
    }

}