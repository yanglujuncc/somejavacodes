package httpserver.test;

import java.io.IOException;

import com.sun.net.httpserver.HttpExchange;

public interface HttpExchangeHandler {

	public void handleExchange(HttpExchange xchg) throws IOException ;
}
