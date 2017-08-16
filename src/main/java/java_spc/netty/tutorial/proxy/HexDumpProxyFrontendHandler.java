package java_spc.netty.tutorial.proxy;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;

public class HexDumpProxyFrontendHandler extends ChannelInboundHandlerAdapter {
    private final String remoteHost;
    private final int remotePort;
    private Channel outboundChannel;

    public HexDumpProxyFrontendHandler(String remoteHost, int remotePort) {
        this.remoteHost = remoteHost;
        this.remotePort = remotePort;
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        final Channel inboundChannel = ctx.channel();

        //Start the connection attempt
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(inboundChannel.eventLoop())
                .channel(ctx.channel().getClass())
                .handler(new HexDumpProxyBackendHandler(inboundChannel))
                .option(ChannelOption.AUTO_READ, false);
        ChannelFuture future = bootstrap.connect(remoteHost, remotePort);
        outboundChannel = future.channel();
        future.addListener((ChannelFutureListener) f -> {
            if (f.isSuccess()) {
                //connection complete start to read first data
                inboundChannel.read();
            } else {
                //close the connection if the connection attempt has failed
                inboundChannel.close();
            }
        });
    }

    @Override
    public void channelRead(final ChannelHandlerContext ctx, Object msg) throws Exception {
        if (outboundChannel.isActive()) {
            outboundChannel.writeAndFlush(msg).addListener((ChannelFutureListener) f -> {
                if (f.isSuccess()) {
                    //was able to flush out data, start to read the next chunk
                    ctx.channel().read();
                } else {
                    f.channel().close();
                }
            });
        }
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        if (outboundChannel != null) {
            closeAndFlush(outboundChannel);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        closeAndFlush(ctx.channel());
    }

    /**
     * Close the specified channel after all queued write requests are flush.
     */
    static void closeAndFlush(Channel ch) {
        if (ch.isActive()) {
            ch.writeAndFlush(Unpooled.EMPTY_BUFFER).addListener(ChannelFutureListener.CLOSE);
        }
    }
}
