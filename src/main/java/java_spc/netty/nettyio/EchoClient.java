package java_spc.netty.nettyio;

import java.util.List;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import java_spc.netty.serialize.base.UserInfo;
import java_spc.netty.serialize.encode_decode.MsgpackDecoder;
import java_spc.netty.serialize.encode_decode.MsgpackEncoder;

/**
 * @author SPC
 * 2017年7月24日
 * Netty原生的Echo程序
 * 客户端
 * 测试编解码
 * 通过MessagePack实现编解码
 */
public class EchoClient {
	private final String host;
	private final int port;
	private final int sendNumber;
	
	public EchoClient() {
		this("127.0.0.1", 8080, 10);
	}
	
	public EchoClient(String host, int port, int sendNumber) {
		this.host=host;
		this.port=port;
		this.sendNumber=sendNumber;
	}
	
	public void run() throws Exception {
		EventLoopGroup group=new NioEventLoopGroup();
		try {
			Bootstrap bootstrap=new Bootstrap();
			bootstrap.group(group)
					 .channel(NioSocketChannel.class)
					 .option(ChannelOption.TCP_NODELAY, true)
					 .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 3000)
					 .handler(new ChannelInitializer<SocketChannel>() {
						@Override
						protected void initChannel(SocketChannel ch) throws Exception {
							ch.pipeline().addLast("frame decoder",new LengthFieldBasedFrameDecoder(65535, 0, 2, 0, 2));
							ch.pipeline().addLast("msgPack decoder",new MsgpackDecoder());
							ch.pipeline().addLast("frame encoder",new LengthFieldPrepender(2));
							ch.pipeline().addLast("msgPack encoder",new MsgpackEncoder());
							ch.pipeline().addLast(new EchoClientHandler(sendNumber));
						}
					});
			ChannelFuture future=bootstrap.connect(host, port).sync();
			future.channel().closeFuture().sync();
		} finally {
			group.shutdownGracefully();
		}
	}
	
	public static void main(String[] args) throws Exception {
		EchoClient client=new EchoClient();
		client.run();
	}
}

class EchoClientHandler extends ChannelInboundHandlerAdapter {
	private final int sendNumber;
	
	public EchoClientHandler(int sendNumber) {
		this.sendNumber=sendNumber;
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		ctx.close();
	}

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		UserInfo[] infos=generateInfos();
		for(UserInfo info:infos) {
			ctx.write(info);
		}
		ctx.flush();
	}

	private UserInfo[] generateInfos() {
		UserInfo[] infos=new UserInfo[sendNumber];
		UserInfo info=null;
		for(int i=0;i<sendNumber;i++) {
			info=new UserInfo();
			info.setUserId(i);
			info.setUserName("ABCDEFG --->"+i);
			infos[i]=info;
		}
		return infos;
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		@SuppressWarnings("unchecked")
		List<UserInfo> infos=(List<UserInfo>) msg;
		System.out.println("Client receive the msgPack message : "+infos);
		ctx.write(msg);
	}

	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
		ctx.flush();
	}
}