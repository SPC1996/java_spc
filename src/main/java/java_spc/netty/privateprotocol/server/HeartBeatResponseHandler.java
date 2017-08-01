package java_spc.netty.privateprotocol.server;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import java_spc.netty.privateprotocol.message.Header;
import java_spc.netty.privateprotocol.message.MessageType;
import java_spc.netty.privateprotocol.message.NettyMessage;

/**
 * 接收心跳请求信息后，构造心跳应答信息返回
 * @author SPC
 * 2017年8月1日
 */
public class HeartBeatResponseHandler extends ChannelInboundHandlerAdapter {

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		NettyMessage message=(NettyMessage) msg;
		if(message.getHeader()!=null&&message.getHeader().getType()==MessageType.HEARTBEAT_REQUEST.value()) {
			System.out.println("Receive client heart beat message : ---> "+message);
			NettyMessage heartBeat=buildHeartBeat();
			System.out.println("Send heart beat response message to client : ---> "+heartBeat);
			ctx.writeAndFlush(heartBeat);
		} else {
			ctx.fireChannelRead(msg);
		}
	}

	private NettyMessage buildHeartBeat() {
		NettyMessage message=new NettyMessage();
		Header header=new Header();
		header.setType(MessageType.HEARTBEAT_RESPONSE.value());
		message.setHeader(header);
		return message;
	}

}
