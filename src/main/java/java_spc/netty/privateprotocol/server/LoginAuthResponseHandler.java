package java_spc.netty.privateprotocol.server;

import java.net.InetSocketAddress;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import java_spc.netty.privateprotocol.message.Header;
import java_spc.netty.privateprotocol.message.MessageType;
import java_spc.netty.privateprotocol.message.NettyMessage;

/**
 * 接入握手消息和进行安全认证
 *
 * @author SPC
 * 2017年8月1日
 */
public class LoginAuthResponseHandler extends ChannelInboundHandlerAdapter {
    private Map<String, Boolean> nodeCheck = new ConcurrentHashMap<>();
    private String[] whiteList = {"127.0.0.1", "192.168.0.115"};

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        NettyMessage message = (NettyMessage) msg;
        if (message.getHeader() != null && message.getHeader().getType() == MessageType.LOGIN_REQUEST.value()) {
            String nodeIndex = ctx.channel().remoteAddress().toString();
            NettyMessage loginResponse = null;
            if (nodeCheck.containsKey(nodeIndex)) {
                loginResponse = buildResponse((byte) -1);
            } else {
                InetSocketAddress address = (InetSocketAddress) ctx.channel().remoteAddress();
                String ip = address.getAddress().getHostAddress();
                boolean isOk = false;
                for (String w : whiteList) {
                    if (w.equals(ip)) {
                        isOk = true;
                        break;
                    }
                }
                loginResponse = isOk ? buildResponse((byte) 0) : buildResponse((byte) -1);
                if (isOk)
                    nodeCheck.put(nodeIndex, true);
            }
            System.out.println("The login response is : " + loginResponse);
            ctx.writeAndFlush(loginResponse);
        } else {
            ctx.fireChannelRead(msg);
        }
    }

    private NettyMessage buildResponse(byte result) {
        NettyMessage message = new NettyMessage();
        Header header = new Header();
        header.setType(MessageType.LOGIN_RESPONSE.value());
        message.setHeader(header);
        message.setBody(result);
        return message;
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        nodeCheck.remove(ctx.channel().remoteAddress().toString());
        ctx.close();
        ctx.fireExceptionCaught(cause);
    }
}
