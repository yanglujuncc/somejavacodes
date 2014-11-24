package httpserver.test;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import com.sun.net.httpserver.HttpContext;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

public class TestBackLog implements HttpHandler {

	HttpRequestHandlerTemp handleEngine;

	public TestBackLog(HttpRequestHandlerTemp handleEngine) {
		this.handleEngine = handleEngine;
	}

	@Override
	public void handle(HttpExchange xchg) throws IOException {

		System.out.println("#handle called...");
		handleEngine.addTask(xchg);

		System.out.println("#handle end...");
	}

	
	public static void main(String[] args) throws IOException {
		
		int corePoolSize=2;
		int maximumPoolSize=4;
		long keepAliveTime=2;
		TimeUnit unit=TimeUnit.SECONDS;
		BlockingQueue<Runnable> workQueue=new ArrayBlockingQueue<Runnable>(1000); 
		HttpRequestHandlerTemp aHttpRequestHandlerTemp=new HttpRequestHandlerTemp(corePoolSize,maximumPoolSize,keepAliveTime,unit,workQueue);
		TestBackLog aTestBackLog=new TestBackLog(aHttpRequestHandlerTemp);
		
		
		// 允许最大连接数
		String serverIP = InetAddress.getLocalHost().getHostAddress();
		int port = 8081;
		int backLog = 1;

		HttpServer server = HttpServer.create(new InetSocketAddress(serverIP, port), backLog);


		HttpContext context = server.createContext("/similar", aTestBackLog);
		context.getFilters().add(new ParameterFilter("utf-8"));
		server.setExecutor(null); // creates a default executor
		server.start();
		
		
		System.out.println("  MonkDataServer start ...");
		System.out.println("  serverIP:"+serverIP);
		System.out.println("serverPort:"+port);
		System.out.println("   backLog:"+backLog);
	
		
		System.out.println("OK");

	}
}
