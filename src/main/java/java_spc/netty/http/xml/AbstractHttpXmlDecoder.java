package java_spc.netty.http.xml;

import java.nio.charset.Charset;

import com.thoughtworks.xstream.XStream;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;

public abstract class AbstractHttpXmlDecoder<T> extends MessageToMessageDecoder<T> {
    private boolean isPrint;
    private final static String CHARSET_NAME = "UTF-8";
    private final static Charset UTF_8 = Charset.forName(CHARSET_NAME);

    protected AbstractHttpXmlDecoder() {
        this(false);
    }

    protected AbstractHttpXmlDecoder(boolean isPrint) {
        this.isPrint = isPrint;
    }

    protected Object decode0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
        String content = msg.toString(UTF_8);
        if (isPrint) {
            System.out.println("The body is : " + content);
        }
        XStream xs = new XStream();
        xs.setMode(XStream.NO_REFERENCES);
        xs.processAnnotations(new Class[]{Order.class, Customer.class, Shipping.class, Address.class});
        Object result = xs.fromXML(content);
        return result;
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println("fail to decode!");
    }
}
