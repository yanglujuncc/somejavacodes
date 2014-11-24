package httpserver;


import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
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

public class SearchHandler implements HttpHandler {

	
	@Override
	public void handle(HttpExchange xchg) throws IOException {

		System.out.println("#handle called...");

		
		Map<String, Object> params = (Map<String, Object>) xchg.getAttribute("parameters");
		for (Entry<String, Object> entry : params.entrySet()) {
			System.out.println("Param " + entry);
		}
		// System.out.println("called..." + xchg.getProtocol());
		// System.out.println("called..." + xchg.getRequestMethod());

		// byte[] datas=response.toString().getBytes("utf-8");
	
		String jsonString = JSON.toJSONString(params);
		byte[] datas = jsonString.getBytes("utf-8");

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

		HttpContext context = server.createContext("/search", new SearchHandler());
		context.getFilters().add(new ParameterFilter("utf-8"));
		server.setExecutor(null); // creates a default executor
		server.start();
		System.out.println("HttpServer start OK");

	}

}
