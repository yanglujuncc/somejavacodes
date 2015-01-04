package concurrent;


import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class TestCondition {
	
	public static void main(String[] args) throws Exception {

	//	object( args);
		condition(args);
	}
	public static void object(String[] args) throws Exception {
	

		final Object eveObj = new Object();

		Thread thread = new Thread(new Runnable() {

			public void run() {

				System.out.println("thread start");

				
				System.out.println("thread waiting lock");
				
				synchronized(eveObj){
					System.out.println("thread got lock,then wait");
					
					try {
						eveObj.wait();  //will release lock
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					System.out.println("thread be notified");

				}
	
		

				System.out.println("thread end");

			}

		}

		);
		thread.start();
		Thread.sleep(3000);

		
		synchronized(eveObj){
			System.out.println("out got lock,do nofifyAll");
			eveObj.notifyAll();
			Thread.sleep(1000);
			
			System.out.println("out release lock");
		}
		
		

		
	}
	public static void condition(String[] args) throws Exception {

		final Lock lock = new ReentrantLock();
		final Condition condition = lock.newCondition();
		final Condition condition2 =lock.newCondition();
		final Condition condition3 =lock.newCondition();

		Thread thread = new Thread(new Runnable() {

			public void run() {

				System.out.println("thread start");

				System.out.println("thread waiting lock");
				lock.lock();
				System.out.println("thread got lock");
				try {

					System.out.println("thread condition await");

					
					condition.await();  //will release lock
					
					System.out.println("thread be signaled,then sleep...");

					
				//	Thread.sleep(1000);

					// do something
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} finally {
					lock.unlock();
					System.out.println("thread unlock");

				}

				System.out.println("thread end");

			}

		}

		);
		thread.start();
		Thread.sleep(3000);

		
		lock.lock();
		System.out.println("out got lock");
		try {

			System.out.println("out condition signalAll");

			condition.signalAll();
			Thread.sleep(1000);

			// do something
		} finally {
			lock.unlock();   //unlock以后 signalAll才会发生作用， await的线程才会被唤醒
			System.out.println("out unlock");
		}

	}
}
