package httpserver.test;


import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import com.alibaba.fastjson.JSON;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpContext;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

public class SleepHandler implements HttpHandler {

	
	@Override
	public void handle(HttpExchange xchg) throws IOException {

		System.out.println("#handle called...");

	
		try {
			System.out.println(Thread.currentThread().getName()+" sleep 10s...");
			Thread.sleep(10L);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		URI uri=xchg.getRequestURI();
		String path=uri.getRawPath();
		String query=uri.getQuery();
		System.out.println(path+"?"+query);
		
		String acceptParams="hello world ";
		byte[] datas = acceptParams.getBytes("utf-8");

		xchg.sendResponseHeaders(200, datas.length);
		OutputStream os = xchg.getResponseBody();
		os.write(datas);
		os.close();

		System.out.println("#handle end...");
	}





	public static void main(String[] args) throws IOException {

	

		// 允许最大连接数
		int backLog = 10;
		InetSocketAddress inetSocketAddress=new InetSocketAddress("192.168.138.229", 8080);
		HttpServer server = HttpServer.create(inetSocketAddress, backLog);

		HttpContext context = server.createContext("/search", new SleepHandler());
		context.getFilters().add(new ParameterFilter("utf-8"));
		server.setExecutor(null); // creates a default executor
		server.start();
		System.out.println("HttpServer start OK");

	}

}
