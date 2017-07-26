package java_spc.netty.nettyio;

import java.io.UnsupportedEncodingException;

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
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32LengthFieldPrepender;
import java_spc.netty.serialize.protobuf.ReqInfo.ReqInfoProto;
import java_spc.netty.serialize.protobuf.ReqInfo.ReqInfoProto.Builder;
import java_spc.netty.serialize.protobuf.RespInfo.RespInfoProto;
/**
 * @author SPC
 * 2017年7月25日
 * 图书订购客户端
 * 对订购消息使用ProtoBuf进行编解码
 */
public class BookClient {
	private final String host;
	private final int port;
	
	public BookClient() {
		this("127.0.0.1", 8080);
	}
	
	public BookClient(String host, int port) {
		this.host=host;
		this.port=port;
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
							ch.pipeline().addLast(new ProtobufVarint32FrameDecoder());
							ch.pipeline().addLast(new ProtobufDecoder(RespInfoProto.getDefaultInstance()));
							ch.pipeline().addLast(new ProtobufVarint32LengthFieldPrepender());
							ch.pipeline().addLast(new ProtobufEncoder());
							ch.pipeline().addLast(new BookClientHandler());
						}
					});
			ChannelFuture future=bootstrap.connect(host, port).sync();
			future.channel().closeFuture().sync();
		} finally {
			group.shutdownGracefully();
		}
	}
	
	public static void main(String[] args) throws Exception {
		BookClient client=new BookClient();
		client.run();
	}
}

class BookClientHandler extends ChannelInboundHandlerAdapter {
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		for(int i=0;i<10;i++) {
			ctx.write(request(i));
		}
		ctx.flush();
	}

	private ReqInfoProto request(int id) throws UnsupportedEncodingException {
		Builder builder=ReqInfoProto.newBuilder();
		builder.setId(id);
		builder.setUname("spc");
		builder.setPname("Netty book");
		builder.setAddress("长沙");
		return builder.build();
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		System.out.println("Client receive response : [\n"+msg+"\n]");
	}
	
	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
		ctx.flush();
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		cause.printStackTrace();
		ctx.close();
	}
}
