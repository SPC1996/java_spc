package java_spc.netty.bio;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author SPC
 * 2017年7月19日
 * 伪异步I/O获取时间
 * 服务端
 */
public class PSBTimeServer {
	private int port;
	
	public PSBTimeServer() {
		this.port=8080;
	}
	
	public PSBTimeServer(int port){
		this.port=port;
	}
	
	public void run(){
		ServerSocket server=null;
		try {
			server=new ServerSocket(port);
			System.out.println("The time server is start in port");
			Socket socket=null;
			PSBHandlerExecutePool singleExecutor=new PSBHandlerExecutePool();
			while(true){
				socket=server.accept();
				singleExecutor.execute(new SBHandler(socket));
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if(server!=null){
				System.out.println("The time server close");
				try {
					server.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
				server=null;
			}
		}
	}
	
	public static void main(String[] args){
		PSBTimeServer server=new PSBTimeServer();
		server.run();
	}
}

class PSBHandlerExecutePool{
	private ExecutorService executor;
	
	public PSBHandlerExecutePool() {
		this(10, 100);
	}
	
	public PSBHandlerExecutePool(int maxPoolSize,int queueSize){
		executor=new ThreadPoolExecutor(Runtime.getRuntime().availableProcessors(),
				maxPoolSize,120L,TimeUnit.SECONDS,
				new ArrayBlockingQueue<Runnable>(queueSize));
	}
	
	public void execute(Runnable task){
		executor.execute(task);
	}
}
