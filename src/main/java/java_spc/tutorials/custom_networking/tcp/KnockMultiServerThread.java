package java_spc.tutorials.custom_networking.tcp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.logging.Logger;

/**
 * 观看java tutorials的custom networking模块时所写的代码
 */
public class KnockMultiServerThread extends Thread {
    private static final Logger LOG = Logger.getLogger("KnockMultiServerThread");
    private Socket socket;

    public KnockMultiServerThread(Socket socket) {
        super("KnockMultiServerThread");
        this.socket = socket;
    }

    @Override
    public void run() {
        try (
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))
        ) {
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
            LOG.severe("couldn't get I/O for the connection");
        }
    }
}
