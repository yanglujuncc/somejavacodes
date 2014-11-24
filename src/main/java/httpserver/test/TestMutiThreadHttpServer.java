package httpserver.test;

import java.io.IOException;
import java.net.InetSocketAddress;

import com.sun.net.httpserver.HttpContext;
import com.sun.net.httpserver.HttpServer;

public class TestMutiThreadHttpServer {

	public static void main(String[] args) throws IOException {

		//允许最大连接数
		int backLog = 100;
		HttpServer server = HttpServer.create(new InetSocketAddress("localhost", 8088), backLog);
		
		int threadNum=10;
		int maxThreadNum=10;
		int taskQueueSize=1000;
		HttpExchangeHandler exchangeHandler1=new SleepHttpExchangeHandler();
		HttpExchangeHandler exchangeHandler2=new SleepHttpExchangeHandler();
		
		HttpContext sleepContext_1 = server.createContext("/sleep1",new MutiTheadHttpHandler(threadNum,maxThreadNum,taskQueueSize,exchangeHandler1));
		HttpContext sleepContext_2 = server.createContext("/sleep2",new MutiTheadHttpHandler(threadNum,maxThreadNum,taskQueueSize,exchangeHandler1));
	//	HttpContext context = server.createContext("/search", new SearchHandler());
	//	context.getFilters().add(new ParameterFilter("utf-8"));
		server.setExecutor(null); // creates a default executor
		server.start();
		System.out.println("OK");

	}

}
