package testThreadPool;

import java.util.Date;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;


/**
 * @see http://sjsky.iteye.com
 * @author michael sjsky007@gmail.com
 */
public class JavaThreadPool {

    /**
     * @param args
     */
    public static void main(String[] args) {

        ThreadPoolExecutor threadPool = new ThreadPoolExecutor(3, 5, 60,
                TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(10),
                new ThreadPoolExecutor.CallerRunsPolicy());
        
        
        for (int i = 0; i < 100; i++) {
          
            SimplePrintJob job = new SimplePrintJob("job_" + i);
            threadPool.execute(job);
            System.out.println("[ main ] add job_" + i + " at:" + new Date());
        }
        System.out.println("execute all job");
    	
		try {
			threadPool.shutdown();
			threadPool.awaitTermination(Long.MAX_VALUE, TimeUnit.HOURS);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

        System.out.println("main program end-----------");
    }

}
