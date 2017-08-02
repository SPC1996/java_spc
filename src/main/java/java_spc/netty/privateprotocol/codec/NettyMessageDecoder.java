package java_spc.netty.privateprotocol.codec;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import java_spc.netty.privateprotocol.message.Header;
import java_spc.netty.privateprotocol.message.NettyMessage;

public class NettyMessageDecoder extends LengthFieldBasedFrameDecoder {
    private final MarshallingDecoder decoder;

    public NettyMessageDecoder(int maxFrameLength, int lengthFieldOffset, int lengthFieldLength, int lengthAdjustment,
                               int initialBytesToStrip) throws IOException {
        super(maxFrameLength, lengthFieldOffset, lengthFieldLength, lengthAdjustment, initialBytesToStrip);
        decoder = new MarshallingDecoder();

    }

    public Object decode(ChannelHandlerContext ctx, ByteBuf in) throws Exception {
        ByteBuf frame = (ByteBuf) super.decode(ctx, in);
        if (frame == null) {
            return null;
        }
        NettyMessage message = new NettyMessage();
        Header header = new Header();
        header.setCrcCode(frame.readInt());
        header.setLength(frame.readInt());
        header.setSessionId(frame.readLong());
        header.setType(frame.readByte());
        header.setPriority(frame.readByte());
        int size = frame.readInt();
        if (size > 0) {
            Map<String, Object> attachment = new HashMap<>(size);
            int keySize = 0;
            byte[] keyArray = null;
            String key = null;
            for (int i = 0; i < size; i++) {
                keySize = frame.readInt();
                keyArray = new byte[keySize];
                frame.readBytes(keyArray);
                key = new String(keyArray, "utf-8");
                attachment.put(key, decoder.decode(frame));
            }
            keyArray = null;
            key = null;
            header.setAttachment(attachment);
        }
        if (frame.readableBytes() > 4) {
            message.setBody(decoder.decode(frame));
        }
        message.setHeader(header);
        return message;
    }
}
