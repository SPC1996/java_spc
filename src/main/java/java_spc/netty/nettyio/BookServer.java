package java_spc.netty.nettyio;

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
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32LengthFieldPrepender;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import java_spc.netty.serialize.protobuf.ReqInfo.ReqInfoProto;
import java_spc.netty.serialize.protobuf.RespInfo.RespInfoProto;
import java_spc.netty.serialize.protobuf.RespInfo.RespInfoProto.Builder;

/**
 * @author SPC
 * 2017年7月25日
 * 图书订购服务端
 * 对订购消息使用ProtoBuf进行编解码
 */
public class BookServer {
	private int port;
	
	public BookServer() {
		this(8080);
	}
	
	public BookServer(int port) {
		this.port=port;
	}
	
	public void run() {
		EventLoopGroup bossGroup=new NioEventLoopGroup();
		EventLoopGroup workerGroup=new NioEventLoopGroup();
		try {
			ServerBootstrap bootstrap=new ServerBootstrap();
			bootstrap.group(bossGroup, workerGroup)
					 .channel(NioServerSocketChannel.class)
					 .option(ChannelOption.SO_BACKLOG, 100)
					 .handler(new LoggingHandler(LogLevel.INFO))
					 .childHandler(new ChannelInitializer<SocketChannel>() {
						@Override
						protected void initChannel(SocketChannel ch) throws Exception {
							ch.pipeline().addLast(new ProtobufVarint32FrameDecoder());
							ch.pipeline().addLast(new ProtobufDecoder(ReqInfoProto.getDefaultInstance()));
							ch.pipeline().addLast(new ProtobufVarint32LengthFieldPrepender());
							ch.pipeline().addLast(new ProtobufEncoder());
							ch.pipeline().addLast(new BookServerHandler());
						}
					});
			ChannelFuture future=bootstrap.bind(port).sync();
			future.channel().closeFuture().sync();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			bossGroup.shutdownGracefully();
			workerGroup.shutdownGracefully();
		}
	}
	
	public static void main(String[] args) {
		BookServer server=new BookServer();
		server.run();
	}
}

class BookServerHandler extends ChannelInboundHandlerAdapter {

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		ReqInfoProto req=(ReqInfoProto) msg;
		if("spc".equalsIgnoreCase(req.getUname())) {
			System.out.println("Server accept request : [\n"+req.toString()+"\n]");
			ctx.writeAndFlush(response(req.getId()));
		}
	}

	private RespInfoProto response(int id) {
		Builder builder=RespInfoProto.newBuilder();
		builder.setId(id);
		builder.setCode(0);
		builder.setDesc("Book order succeed, 3 days later, sent to your address");
		return builder.build();
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		cause.printStackTrace();
		ctx.close();
	}
	
}