package java_spc.netty.aio;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.util.Date;
import java.util.concurrent.CountDownLatch;

/**
 * @author SPC
 * 2017年7月20日
 * 使用异步非阻塞IO获取时间--使用AIO
 * 服务端
 */
public class TANBTimeServer {
    private int port;

    public TANBTimeServer() {
        this.port = 8080;
    }

    public TANBTimeServer(int port) {
        this.port = port;
    }

    public void run() {
        TANBHandler service = new TANBHandler(port);
        new Thread(service, "AIO-SERVER-001").start();
    }

    public static void main(String[] args) {
        TANBTimeServer server = new TANBTimeServer();
        server.run();
    }
}

class TANBHandler implements Runnable {
    private int port;
    CountDownLatch latch;
    AsynchronousServerSocketChannel channel;

    public TANBHandler() {
        this(8080);
    }

    public TANBHandler(int port) {
        this.port = port;
        try {
            channel = AsynchronousServerSocketChannel.open();
            channel.bind(new InetSocketAddress(this.port));
            System.out.println("The time server is start in port : " + port);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        latch = new CountDownLatch(1);
        doAccept();
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void doAccept() {
        channel.accept(this, new AcceptHandler());
    }
}

class AcceptHandler implements CompletionHandler<AsynchronousSocketChannel, TANBHandler> {

    @Override
    public void completed(AsynchronousSocketChannel result, TANBHandler attachment) {
        attachment.channel.accept(attachment, this);
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        result.read(buffer, buffer, new ReadHandler(result));
    }

    @Override
    public void failed(Throwable exc, TANBHandler attachment) {
        exc.printStackTrace();
        attachment.latch.countDown();
    }

}

class ReadHandler implements CompletionHandler<Integer, ByteBuffer> {
    private AsynchronousSocketChannel channel;

    public ReadHandler(AsynchronousSocketChannel channel) {
        if (this.channel == null) {
            this.channel = channel;
        }
    }

    @Override
    public void completed(Integer result, ByteBuffer attachment) {
        attachment.flip();
        byte[] body = new byte[attachment.remaining()];
        attachment.get(body);
        try {
            String req = new String(body, "utf-8");
            System.out.println("The time server receive order : " + req);
            String currentTime = "QUERY TIME ORDER".equalsIgnoreCase(req) ? new Date(System.currentTimeMillis()).toString() : "BAD ORDER";
            doWrite(currentTime);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    private void doWrite(String currentTime) {
        if (currentTime != null && currentTime.trim().length() > 0) {
            byte[] bytes = currentTime.getBytes();
            ByteBuffer writeBuffer = ByteBuffer.allocate(bytes.length);
            writeBuffer.put(bytes);
            writeBuffer.flip();
            channel.write(writeBuffer, writeBuffer, new CompletionHandler<Integer, ByteBuffer>() {

                @Override
                public void completed(Integer result, ByteBuffer buffer) {
                    if (buffer.hasRemaining()) {
                        channel.write(buffer, buffer, this);
                    }
                }

                @Override
                public void failed(Throwable exc, ByteBuffer buffer) {
                    try {
                        channel.close();
                    } catch (IOException e) {
                        //Ingnore on close
                    }
                }
            });
        }
    }

    @Override
    public void failed(Throwable exc, ByteBuffer attachment) {
        try {
            this.channel.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}