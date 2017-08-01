package java_spc.netty.privateprotocol.codec;

import java.io.IOException;

import org.jboss.marshalling.ByteInput;
import org.jboss.marshalling.Unmarshaller;

import io.netty.buffer.ByteBuf;

public class MarshallingDecoder {
	private final Unmarshaller unmarshaller;
	
	public MarshallingDecoder() throws IOException {
		unmarshaller=MarshallingCodeCFactory.buildUnmarshalling();
	}
	
	public Object decode(ByteBuf in) throws Exception {
		int size=in.readInt();
		ByteBuf buf=in.slice(in.readerIndex(), size);
		ByteInput input=new ChannelBufferByteInput(buf);
		try {
			unmarshaller.start(input);
			Object obj=unmarshaller.readObject();
			unmarshaller.finish();
			in.readerIndex(in.readerIndex()+size);
			return obj;
		} finally {
			unmarshaller.close();
		}
	}
}
