package java_spc.tutorials.custom_networking.tcp;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.Scanner;
import java.util.logging.Logger;

/**
 * 观看java tutorials的custom networking模块时所写的代码
 */
public class KnockMultiServer {
    private static final Logger LOG = Logger.getLogger("KnockMultiServer");

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("input port");
        int port = Integer.parseInt(scanner.nextLine());
        boolean listening = true;

        try (
                ServerSocket serverSocket = new ServerSocket(port)
        ) {
            System.out.println("the server is listening on port " + port);
            while (listening) {
                new KnockMultiServerThread(serverSocket.accept()).start();
            }
        } catch (IOException e) {
            LOG.severe("exception caught when trying to listen on port " +
                    port + " or listening for a connection");
            System.exit(1);
        }
    }
}
