package java_spc.netty.bio;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * @author SPC
 * 2017年7月19日
 * 同步阻塞I/O实现获取时间
 * 客户端
 */
public class SBTimeClient {
    private String host;
    private int port;

    public SBTimeClient() {
        this.host = "127.0.0.1";
        this.port = 8080;
    }

    public SBTimeClient(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public void run() {
        Socket socket = null;
        BufferedReader in = null;
        PrintWriter out = null;
        try {
            socket = new Socket(host, port);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);
            out.println("QUERY TIME ORDER");
            System.out.println("Send order to server succeed");
            String recvMsg = in.readLine();
            System.out.println("Now is : " + recvMsg);
        } catch (Exception e) {
        } finally {
            if (out != null) {
                out.close();
                out = null;
            }
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                in = null;
            }
            if (socket != null) {
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                socket = null;
            }
        }
    }

    public static void main(String[] args) {
        SBTimeClient client = new SBTimeClient();
        client.run();
    }
}
