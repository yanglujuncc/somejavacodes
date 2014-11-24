package httpserver.test;


import java.io.IOException;
import java.net.InetSocketAddress;
import com.sun.net.httpserver.HttpContext;
import com.sun.net.httpserver.HttpServer;

public class TestHttpServer {

	

	public static void main(String[] args) throws IOException {

		//允许最大连接数
		int backLog = 100;
		HttpServer server = HttpServer.create(new InetSocketAddress("localhost", 8088), backLog);

		HttpContext sleepContext_1 = server.createContext("/sleep1", new SleepHandler());
		HttpContext sleepContext_2 = server.createContext("/sleep2", new SleepHandler());
	//	HttpContext context = server.createContext("/search", new SearchHandler());
		context.getFilters().add(new ParameterFilter("utf-8"));
		server.setExecutor(null); // creates a default executor
		server.start();
		System.out.println("OK");

	}

}
