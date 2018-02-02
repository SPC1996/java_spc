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
public class KnockServer {
    private static final Logger LOG = Logger.getLogger("KnockServer");

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("input port");
        int port = Integer.parseInt(scanner.nextLine());

        try (
                ServerSocket serverSocket = new ServerSocket(port);
                Socket clientSocket = serverSocket.accept();
                PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        ) {
            System.out.println("the server is listening on port " + port);
            String inputLine, outputLine;

            KnockProtocol protocol = new KnockProtocol();
            outputLine = protocol.processInput(null);
            out.println(outputLine);

            while ((inputLine = in.readLine()) != null) {
                outputLine = protocol.processInput(inputLine);
                out.println(outputLine);
                if (outputLine.equalsIgnoreCase("Bye.")) {
                    break;
                }
            }
        } catch (IOException e) {
            LOG.severe("exception caught when trying to listen on port " +
                    port + " or listening for a connection");
            System.exit(1);
        }
    }
}
