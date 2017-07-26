package java_spc.netty.serialize.base;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.nio.ByteBuffer;

/**
 * @author SPC
 * 2017年7月24日
 * 对用户信息进行序列化
 */
public class SerializeUserInfo {
	public static void testByteStreamSize() throws IOException{
		UserInfo info=new UserInfo();
		info.buildUserId(100).buildUserName("Welcome to netty");
		ByteArrayOutputStream bos=new ByteArrayOutputStream();
		ObjectOutputStream os=new ObjectOutputStream(bos);
		os.writeObject(info);
		os.flush();
		os.close();
		byte[] b=bos.toByteArray();
		System.out.println("The jdk serializable length is : "+b.length);
		bos.close();
		System.out.println("--------------------------------------");
		System.out.println("The byte array serializable length is : "+info.code().length);
	}
	
	public static void testSpeed() throws IOException {
		UserInfo info=new UserInfo();
		info.buildUserId(100).buildUserName("Welcome to netty");
		int loop=1000000;
		ByteArrayOutputStream bos=null;
		ObjectOutputStream os=null;
		long startTime=System.currentTimeMillis();
		for(int i=0;i<loop;i++) {
			bos=new ByteArrayOutputStream();
			os=new ObjectOutputStream(bos);
			os.writeObject(info);
			os.flush();
			os.close();
			@SuppressWarnings("unused")
			byte[] b=bos.toByteArray();
			bos.close();
		}
		long endTime=System.currentTimeMillis();
		System.out.println("The jdk serializable cost time is : "+(endTime-startTime)+" ms");
		System.out.println("--------------------------------------------------------");
		ByteBuffer buffer=ByteBuffer.allocate(1024);
		startTime=System.currentTimeMillis();
		for(int i=0;i<loop;i++) {
			@SuppressWarnings("unused")
			byte[] b=info.code(buffer);
		}
		endTime=System.currentTimeMillis();
		System.out.println("The byte array serializable cost time is : "+(endTime-startTime)+" ms");
	}

	public static void main(String[] args) throws IOException {
		testByteStreamSize();
		testSpeed();
	}

}
