package httpserver;


import java.io.IOException;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;

import com.sun.net.httpserver.HttpContext;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;


public class HelloworldHttpServer {

	public static class HelloWordHandler implements HttpHandler {

		@Override
		public void handle(HttpExchange xchg) throws IOException {
			// TODO Auto-generated method stub
			try {
				String msg="hello world.";
				
				System.out.println("sleep 1000 ms");
				Thread.sleep(1000*1000);
				
				System.out.println("weekup..");
				byte[]returnDatas=msg.getBytes("utf-8");
				xchg.sendResponseHeaders(200, returnDatas.length);
				OutputStream os = xchg.getResponseBody();
				os.write(returnDatas);
				os.close();

			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("# error when send response   ...");
			}
			
		}
		
	}

	public static void main(String[] args) throws IOException {

		String serverIP=InetAddress.getLocalHost().getHostAddress();
		int port=8081;
		int backLog=10;
	
		HttpServer server= HttpServer.create(new InetSocketAddress(serverIP,port), backLog);

	
		HttpContext contextBookClinet = server.createContext("/hello", new HelloWordHandler());
		
	
		
		server.setExecutor(null); // creates a default executor
		server.start();
		
		System.out.println("  MonkDataServer start ...");
		System.out.println("  serverIP:"+serverIP);
		System.out.println("serverPort:"+port);
		System.out.println("   backLog:"+backLog);
		//允许最大连接数
		
		System.out.println("OK");

	}

}
