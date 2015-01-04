package concurrent;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;

public class TestAQS2 {

	private static class Sync extends AbstractQueuedSynchronizer {

	//	static final int WaitingResult = 1;
		static final int StateGotResult = 1;

		public Sync() {
			setState(0);
		}

		protected final boolean tryAcquire(int acquires) {

		//	System.out.println("tryAcquire() ");

			int c = getState();
			
			if(c==acquires)
				return true;		
			else
				return false;
		}

		protected final boolean tryRelease(int releases) {
			
			setState(releases);
			
			return true;
		}

		protected int tryAcquireShared(int acquires) {
	//		System.out.println("tryAcquireShared() ");

	
			int c = getState();
			
			if(c==acquires)
				return 1;		
			else
				return -1;

		}

		protected boolean tryReleaseShared(int releases) {
			setState(releases);
			
			return true;
		}

	}

	public static class MyFuture<String> implements Future<String> {

		String result;
		private final Sync sync = new Sync();

		public MyFuture() {
			result = null;
		}

		public void setResult(String result){
			this.result=result;
		//	sync.release(Sync.GotResult);
			//sync.release(Sync.GotResult);
			sync.releaseShared(Sync.StateGotResult);
			//sync.set
		}

		public boolean cancel(boolean mayInterruptIfRunning) {

			return false;
		}

		public boolean isCancelled() {

			return false;
		}

		public boolean isDone() {
			if (result == null)
				return false;
			else
				return true;
		}

		public String get() throws InterruptedException, ExecutionException {

		//	sync.acquire(Sync.GotResult);
			sync.acquireShared(Sync.StateGotResult);  //
			
		//	sync.releaseShared(arg)
			
			return result;
		}

		public String get(long timeout, TimeUnit unit) throws TimeoutException {

			long nanosTimeout = unit.toNanos(timeout);
			System.out.println("nanosTimeout:"+nanosTimeout);
			try {
				boolean tryResult = sync.tryAcquireNanos(Sync.StateGotResult, nanosTimeout);
				if(tryResult==false){
					throw new TimeoutException();
				}
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return result;
		}

	}

	public static void main(String[] args) throws InterruptedException, Exception {

		
		//用 AbstractQueuedSynchronizer 实现了 类似 notifyall 的功能
		
		//System.out.println(TimeUnit.MILLISECONDS.toNanos(1));
		final MyFuture<String> myFuture = new MyFuture<String>();

		class GetResultRunnber implements Runnable{

			public void run() {
				String result = null;
			
				System.out.println(Thread.currentThread()+":getting...");			
				try {					
					 result = myFuture.get();
				} catch (Exception e) {
					e.printStackTrace();
				}	
				System.out.println(Thread.currentThread()+":got result:" + result);				
				
			}
			
		}
		class GetResultRunnber2 implements Runnable{

			public void run() {
				String result = null;
				System.out.println(Thread.currentThread()+":getting...");			
				try {					
					 result = myFuture.get(1000,TimeUnit.MILLISECONDS);
				} catch (TimeoutException e) {
					System.out.println(Thread.currentThread()+": timeout");								
				}	
				System.out.println(Thread.currentThread()+":got result:" + result);				
				
			}
			
		}
		
		Thread[] threads = new Thread[4];
		
		threads[0]=new Thread(new GetResultRunnber(),"1");
		threads[1]=new Thread(new GetResultRunnber(),"2");
		threads[2]=new Thread(new GetResultRunnber(),"3");
		threads[3]=new Thread(new GetResultRunnber2(),"4");
		
		threads[0].start();
		threads[1].start();
		threads[2].start();
		threads[3].start();
	
		Thread.sleep(5000);
		
	
		myFuture.setResult("hello");
		System.out.println(Thread.currentThread()+":set result ok");
		
		Thread.sleep(3000);
		
		System.out.println(Thread.currentThread()+":got result:" + myFuture.get());		
		
	}
}
