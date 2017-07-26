package java_spc.netty.tutorial;


import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.util.ReferenceCountUtil;

public class DiscardServer{
	private int port;
	
	public DiscardServer(int port){
		this.port=port;
	}
	
	public void run() throws Exception{
		EventLoopGroup bossGroup=new NioEventLoopGroup();
		EventLoopGroup workerGroup=new NioEventLoopGroup();
		try {
			ServerBootstrap b=new ServerBootstrap();
			b.group(bossGroup, workerGroup)
			 .channel(NioServerSocketChannel.class)
			 .childHandler(new ChannelInitializer<SocketChannel>() {

				@Override
				public void initChannel(SocketChannel ch) throws Exception {
					ch.pipeline().addLast(new DiscardServerHandler());
				}
			})
			 .option(ChannelOption.SO_BACKLOG, 128)
			 .childOption(ChannelOption.SO_KEEPALIVE, true);
			
			ChannelFuture f=b.bind(port).sync();
			f.channel().closeFuture().sync();
			 
		} finally {
			workerGroup.shutdownGracefully();
			bossGroup.shutdownGracefully();
		}
	}
	
	public static void main(String[] args) throws Exception{
		int port;
		if(args.length>0){
			port=Integer.parseInt(args[0]);
		} else {
			port=8080;
		}
		new DiscardServer(port).run();
	}
}

class DiscardServerHandler extends ChannelInboundHandlerAdapter{
	public void channelRead(ChannelHandlerContext ctx,Object msg){
		ByteBuf in = (ByteBuf) msg;
	    try {
	        while (in.isReadable()) { 
	        	System.out.println(in.toString(io.netty.util.CharsetUtil.US_ASCII));
	        }
	    } finally {
	        ReferenceCountUtil.release(msg);
	    }
	}
	
	public void exceptionCaught(ChannelHandlerContext ctx,Throwable cause) {
		cause.printStackTrace();
		ctx.close();
	}
}
