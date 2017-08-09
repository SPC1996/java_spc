package java_spc.netty.http.nio_based;

public class MalformedRequestException extends Exception {
    MalformedRequestException() {
    }

    MalformedRequestException(String msg) {
        super(msg);
    }

    MalformedRequestException(Exception e) {
        super(e);
    }
}
