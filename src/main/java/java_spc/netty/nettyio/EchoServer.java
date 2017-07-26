package java_spc.netty.nettyio;

import java.util.List;

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
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import java_spc.netty.serialize.base.UserInfo;
import java_spc.netty.serialize.encode_decode.MsgpackDecoder;
import java_spc.netty.serialize.encode_decode.MsgpackEncoder;

/**
 * @author SPC
 * 2017年7月24日
 * Netty原生的Echo程序
 * 服务端
 * 测试编解码
 * 通过MessagePack实现编解码
 */
public class EchoServer {
	private int port;
	
	public EchoServer() {
		this(8080);
	}
	
	public EchoServer(int port) {
		this.port=port;
	}
	
	public void run() throws Exception {
		EventLoopGroup bossGroup=new NioEventLoopGroup();
		EventLoopGroup workerGroup=new NioEventLoopGroup();
		try {
			ServerBootstrap bootstrap=new ServerBootstrap();
			bootstrap.group(bossGroup, workerGroup)
					 .channel(NioServerSocketChannel.class)
					 .option(ChannelOption.SO_BACKLOG, 1024)
					 .childHandler(new ChannelInitializer<SocketChannel>() {
						@Override
						protected void initChannel(SocketChannel ch) throws Exception {
							ch.pipeline().addLast("frame decoder",new LengthFieldBasedFrameDecoder(65535, 0, 2, 0, 2));
							ch.pipeline().addLast("msgPack decoder",new MsgpackDecoder());
							ch.pipeline().addLast("frame encoder",new LengthFieldPrepender(2));
							ch.pipeline().addLast("msgPack encoder",new MsgpackEncoder());
							ch.pipeline().addLast(new EchoServerHandler());
						}
					});
			ChannelFuture future=bootstrap.bind(port).sync();
			future.channel().closeFuture().sync();
		} finally {
			bossGroup.shutdownGracefully();
			workerGroup.shutdownGracefully();
		}
	}
	
	public static void main(String[] args) throws Exception {
		EchoServer server=new EchoServer();
		server.run();
	}
}

class EchoServerHandler extends ChannelInboundHandlerAdapter {

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		ctx.close();
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		@SuppressWarnings("unchecked")
		List<UserInfo> infos=(List<UserInfo>) msg;
		System.out.println("Server receive the msgPack message : "+infos);
		ctx.writeAndFlush(msg);
	}
	
}