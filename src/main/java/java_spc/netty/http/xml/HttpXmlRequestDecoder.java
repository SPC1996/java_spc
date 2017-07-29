package java_spc.netty.http.xml;

import java.util.List;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpVersion;
import io.netty.util.CharsetUtil;

public class HttpXmlRequestDecoder extends AbstractHttpXmlDecoder<FullHttpRequest>{

	public HttpXmlRequestDecoder() {
		this(false);
	}
	
	public HttpXmlRequestDecoder(boolean isPrint) {
		super(isPrint);
	}

	@Override
	protected void decode(ChannelHandlerContext ctx, FullHttpRequest request, List<Object> out) throws Exception {
		if(!request.decoderResult().isSuccess()) {
			sendError(ctx, HttpResponseStatus.BAD_REQUEST);
			return;
		}
		HttpXmlRequest newRequest=new HttpXmlRequest(request, decode0(ctx, request.content()));
		out.add(newRequest);
	}

	private static void sendError(ChannelHandlerContext ctx, HttpResponseStatus status) {
		FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, status,  
                Unpooled.copiedBuffer("Failure: " + status.toString() + "\r\n", CharsetUtil.UTF_8));  
        response.headers().set(HttpHeaderNames.CONTENT_TYPE, "text/plain; charset=UTF-8");  
        ctx.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE); 
	}
	
}
