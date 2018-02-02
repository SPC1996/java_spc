package java_spc.tutorials.custom_networking.udp;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Date;
import java.util.logging.Logger;

/**
 * 观看java tutorials的custom networking模块时所写的代码
 */
public class QuoteServerThread extends Thread {
    private static final Logger LOG = Logger.getLogger("QuoteServerThread");

    protected DatagramSocket socket;
    protected BufferedReader in;
    protected boolean moreQuotes = true;

    public QuoteServerThread() {
        this("QuoteServerThread");
    }

    public QuoteServerThread(String name) {
        super(name);
        try {
            socket = new DatagramSocket(2333);
            in = new BufferedReader(new FileReader("./one-liners.txt"));
        } catch (FileNotFoundException e) {
            LOG.severe("could not open quote file. serving time instead.");
        } catch (IOException e) {
            LOG.severe("socket init failed!");
            System.exit(1);
        }
    }

    @Override
    public void run() {
        while (moreQuotes) {
            try {
                byte[] buf = new byte[256];
                DatagramPacket packet = new DatagramPacket(buf, buf.length);
                socket.receive(packet);

                String response;
                if (in == null) {
                    response = new Date().toString();
                } else {
                    response = getNextQuote();
                }
                buf = response.getBytes();

                InetAddress address = packet.getAddress();
                int port = packet.getPort();
                packet = new DatagramPacket(buf, buf.length, address, port);
                socket.send(packet);
            } catch (IOException e) {
                LOG.severe("exception caught in receive packet!");
                moreQuotes = false;
            }
        }
        socket.close();
    }

    protected String getNextQuote() {
        String result;
        try {
            if ((result = in.readLine()) == null) {
                in.close();
                moreQuotes = false;
                result = "no more quotes. goodbye.";
            }
        } catch (IOException e) {
            LOG.severe("IOException occurred in server");
            result = "IOException occurred in server";
        }
        return result;
    }
}
