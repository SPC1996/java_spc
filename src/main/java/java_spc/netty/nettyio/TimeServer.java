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

/**
 * @author SPC
 * 2017年7月21日
 * 使用Netty获取时间
 * 服务端
 */
public class TimeServer {
	private int port;
	
	private class ChildChannelHandler extends ChannelInitializer<SocketChannel>{

		@Override
		protected void initChannel(SocketChannel channel) throws Exception {
			//使用普通的handler
//			channel.pipeline().addLast(new ServerHandler());
			//使用会发生TCP粘包的Handler
			channel.pipeline().addLast(new PackageSplicingServerHandler());
		}
		
	}
	
	public TimeServer() {
		this.port=8080;
	}
	
	public TimeServer(int port){
		this.port=port;
	}
	
	public void bind(int port) throws Exception{
		//配置服务端的NIO线程组
		EventLoopGroup bossGroup=new NioEventLoopGroup();
		EventLoopGroup workerGroup=new NioEventLoopGroup();
		try {
			ServerBootstrap bootstrap=new ServerBootstrap();
			bootstrap.group(bossGroup, workerGroup)
					 .channel(NioServerSocketChannel.class)
					 .option(ChannelOption.SO_BACKLOG, 1024)
					 .childHandler(new ChildChannelHandler());
			ChannelFuture future=bootstrap.bind(port).sync();
			future.channel().closeFuture().sync();
		} finally {
			bossGroup.shutdownGracefully();
			workerGroup.shutdownGracefully();
		}
	}
	
	public void run() throws Exception{
		bind(port);
	}
	
	public static void main(String[] args) throws Exception{
		new TimeServer().run();
	}
}

/**
 * @author SPC
 * 2017年7月21日
 * 普通处理请求信息的Handler类
 */
class ServerHandler extends ChannelInboundHandlerAdapter{

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		ByteBuf buf=(ByteBuf) msg;
		byte[] req=new byte[buf.readableBytes()];
		buf.readBytes(req);
		String body=new String(req, "utf-8");
		System.out.println("The time server receive order : "+body);
		String currentTime="QUERY TIME ORDER".equalsIgnoreCase(body)?new Date(System.currentTimeMillis()).toString():"BAD ORDER";
		ByteBuf resp=Unpooled.copiedBuffer(currentTime.getBytes());
		ctx.write(resp);
	}

	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
		ctx.flush();
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		ctx.close();
	}
	
}

/**
 * @author SPC
 * 2017年7月21日
 * 会发生TCP粘包的Handler
 */
class PackageSplicingServerHandler extends ChannelInboundHandlerAdapter{
	private int counter;
	
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		ctx.close();
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		ByteBuf buf=(ByteBuf)msg;
		byte[] req=new byte[buf.readableBytes()];
		buf.readBytes(req);
		String body=new String(req,"utf-8").substring(0,req.length-System.getProperty("line.separator").length());
		System.out.println("The time server receive order : "+body+" ; the counter is : "+ ++counter);
		String currentTime="QUERY TIME ORDER".equalsIgnoreCase(body)?new Date(System.currentTimeMillis()).toString():"BAD ORDER";
		currentTime=currentTime+System.getProperty("line.separator");
		ByteBuf resp=Unpooled.copiedBuffer(currentTime.getBytes());
		ctx.writeAndFlush(resp);
	}
	
}