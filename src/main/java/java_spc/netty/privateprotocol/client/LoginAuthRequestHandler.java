package java_spc.netty.privateprotocol.client;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import java_spc.netty.privateprotocol.message.Header;
import java_spc.netty.privateprotocol.message.MessageType;
import java_spc.netty.privateprotocol.message.NettyMessage;

/**
 * 在通道激活时发送握手请求
 * 
 * @author SPC
 * 2017年8月1日
 */
public class LoginAuthRequestHandler extends ChannelInboundHandlerAdapter{

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		ctx.writeAndFlush(buildLoginRequest());
	}
	
	private NettyMessage buildLoginRequest() {
		NettyMessage message = new NettyMessage();
		Header header = new Header();
		header.setType(MessageType.LOGIN_REQUEST.value());
		message.setHeader(header);
		return message;
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		NettyMessage message=(NettyMessage) msg;
		if(message.getHeader()!=null&&message.getHeader().getType()==MessageType.LOGIN_RESPONSE.value()) {
			byte loginResult=(byte) message.getBody();
			if(loginResult!=(byte)0) {
				ctx.close();
			} else {
				System.out.println("Login is ok : "+message);
				ctx.fireChannelRead(msg);
			}
		} else {
			System.out.println(msg);
			ctx.fireChannelRead(msg);
		}
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		ctx.fireExceptionCaught(cause);
	}

}
