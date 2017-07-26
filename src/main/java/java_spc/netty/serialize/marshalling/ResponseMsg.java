package java_spc.netty.serialize.marshalling;

import java.io.Serializable;

public class ResponseMsg implements Serializable {
	private static final long serialVersionUID = 1L;
	private String res;
	private String status;

	public String getRes() {
		return res;
	}

	public void setRes(String res) {
		this.res = res;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String toString() {
		return "status: "+status+", res: "+res;
	}
}
