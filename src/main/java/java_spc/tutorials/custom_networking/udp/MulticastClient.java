package java_spc.tutorials.custom_networking.udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.util.logging.Logger;

/**
 * 观看java tutorials的custom networking模块时所写的代码
 * 虚拟机参数配置：-Djava.net.preferIPv4Stack=true
 */
public class MulticastClient {
    private static final Logger LOG = Logger.getLogger("MulticastClient");

    public static void main(String[] args) {
        try {
            MulticastSocket socket = new MulticastSocket(2334);
            InetAddress address = InetAddress.getByName("230.0.0.1");
            socket.joinGroup(address);

            DatagramPacket packet;
            for (int i = 0; i < 5; i++) {
                byte[] buf = new byte[256];
                packet = new DatagramPacket(buf, buf.length);
                socket.receive(packet);

                String received = new String(packet.getData(), 0, packet.getLength());
                System.out.println("quote of the moment: " + received);
            }
        } catch (IOException e) {
            LOG.severe("client init failed!");
            System.exit(1);
        }
    }
}
