package java_spc.netty.http.nio_based;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;

/**
 * Primary driver class used by non-blocking Servers to receive,
 * prepare, send, and shutdown requests.
 */
public class RequestHandler implements Handler{
    private ChannelIO cio;
    private ByteBuffer rbb=null;
    private boolean requestReceived=false;
    private Request request=null;
    private Reply reply=null;
    private static int created=0;

    RequestHandler(ChannelIO cio) {
        this.cio=cio;
        synchronized (RequestHandler.class) {
            created++;
            if (created%50==0) {
                System.out.println(".");
                created=0;
            } else {
                System.out.print(".");
            }
        }
    }

    private boolean receive(SelectionKey sk) throws IOException {
        if (requestReceived) return true;
        if (!cio.doHandShake(sk)) return false;
        if (cio.read()<0||Request.isComplete(cio.getReadBuf())) {
            rbb=cio.getReadBuf();
            requestReceived=true;
            return true;
        }
        return false;
    }

    private boolean parse() throws IOException {
        try {
           request=Request.parse(rbb);
           return true;
        } catch (MalformedRequestException e) {
            reply=new Reply(Reply.Code.BAD_REQUEST, new StringContent(e));
        }
        return false;
    }

    private void build() throws IOException {
        Request.Action action=request.action();
        if (action!=Request.Action.GET&&action!=Request.Action.HEAD) {
            reply=new Reply(Reply.Code.METHOD_NOT_ALLOWED, new StringContent(request.toString()));
        }
        reply=new Reply(Reply.Code.OK, new FileContent(request.uri()), action);
    }

    private boolean send() throws IOException {
        try {
            return reply.send(cio);
        } catch (IOException e) {
            if (e.getMessage().startsWith("Resource temporarily")) {
                System.out.println("## RTA");
                return true;
            }
            throw e;
        }
    }

    @Override
    public void handle(SelectionKey sk) throws IOException {
        try {
            if (request==null) {
                if (!receive(sk))
                    return;
                rbb.flip();
                if (parse())
                    build();
                try {
                    reply.prepare();
                } catch (IOException e) {
                    reply.release();
                    reply=new Reply(Reply.Code.NOT_FOUND, new StringContent(e));
                    reply.prepare();
                }
                if (send()) {
                    sk.interestOps(SelectionKey.OP_WRITE);
                } else {
                    if (cio.shutdown()) {
                        cio.close();
                        reply.release();
                    }
                }
            } else {
                if (!send()) {
                    if (cio.shutdown()) {
                        cio.close();
                        reply.release();
                    }
                }
            }
        } catch (IOException e) {
            String m=e.getMessage();
            if (!m.equals("Broken pipe")&&!m.equals("Connection reset by peer")) {
                System.err.println("RequestHandler: "+e.toString());
            }
            try {
                cio.shutdown();
            } catch (IOException ignored) {

            }
            cio.close();
            if (reply!=null) {
                reply.release();
            }
        }
    }
}
