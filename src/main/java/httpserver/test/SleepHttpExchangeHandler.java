package httpserver.test;

import java.io.IOException;

import com.sun.net.httpserver.HttpExchange;

public class SleepHttpExchangeHandler implements HttpExchangeHandler{

	@Override
	public void handleExchange(HttpExchange xchg) throws IOException {

		try {
			System.out.println(Thread.currentThread().getName()+" sleep 10s...");
			Thread.sleep(10000L);
		} catch (InterruptedException e) {
			
			e.printStackTrace();
		}
		
	}

}
