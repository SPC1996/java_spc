package java_spc.netty.serialize.protobuf;

import com.google.protobuf.InvalidProtocolBufferException;

import java_spc.netty.serialize.protobuf.ReqInfo.ReqInfoProto;
import java_spc.netty.serialize.protobuf.ReqInfo.ReqInfoProto.Builder;

public class Test {
    private static byte[] encode(ReqInfoProto req) {
        return req.toByteArray();
    }

    private static ReqInfoProto decode(byte[] body) throws InvalidProtocolBufferException {
        return ReqInfoProto.parseFrom(body);
    }

    private static ReqInfoProto create() {
        Builder builder = ReqInfoProto.newBuilder();
        builder.setId(1);
        builder.setUname("spc");
        builder.setPname("Book");
        builder.setAddress("Changsha");
        return builder.build();
    }

    public static void main(String[] args) throws InvalidProtocolBufferException {
        ReqInfoProto req = create();
        System.out.println("Before encode : " + req.toString());
        ReqInfoProto req2 = decode(encode(req));
        System.out.println("After encode : " + req2.toString());
        System.out.println("Assert equal : " + req.equals(req2));
    }

}
