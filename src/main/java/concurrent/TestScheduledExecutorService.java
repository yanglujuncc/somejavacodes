package concurrent;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class TestScheduledExecutorService {
	  public static void main(String[] args) throws Exception{
		  
	        ScheduledExecutorService execService =   Executors.newScheduledThreadPool(3);
	        
	        execService.scheduleAtFixedRate(new Runnable() {
	            public void run() {
	                System.out.println(Thread.currentThread().getName()+" -> "+System.currentTimeMillis());
	                try {
	                    Thread.sleep(2000L);
	                } catch (Exception e) {
	                    e.printStackTrace();
	                }
	            }
	        }, 1, 1, TimeUnit.SECONDS);
	        //
	        execService.scheduleWithFixedDelay(new Runnable() {
	            public void run() {
	                System.out.println(Thread.currentThread().getName()+" -> "+System.currentTimeMillis());
	                try {
	                    Thread.sleep(2000L);
	                } catch (Exception e) {
	                    e.printStackTrace();
	                }
	            }
	        }, 1, 1, TimeUnit.SECONDS);
	        Thread.sleep(5000L);
	        execService.shutdown();
	    }
}
