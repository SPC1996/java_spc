package java_spc.netty.privateprotocol.client;

import java.util.concurrent.TimeUnit;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.concurrent.ScheduledFuture;
import java_spc.netty.privateprotocol.message.Header;
import java_spc.netty.privateprotocol.message.MessageType;
import java_spc.netty.privateprotocol.message.NettyMessage;

/**
 * 发送心跳消息，检测链路的可用性
 * 
 * @author SPC
 * 2017年8月1日
 */
public class HeartBeatRequestHandler extends ChannelInboundHandlerAdapter {
	private volatile ScheduledFuture<?> heartBeat;
	private class HeartBeatTask implements Runnable {
		private final ChannelHandlerContext ctx;
		
		public HeartBeatTask(final ChannelHandlerContext ctx) {
			this.ctx=ctx;
		}

		@Override
		public void run() {
			NettyMessage heartBeat=buildHeartBeat();
			System.out.println("Client send heart beat message to server : ---> "+heartBeat);
			ctx.writeAndFlush(heartBeat);
		}

		private NettyMessage buildHeartBeat() {
			NettyMessage message=new NettyMessage();
			Header header=new Header();
			header.setType(MessageType.HEARTBEAT_REQUEST.value());
			message.setHeader(header);
			return message;
		}
	}
	
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		NettyMessage message=(NettyMessage) msg;
		if(message.getHeader()!=null&&message.getHeader().getType()==MessageType.LOGIN_RESPONSE.value()) {
			heartBeat=ctx.executor().scheduleAtFixedRate(new HeartBeatTask(ctx), 0, 5000, TimeUnit.MILLISECONDS);
		} else if (message.getHeader()!=null&&message.getHeader().getType()==MessageType.HEARTBEAT_RESPONSE.value()) {
			System.out.println("Client receive server heart beat message : ---> "+message);
		} else {
			ctx.fireChannelRead(msg);
		}
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		if(heartBeat!=null) {
			heartBeat.cancel(true);
			heartBeat=null;
		}
		ctx.fireExceptionCaught(cause);
	}
	
}
