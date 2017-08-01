package java_spc.netty.privateprotocol.codec;

import java.io.IOException;
import java.util.Map;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import java_spc.netty.privateprotocol.message.Header;
import java_spc.netty.privateprotocol.message.NettyMessage;

/**
 * 用于NettyMessage编码
 * 
 * @author SPC
 * @see NettyMessage
 * 2017年7月31日
 */
public final class NettyMessageEncoder extends MessageToByteEncoder<NettyMessage> {
	private MarshallingEncoder encoder;
	
	public NettyMessageEncoder() throws IOException {
		this.encoder=new MarshallingEncoder();
	}
	
	@Override
	public void encode(ChannelHandlerContext ctx, NettyMessage msg, ByteBuf sendBuf) throws Exception {
		if(msg==null||msg.getHeader()==null) {
			throw new Exception("The code message is null!");
		}
		
		Header header=msg.getHeader();
		sendBuf.writeInt(header.getCrcCode());
		sendBuf.writeInt(header.getLength());
		sendBuf.writeLong(header.getSessionId());
		sendBuf.writeByte(header.getType());
		sendBuf.writeByte(header.getPriority());
		sendBuf.writeInt(header.getAttachment().size());
		
		String key=null;
		byte[] keyArray=null;
		Object value=null;
		for(Map.Entry<String, Object> param : msg.getHeader().getAttachment().entrySet()) {
			key=param.getKey();
			keyArray=key.getBytes("utf-8");
			sendBuf.writeInt(keyArray.length);
			sendBuf.writeBytes(keyArray);
			value=param.getValue();
			encoder.encode(value, sendBuf);
		}
		key=null;
		keyArray=null;
		value=null;
		if(msg.getBody()!=null) {
			encoder.encode(msg.getBody(), sendBuf);
			sendBuf.setInt(4, sendBuf.readableBytes());
		} else {
			sendBuf.writeInt(0);
			sendBuf.setInt(4, sendBuf.readableBytes());
		}
	}
}
