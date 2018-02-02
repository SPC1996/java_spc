package java_spc.tutorials.custom_networking.udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.util.Date;
import java.util.logging.Logger;

/**
 * 观看java tutorials的custom networking模块时所写的代码
 */
public class MulticastServerThread extends QuoteServerThread {
    private static final Logger LOG = Logger.getLogger("MulticastServerThread");

    private long FIVE_SECONDS = 5000;

    public MulticastServerThread() {
        super("MulticastServerThread");
    }

    @Override
    public void run() {
        while (moreQuotes) {
            try {
                byte[] buf = new byte[256];
                String response;
                if (in == null) {
                    response = new Date().toString();
                } else {
                    response = getNextQuote();
                }
                buf = response.getBytes();

                InetAddress group = InetAddress.getByName("230.0.0.1");
                DatagramPacket packet = new DatagramPacket(buf, buf.length, group, 2334);
                socket.send(packet);

                try {
                    sleep((long) (Math.random() * FIVE_SECONDS));
                } catch (InterruptedException e) {
                    LOG.severe("sleep is interrupted!");
                    moreQuotes = false;
                }
            } catch (IOException e) {
                LOG.severe("exception occurred!");
            }
        }
        socket.close();
    }
}
