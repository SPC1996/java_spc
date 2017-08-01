package java_spc.netty.privateprotocol.message;

/**
 * 私有协议栈消息定义
 * 包括两部分:
 * 名称		类型		长度		描述
 * header	Header	变长		消息头定义
 * body		Object	变长		对于请求信息，是方法的参数
 * 							对于响应信息，是返回值
 * 
 * @author SPC
 * @see Header
 * @see Object
 * 2017年7月31日
 */
public final class NettyMessage {
	private Header header;
	private Object body;

	public final Header getHeader() {
		return header;
	}

	public final void setHeader(Header header) {
		this.header = header;
	}

	public final Object getBody() {
		return body;
	}

	public final void setBody(Object body) {
		this.body = body;
	}
	
	public String toString() {
		return "NettyMessage {header="+header+",body="+body+"}"; 
	}
}
