package java_spc.netty.http.nio_based;

import java.io.IOException;
import java.nio.ByteBuffer;

public class RequestServicer implements Runnable {
    private ChannelIO cio;
    private static int created = 0;

    RequestServicer(ChannelIO cio) {
        this.cio = cio;
        synchronized (RequestServicer.class) {
            created++;
            if (created % 50 == 0) {
                System.out.println(".");
                created = 0;
            } else {
                System.out.print(".");
            }
        }
    }

    private void service() throws IOException {
        Reply reply = null;
        try {
            ByteBuffer rbb = receive();
            Request request = null;
            try {
                request = Request.parse(rbb);
            } catch (MalformedRequestException e) {
                reply = new Reply(Reply.Code.BAD_REQUEST, new StringContent(e));
            }
            if (reply == null) {
                reply = build(request);
            }
            do {
            } while (reply.send(cio));
            do {
            } while (!cio.shutdown());
            cio.close();
            reply.release();
        } catch (IOException e) {
            String m = e.getMessage();
            if (!m.equals("Broken pipe") && !m.equals("Connection reset by peer")) {
                System.err.println("RequestHandler: " + e.toString());
            }
            try {
                cio.shutdown();
            } catch (IOException ignored) {

            }
            cio.close();
            if (reply != null) {
                reply.release();
            }
        }
    }

    private ByteBuffer receive() throws IOException {
        do {
        } while (!cio.doHandShake());
        for (; ; ) {
            int read = cio.read();
            ByteBuffer bb = cio.getReadBuf();
            if (read < 0 || Request.isComplete(bb)) {
                bb.flip();
                return bb;
            }
        }
    }

    private Reply build(Request request) throws IOException {
        Reply reply = null;
        Request.Action action = request.action();
        if (action != Request.Action.GET && action != Request.Action.HEAD) {
            reply = new Reply(Reply.Code.METHOD_NOT_ALLOWED, new StringContent(request.toString()));
        } else {
            reply = new Reply(Reply.Code.OK, new FileContent(request.uri()), action);
        }
        try {
            reply.prepare();
        } catch (IOException e) {
            reply.release();
            reply = new Reply(Reply.Code.NOT_FOUND, new StringContent(e));
            reply.prepare();
        }
        return reply;
    }

    @Override
    public void run() {
        try {
            service();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
