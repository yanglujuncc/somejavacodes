package concurrent;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

public class TestLock {

	public static void main(String[] args) throws Exception{
		
		final	ReentrantLock rLock=new ReentrantLock();
		
	
		Thread thread = new Thread(new Runnable() {

			public void run() {
				
				System.out.println("thread start");
				 System.out.println("thread waiting lock");
				 rLock.lock();
				 System.out.println("thread got lock");
					
				try {
					Thread.sleep(5000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				 rLock.unlock();
				 
				 System.out.println("thread unlock");
				 
				 System.out.println("thread end");
					
			}

		}

		);
		thread.start();
		Thread.sleep(1000);

		System.out.println("waiting lock");
		rLock.tryLock(10000, TimeUnit .MILLISECONDS);
		System.out.println("got lock");
		 rLock.unlock();
			
	}
}
