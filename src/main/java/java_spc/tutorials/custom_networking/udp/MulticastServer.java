package java_spc.tutorials.custom_networking.udp;

/**
 * 观看java tutorials的custom networking模块时所写的代码
 */
public class MulticastServer {
    public static void main(String[] args) {
        new MulticastServerThread().start();
    }
}
