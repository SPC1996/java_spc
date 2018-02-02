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
public class KnockClient {
    private static final Logger LOG = Logger.getLogger("KnockClient");

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("input host name or ip");
        String hostName = scanner.nextLine();
        System.out.println("input port");
        int port = Integer.parseInt(scanner.nextLine());

        try (
                Socket knockSocket = new Socket(hostName, port);
                PrintWriter out = new PrintWriter(knockSocket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(knockSocket.getInputStream())
                )
        ) {
            String fromServer, fromUser;
            while ((fromServer = in.readLine()) != null) {
                System.out.println("server: " + fromServer);
                if (fromServer.equals("Bye.")) {
                    break;
                }
                System.out.println("input what you want to say...");
                fromUser = scanner.nextLine();
                if (fromUser != null) {
                    System.out.println("client: " + fromUser);
                    out.println(fromUser);
                }
            }
        } catch (UnknownHostException e) {
            LOG.severe("don't know about host " + hostName);
            System.exit(1);
        } catch (IOException e) {
            LOG.severe("couldn't get I/O for the connection to " + hostName);
            System.exit(1);
        }
    }
}
