package java_spc.netty.http.xml;

import io.netty.handler.codec.http.FullHttpResponse;

public class HttpXmlResponse {
    private FullHttpResponse response;
    private Object body;

    public HttpXmlResponse(FullHttpResponse response, Object body) {
        this.response = response;
        this.body = body;
    }

    public final FullHttpResponse getResponse() {
        return response;
    }

    public final void setResponse(FullHttpResponse response) {
        this.response = response;
    }

    public final Object getBody() {
        return body;
    }

    public final void setBody(Object body) {
        this.body = body;
    }

    public String toString() {
        return "HttpXmlResponse [response=" + response + ", body=" + body + "]";
    }
}
