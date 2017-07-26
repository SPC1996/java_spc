package java_spc.netty.nettyio;

import java.util.logging.Logger;
import io.netty.bootstrap.Bootstrap;
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
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;

/**
 * @author SPC
 * 2017年7月24日
 * 支持TCP粘包/拆包的TimeClient
 */
public class NPSTimeClient {
	private String host;
	private int port;
	private class ChildHandler extends ChannelInitializer<SocketChannel> {
		private String type;
		
		public ChildHandler(String type) {
			this.type=type;
		}
		
		@Override
		protected void initChannel(SocketChannel channel) throws Exception {
			switch (type) {
			case "one":
				//利用LineBasedFrameDecoder和StringDecoder解决TCP粘包问题
				channel.pipeline().addLast(new LineBasedFrameDecoder(1024));
				channel.pipeline().addLast(new StringDecoder());
				channel.pipeline().addLast(new CaseOneClientHandler());
				break;
			case "two":
				//使用DelimiterBasedFrameDecoder和StringDecoder解决粘包问题
				ByteBuf delimiter=Unpooled.copiedBuffer("$_".getBytes());
				channel.pipeline().addLast(new DelimiterBasedFrameDecoder(1024, delimiter));
				channel.pipeline().addLast(new StringDecoder());
				channel.pipeline().addLast(new CaseTwoClientHandler());
				break;
			default:
				break;
			}
		}
		
	}
	
	public NPSTimeClient() {
		this("127.0.0.1", 8080);
	}
	
	public NPSTimeClient(String host, int port) {
		this.host=host;
		this.port=port;
	}
	
	public void connect(String host, int port) throws Exception {
		EventLoopGroup group=new NioEventLoopGroup();
		try {
			Bootstrap bootstrap=new Bootstrap();
			bootstrap.group(group)
					 .channel(NioSocketChannel.class)
					 .option(ChannelOption.TCP_NODELAY, true)
					 .handler(new ChildHandler("two"));
			ChannelFuture future=bootstrap.connect(host,port).sync();
			future.channel().closeFuture().sync();
		} finally {
			group.shutdownGracefully();
		}
	}

	public void run() throws Exception {
		connect(host, port);
	}
	
	public static void main(String[] args) throws Exception {
		NPSTimeClient client=new NPSTimeClient();
		client.run();
	}
}

/**
 * @author SPC
 * 2017年7月24日
 * 处理LineBasedFrameDecoder和StringDecoder过滤后的信息
 * 收到的信息为String
 * 流程：msg->LineBasedFrameDecoder->StringDecoder->CaseOneClientHandler
 * LineBasedFrameDecoder:遍历ByteBuf中的可读字节，判断是否有换行符"\n"或"\r\n",
 * 						  如果有，从可读索引到结束位置的字符组成一行，进行下一步处理
 * StringDecoder:将接收到的对象转化为字符串
 */
class CaseOneClientHandler extends ChannelInboundHandlerAdapter {
	private static final Logger logger=Logger.getLogger(CaseOneClientHandler.class.getName());
	private int counter;
	private byte[] req;
	
	public CaseOneClientHandler() {
		req=("QUERY TIME ORDER"+System.getProperty("line.separator")).getBytes();
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		logger.warning("Unexpected exception from downstream : "+cause.getMessage());
		ctx.close();
	}

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		ByteBuf message=null;
		for(int i=0;i<100;i++) {
			message=Unpooled.buffer(req.length);
			message.writeBytes(req);
			ctx.writeAndFlush(message);
		}
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		String body=(String)msg;
		System.out.println("Now is : "+body+" ; the counter is : "+ ++counter);
	}
}

/**
 * @author SPC
 * 2017年7月24日
 * 处理DelimiterBasedFrameDecoder和StringDecoder过滤后的信息
 * 收到的信息为String
 * 流程：msg->DelimiterBasedFrameDecoder->StringDecoder->CaseTwoClientHandler
 * DelimiterBasedFrameDecoder:遍历ByteBuf中的可读字节，判断是否有指定的分隔符,
 * 						  如果有，从可读索引到结束位置的字符组成一行，进行下一步处理
 * StringDecoder:将接收到的对象转化为字符串
 */
class CaseTwoClientHandler extends ChannelInboundHandlerAdapter {
	private static final Logger logger=Logger.getLogger(CaseOneClientHandler.class.getName());
	private int counter;
	private byte[] req;
	
	public CaseTwoClientHandler() {
		req="Hi,SPC.Welcome to Netty.$_".getBytes();
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		logger.warning("Unexpected exception from downstream : "+cause.getMessage());
		ctx.close();
	}

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		ByteBuf message=null;
		for(int i=0;i<10;i++) {
			message=Unpooled.buffer(req.length);
			message.writeBytes(req);
			ctx.writeAndFlush(message);
		}
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		System.out.println("This is : "+ ++counter+" times receive server : ["+ msg+"]");
	}

	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
		ctx.flush();
	}
}