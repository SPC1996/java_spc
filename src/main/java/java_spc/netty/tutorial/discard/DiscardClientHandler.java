package java_spc.netty.tutorial.discard;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * 负责向服务端发送数据，并处理服务端返回的数据
 *
 * @author SPC
 */
public class DiscardClientHandler extends SimpleChannelInboundHandler<Object>{
    private ByteBuf content;
    private ChannelHandlerContext ctx;

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        this.ctx=ctx;
        //初始化要发送的信息
        content=ctx.alloc().directBuffer(DiscardClient.SIZE).writeZero(DiscardClient.SIZE);
        //发送信息
        generateTraffic();

    }

    private void generateTraffic() {
        //刷新socket发送端缓冲，一旦刷新，再次生成相同数量的通信量
        ctx.writeAndFlush(content.retainedDuplicate()).addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture future) throws Exception {
                if(future.isSuccess()) {
                    generateTraffic();
                } else {
                    future.cause().printStackTrace();
                    future.channel().close();
                }
            }
        });
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        content.release();
    }

    @Override
    public void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
        //丢弃接收的数据
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        //产生异常时关闭连接
        cause.printStackTrace();
        ctx.close();
    }
}
