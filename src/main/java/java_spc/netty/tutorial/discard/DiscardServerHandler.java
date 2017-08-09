package java_spc.netty.tutorial.discard;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * 不处理服务端接收的数据
 * 默认丢弃数据
 *
 * @author SPC
 */
public class DiscardServerHandler extends SimpleChannelInboundHandler<Object> {
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }

    @Override
    public void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
        //丢弃接收到的数据
    }
}
