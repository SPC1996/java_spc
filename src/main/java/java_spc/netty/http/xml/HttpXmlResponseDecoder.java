package java_spc.netty.http.xml;

import java.util.List;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpResponse;

public class HttpXmlResponseDecoder extends AbstractHttpXmlDecoder<FullHttpResponse> {
	public HttpXmlResponseDecoder() {
		this(false);
	}
	
	public HttpXmlResponseDecoder(boolean isPrint) {
		super(isPrint);
	}
	
	@Override
	protected void decode(ChannelHandlerContext ctx, FullHttpResponse msg, List<Object> out) throws Exception {
		HttpXmlResponse response=new HttpXmlResponse(msg, decode0(ctx, msg.content()));
		out.add(response);
	}
}
