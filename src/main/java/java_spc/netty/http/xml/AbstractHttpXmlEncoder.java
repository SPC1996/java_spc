package java_spc.netty.http.xml;

import java.nio.charset.Charset;

import com.thoughtworks.xstream.XStream;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;

public abstract class AbstractHttpXmlEncoder<T> extends MessageToMessageEncoder<T> {
	private final static String CHARSET_NAME="UTF-8";
	private final static Charset UTF_8=Charset.forName(CHARSET_NAME);
	
	protected ByteBuf encode0(ChannelHandlerContext ctx, Object msg) throws Exception {
		XStream xs=new XStream();
		xs.setMode(XStream.NO_REFERENCES);
		xs.processAnnotations(new Class[] { Order.class, Customer.class, Shipping.class, Address.class });
		String xml=xs.toXML(msg);
		ByteBuf encodeBuf=Unpooled.copiedBuffer(xml, UTF_8);
		return encodeBuf;
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		System.out.println("fail to encode!");
	}
}
