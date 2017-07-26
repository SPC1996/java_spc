package java_spc.netty.serialize.base;

import java.io.Serializable;
import java.nio.ByteBuffer;

import org.msgpack.annotation.Message;

/**
 * @author SPC
 * 2017年7月24日
 * 可序列化的UserInfo信息
 */
@Message
public class UserInfo implements Serializable {
	private static final long serialVersionUID = 1L;
	private int userId;
	private String userName;
	
	public UserInfo buildUserName(String userName) {
		this.userName=userName;
		return this;
	}
	
	public UserInfo buildUserId(int userId) {
		this.userId=userId;
		return this;
	}
	
	public final String getUserName() {
		return userName;
	}
	
	public final void setUserName(String userName) {
		this.userName=userName;
	}
	
	public final int getUserId() {
		return userId;
	}
	
	public final void setUserId(int userId) {
		this.userId=userId;
	}
	
	public byte[] code() {
		ByteBuffer buffer=ByteBuffer.allocate(1024);
		byte[] value=userName.getBytes();
		buffer.putInt(value.length);
		buffer.put(value);
		buffer.putInt(userId);
		buffer.flip();
		value=null;
		byte[] result=new byte[buffer.remaining()];
		buffer.get(result);
		return result;
	}
	
	public byte[] code(ByteBuffer buffer) {
		buffer.clear();
		byte[] value=userName.getBytes();
		buffer.putInt(value.length);
		buffer.put(value);
		buffer.putInt(userId);
		buffer.flip();
		value=null;
		byte[] result=new byte[buffer.remaining()];
		buffer.get(result);
		return result;
	}
	
	public String toString() {
		return "["+userId+", "+userName+"]";
	}
}
