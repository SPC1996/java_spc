package java_spc.tutorials.custom_networking.tcp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;
import java.util.logging.Logger;

/**
 * 观看java tutorials的custom networking模块时所写的代码
 */
public class EchoClient {
    private static final Logger LOG = Logger.getLogger("EchoClient");

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("input host name or ip");
        String hostName = scanner.nextLine();
        System.out.println("input port");
        int port = Integer.parseInt(scanner.nextLine());


        try (
                Socket echoSocket = new Socket(hostName, port);
                PrintWriter out = new PrintWriter(echoSocket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(new InputStreamReader(echoSocket.getInputStream()))
        ) {
            System.out.println("echo data");
            String userInput;
            while (!(userInput = scanner.nextLine()).toUpperCase().equals("EXIT")) {
                out.println(userInput);
                System.out.println("echo: " + in.readLine());
            }

        } catch (UnknownHostException e) {
            LOG.severe("don't know about host " + hostName);
            System.exit(1);
        } catch (IOException e) {
            LOG.severe("socket init failed, couldn't get I/O for the connection to " + hostName);
            System.exit(1);
        }
    }
}
