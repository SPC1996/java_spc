package java_spc.netty.http.nio_based;

import java_spc.util.Resource;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;
import java.io.FileInputStream;
import java.net.InetSocketAddress;
import java.nio.channels.ServerSocketChannel;
import java.security.KeyStore;

/**
 * The main server base class.
 * <p>
 * This class is responsible for setting up most of the server state
 * before the actual server subclass take over.
 */
public abstract class Server {
    private static final String KEY_PATH_SOURCE = Resource.pathToSource("key\\");

    ServerSocketChannel ssc;
    SSLContext sslContext = null;

    private static final int PORT = 8000;
    private static final int BACKLOG = 1024;
    private static final boolean SECURE = false;

    Server(int port, int backlog, boolean secure) throws Exception {
        if (secure) {
            createSSLContext();
        }
        ssc = ServerSocketChannel.open();
        ssc.socket().setReuseAddress(true);
        ssc.socket().bind(new InetSocketAddress(port), backlog);
    }

    private void createSSLContext() throws Exception {
        char[] passphrase = "passphrase".toCharArray();
        KeyStore keyStore = KeyStore.getInstance("JKS");
        keyStore.load(new FileInputStream(KEY_PATH_SOURCE + "testkeys"), passphrase);
        KeyManagerFactory kmf = KeyManagerFactory.getInstance("SunX509");
        kmf.init(keyStore, passphrase);
        TrustManagerFactory tmf = TrustManagerFactory.getInstance("SunX509");
        tmf.init(keyStore);

        sslContext = SSLContext.getInstance("TLS");
        sslContext.init(kmf.getKeyManagers(), tmf.getTrustManagers(), null);
    }

    abstract void runServer() throws Exception;

    private static void usuage() {
        System.out.println(
                "Uasge: Server <type> [option]\n"
                        + " type:\n"
                        + "      B1 Blocking/Single-threaded Server\n"
                        + "      BN Blocking/Multi-threaded Server\n"
                        + "      BP Blocking/Pooled-thread Server\n"
                        + "      N1 Nonblocking/Single-threaded Server\n"
                        + "      N2 Nonblocking/Dual-threaded Server\n"
                        + "\n"
                        + " options:\n"
                        + "      -port port      port number\n"
                        + "          default: " + PORT + "\n"
                        + "      -backlog backlog    backlog\n"
                        + "          default: " + BACKLOG + "\n"
                        + "      -secure     encrypt with SSL/TLS"
        );
        System.exit(1);
    }

    private static Server createServer(String[] args) throws Exception {
        if (args.length < 1) {
            usuage();
        }

        int port = PORT;
        int backlog = BACKLOG;
        boolean secure = SECURE;

        for (int i = 1; i < args.length; i++) {
            if (args[i].equals("-port")) {
                checkArgs(i, args.length);
                port = Integer.valueOf(args[++i]);
            } else if (args[i].equals("-backlog")) {
                checkArgs(i, args.length);
                backlog = Integer.valueOf(args[++i]);
            } else if (args[i].equals("-secure")) {
                secure = true;
            } else {
                usuage();
            }
        }

        Server server = null;
        if (args[0].equals("B1")) {
            server = new B1(port, backlog, secure);
        } else if (args[0].equals("BN")) {
            server = new BN(port, backlog, secure);
        } else if (args[0].equals("BP")) {
            server = new BP(port, backlog, secure);
        } else if (args[0].equals("N1")) {
            server = new N1(port, backlog, secure);
        } else if (args[0].equals("N2")) {
            server = new N2(port, backlog, secure);
        } else {
            usuage();
        }
        return server;

    }

    private static void checkArgs(int i, int len) {
        if (i + 1 >= len) {
            usuage();
        }
    }

    public static void main(String[] args) throws Exception {
        Server server = createServer(args);
        if (server == null) {
            usuage();
        } else {
            System.out.println("Server started.");
            server.runServer();
        }
    }
}
