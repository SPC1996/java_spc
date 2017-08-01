package java_spc.netty.privateprotocol.codec;

import java.io.IOException;

import org.jboss.marshalling.Marshaller;
import org.jboss.marshalling.MarshallerFactory;
import org.jboss.marshalling.Marshalling;
import org.jboss.marshalling.MarshallingConfiguration;
import org.jboss.marshalling.Unmarshaller;

public final class MarshallingCodeCFactory {
	public static Unmarshaller buildUnmarshalling() throws IOException {
		final MarshallerFactory factory=Marshalling.getProvidedMarshallerFactory("serial");
		final MarshallingConfiguration configuration=new MarshallingConfiguration();
		configuration.setVersion(5);
		final Unmarshaller unmarshaller=factory.createUnmarshaller(configuration);
		return unmarshaller;
	}
	
	public static Marshaller buildMarshalling() throws IOException {
		final MarshallerFactory factory=Marshalling.getProvidedMarshallerFactory("serial");
		final MarshallingConfiguration configuration=new MarshallingConfiguration();
		configuration.setVersion(5);
		final Marshaller marshaller=factory.createMarshaller(configuration);
		return marshaller;
	}
}
