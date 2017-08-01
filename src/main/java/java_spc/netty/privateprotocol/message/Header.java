package java_spc.netty.privateprotocol.message;

import java.util.HashMap;
import java.util.Map;

/**
 * 私有协议栈消息头定义
 * 名称			类型					长度			描述
 * crcCode		int					32			消息校验码	crcCode=0xABEF+主版本号+次版本号
 * 												1)0xABEF：固定值，表明该消息是私有协议消息，2个字节
 * 												2)主版本号：1-255，1个字节
 * 												3)次版本号：1-255，1个字节
 * length		int					32			整个消息长度，包括消息头和消息体
 * sessionId	long				64			集群节点内全局唯一，由会话ID生成器生成
 * type			byte				8			0：业务请求信息
 * 												1：业务响应信息
 * 												2：业务ONE WAY 信息（既是请求又是响应消息）
 * 												3：握手请求信息
 * 												4：握手应答信息
 * 												5：心跳请求信息
 * 												6：心跳应答信息
 * priority		byte				8			消息优先级：0-255
 * attachment	Map<String,object>	变长			可选字段用于扩展消息头
 * 
 * @author SPC
 * 2017年7月31日
 */
public final class Header {
	private int crcCode = 0xabef0101;
	private int length;
	private long sessionId;
	private byte type;
	private byte priority;
	private Map<String, Object> attachment = new HashMap<>();

	public final int getCrcCode() {
		return crcCode;
	}

	public final void setCrcCode(int crcCode) {
		this.crcCode = crcCode;
	}

	public final int getLength() {
		return length;
	}

	public final void setLength(int length) {
		this.length = length;
	}

	public final long getSessionId() {
		return sessionId;
	}

	public final void setSessionId(long sessionId) {
		this.sessionId = sessionId;
	}

	public final byte getType() {
		return type;
	}

	public final void setType(byte type) {
		this.type = type;
	}

	public final byte getPriority() {
		return priority;
	}

	public final void setPriority(byte priority) {
		this.priority = priority;
	}

	public final Map<String, Object> getAttachment() {
		return attachment;
	}

	public final void setAttachment(Map<String, Object> attachment) {
		this.attachment = attachment;
	}
	
	public String toString() {
		return "Header {crcCode="+crcCode+",length="+length
				+",sessionId="+sessionId+",type="+type+",priority="
				+priority+",attachment="+attachment+"}";
	}
}
