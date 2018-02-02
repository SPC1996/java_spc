package java_spc.tutorials.custom_networking.tcp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.Logger;

/**
 * 观看java tutorials的custom networking模块时所写的代码
 */
public class EchoServer {
    private static final Logger LOG = Logger.getLogger("EchoServer");

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Input port");
        int port = Integer.parseInt(scanner.nextLine());
        System.out.println("server is listening on port " + port);

        try (
                ServerSocket serverSocket = new ServerSocket(port);
                Socket clientSocket = serverSocket.accept();
                PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))
        ) {
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                System.out.println(inputLine);
                out.println(inputLine);
            }
        } catch (IOException e) {
            LOG.severe("Exception caught when trying to listen on port "
                    + port + " or listening for a connection");
        }
    }
}
