package java_spc.netty.bio;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

/**
 * 
 * @author SPC
 * 2017年7月19日
 * 同步阻塞I/O获取时间  
 * 服务端
 */
public class SBTimeServer {
	private int port;
	
	public SBTimeServer(){
		this.port=8080;
	}
	
	public SBTimeServer(int port){
		this.port=port;
	}
	public void run(){
		ServerSocket server=null;
		try {
			server=new ServerSocket(port);
			System.out.println("The time server is start in port");
			Socket socket=null;
			while(true){
				socket=server.accept();
				new Thread(new SBHandler(socket)).start();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(server!=null){
				System.out.println("The time Server close");
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
		SBTimeServer server=new SBTimeServer();
		server.run();
	}
}

class SBHandler implements Runnable{
	private Socket socket;
	
	public SBHandler(Socket socket) {
		this.socket=socket;
	}
	
	@Override
	public void run() {
		BufferedReader in=null;
		PrintWriter out=null;
		try {
			in=new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
			out=new PrintWriter(this.socket.getOutputStream(),true);
			String currentTime=null;
			String body=null;
			while(true){
				body=in.readLine();
				if(body==null)
					break;
				System.out.println("The time server receive order :"+body);
				currentTime="QUERY TIME ORDER".equalsIgnoreCase(body)?new Date(System.currentTimeMillis()).toString():"BAD ORDER";
				out.println(currentTime);
			}
		} catch (Exception e) {
			if(in!=null){
				try {
					in.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
			if(out!=null){
				out.close();
				out=null;
			}
			if(this.socket!=null){
				try {
					this.socket.close();
				} catch (IOException e2) {
					e2.printStackTrace();
				}
				this.socket=null;
			}
		}
	}
}
