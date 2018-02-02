package java_spc.tutorials.custom_networking.udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.logging.Logger;

/**
 * 观看java tutorials的custom networking模块时所写的代码
 */
public class QuoteClient {
    private static final Logger LOG = Logger.getLogger("QuoteClient");

    public static void main(String[] args) {
        try {
            DatagramSocket socket = new DatagramSocket();

            byte[] buf = new byte[256];
            InetAddress address = InetAddress.getByName("localhost");
            DatagramPacket packet = new DatagramPacket(buf, buf.length, address, 2333);
            socket.send(packet);

            packet = new DatagramPacket(buf, buf.length);
            socket.receive(packet);

            String received = new String(packet.getData(), 0, packet.getLength());
            System.out.println("quote of the moment: " + received);
        } catch (SocketException e) {
            LOG.severe("socket init failed!");
            System.exit(1);
        } catch (UnknownHostException e) {
            LOG.severe("can't know the host name localhost");
        } catch (IOException e) {
            LOG.severe("exception caught in packet send");
        }
    }
}
