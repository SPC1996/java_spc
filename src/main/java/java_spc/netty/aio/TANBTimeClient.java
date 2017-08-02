package java_spc.netty.aio;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.util.concurrent.CountDownLatch;

/**
 * @author SPC
 * 2017年7月21日
 * 使用异步非阻塞IO获取时间--使用AIO
 * 客户端
 */
public class TANBTimeClient {
    private String host;
    private int port;

    public TANBTimeClient() {
        this("127.0.0.1", 8080);
    }

    public TANBTimeClient(String host, int port) {
        this.host = host == null ? "127.0.0.1" : host;
        this.port = port;
    }

    public void run() {
        new Thread(new TANBClientHandler(host, port), "AIO-CLIENT-001").start();
    }

    public static void main(String[] args) {
        TANBTimeClient client = new TANBTimeClient();
        client.run();
    }
}

class TANBClientHandler implements CompletionHandler<Void, TANBClientHandler>, Runnable {
    private AsynchronousSocketChannel channel;
    private String host;
    private int port;
    private CountDownLatch latch;

    public TANBClientHandler() {
        this("127.0.0.1", 8080);
    }

    public TANBClientHandler(String host, int port) {
        this.host = host;
        this.port = port;
        try {
            channel = AsynchronousSocketChannel.open();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        latch = new CountDownLatch(1);
        channel.connect(new InetSocketAddress(host, port), this, this);
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        try {
            channel.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void completed(Void result, TANBClientHandler attachment) {
        byte[] req = "QUERY TIME ORDER".getBytes();
        ByteBuffer writeBuffer = ByteBuffer.allocate(req.length);
        writeBuffer.put(req);
        writeBuffer.flip();
        channel.write(writeBuffer, writeBuffer, new CompletionHandler<Integer, ByteBuffer>() {

            @Override
            public void completed(Integer result, ByteBuffer buffer) {
                if (buffer.hasRemaining()) {
                    channel.write(buffer, buffer, this);
                } else {
                    ByteBuffer readBuffer = ByteBuffer.allocate(1024);
                    channel.read(readBuffer, readBuffer, new CompletionHandler<Integer, ByteBuffer>() {

                        @Override
                        public void completed(Integer result, ByteBuffer buffer) {
                            buffer.flip();
                            byte[] bytes = new byte[buffer.remaining()];
                            buffer.get(bytes);
                            String body;
                            try {
                                body = new String(bytes, "utf-8");
                                System.out.println("Now is : " + body);
                                latch.countDown();
                            } catch (UnsupportedEncodingException e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void failed(Throwable exc, ByteBuffer attachment) {
                            try {
                                channel.close();
                            } catch (IOException e) {
                                //Ignore on close
                            }
                        }
                    });
                }
            }

            @Override
            public void failed(Throwable exc, ByteBuffer attachment) {
                try {
                    channel.close();
                    latch.countDown();
                } catch (IOException e) {
                    //Ignore on close
                }
            }
        });
    }

    @Override
    public void failed(Throwable exc, TANBClientHandler attachment) {
        try {
            channel.close();
        } catch (IOException e) {
            //Ignore on close
        }
    }

}