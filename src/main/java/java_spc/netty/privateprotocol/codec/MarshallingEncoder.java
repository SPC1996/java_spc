package java_spc.netty.privateprotocol.codec;

import java.io.IOException;

import org.jboss.marshalling.Marshaller;

import io.netty.buffer.ByteBuf;

public final class MarshallingEncoder {
	private final static byte[] LENGTH_PLACEHOLDER=new byte[4];
	private Marshaller marshaller;
	
	public MarshallingEncoder() throws IOException {
		marshaller=MarshallingCodeCFactory.buildMarshalling();
	}
	
	public void encode(Object msg, ByteBuf out) throws Exception {
		try {
			int pos=out.writerIndex();
			out.writeBytes(LENGTH_PLACEHOLDER);
			ChannelBufferByteOutput output=new ChannelBufferByteOutput(out);
			marshaller.start(output);
			marshaller.writeObject(msg);
			marshaller.finish();
			out.setInt(pos, out.writerIndex()-pos-4);
		} finally {
			marshaller.close();
		}
	}
}
