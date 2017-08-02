package java_spc.netty.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * @author SPC
 * 2017年7月19日
 * 同步非阻塞IO获取时间--使用NIO
 * 客户端
 */
public class SNBTimeClient {
    private String host;
    private int port;

    public SNBTimeClient() {
        this.host = "127.0.0.1";
        this.port = 8080;
    }

    public SNBTimeClient(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public void run() {
        new Thread(new SNBHandle(host, port), "NIO-CLIENT-001").start();
    }

    public static void main(String[] args) {
        SNBTimeClient client = new SNBTimeClient();
        client.run();
    }
}

class SNBHandle implements Runnable {
    private String host;
    private int port;
    private Selector selector;
    private SocketChannel sc;
    private volatile boolean stop;

    public SNBHandle() {
        this(null, 8080);
    }

    public SNBHandle(String host, int port) {
        this.host = host == null ? "127.0.0.1" : host;
        this.port = port;
        try {
            selector = Selector.open();
            sc = SocketChannel.open();
            sc.configureBlocking(false);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    @Override
    public void run() {
        try {
            doConnect();
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
        while (!stop) {
            try {
                selector.select(1000);
                Set<SelectionKey> selectionKeys = selector.selectedKeys();
                Iterator<SelectionKey> iterator = selectionKeys.iterator();
                SelectionKey key = null;
                while (iterator.hasNext()) {
                    key = iterator.next();
                    iterator.remove();
                    try {
                        handleInput(key);
                    } catch (IOException e) {
                        if (key != null) {
                            key.cancel();
                            if (key.channel() != null) {
                                key.channel().close();
                            }
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                System.exit(1);
            }
        }
        if (selector != null) {
            try {
                selector.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void handleInput(SelectionKey key) throws IOException {
        if (key.isValid()) {
            SocketChannel sc = (SocketChannel) key.channel();
            if (key.isConnectable()) {
                if (sc.finishConnect()) {
                    sc.register(selector, SelectionKey.OP_READ);
                    doWrite(sc);
                } else {
                    System.exit(1);
                }
            }
            if (key.isReadable()) {
                ByteBuffer readBuffer = ByteBuffer.allocate(1024);
                int readBytes = sc.read(readBuffer);
                if (readBytes > 0) {
                    readBuffer.flip();
                    byte[] bytes = new byte[readBuffer.remaining()];
                    readBuffer.get(bytes);
                    String body = new String(bytes, "utf-8");
                    System.out.println("Now is : " + body);
                    stop = true;
                } else if (readBytes < 0) {
                    key.cancel();
                    sc.close();
                } else {
                    ;
                }
            }
        }
    }

    private void doWrite(SocketChannel sc) throws IOException {
        byte[] req = "QUERY TIME ORDER".getBytes();
        ByteBuffer writeBuffer = ByteBuffer.allocate(req.length);
        writeBuffer.put(req);
        writeBuffer.flip();
        sc.write(writeBuffer);
        if (!writeBuffer.hasRemaining()) {
            System.out.println("Send order to server succeed");
        }
    }

    private void doConnect() throws IOException {
        if (sc.connect(new InetSocketAddress(host, port))) {
            sc.register(selector, SelectionKey.OP_READ);
            doWrite(sc);
        } else {
            sc.register(selector, SelectionKey.OP_CONNECT);
        }
    }
}
