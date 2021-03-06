package concurrent;

import java.util.concurrent.atomic.AtomicInteger;

public class TestVolatile {

	public volatile static int count = 0;
	//public static int count = 0;
	
	public static AtomicInteger aInteger=new AtomicInteger(0);
	
	public static void inc() {

		// 这里延迟1毫秒，使得结果明显
		for (int i = 0; i < 1000; i++) {
			count++;
			//aInteger.getAndAdd(1);
		//	aInteger.getAndAdd(1);
		//	aInteger.
		}

	}	
	public static int get() {

		// 这里延迟1毫秒，使得结果明显
		
		return count;
		//return aInteger.get();
	}

	public static void main(String[] args) throws Exception {

		// 同时启动1000个线程，去进行i++计算，看看实际结果
		int size = 100;
		Thread[] threads = new Thread[size];
		
		for (int i = 0; i < size; i++) {
			
			threads[i] = new Thread(new Runnable() {
				public void run() {
					TestVolatile.inc();
				}
			});
			
			threads[i].start();
		}

		for (int i = 0; i < size; i++) {
			threads[i].join();
		}

		// 这里每次运行的值都有可能不同,可能为1000
		System.out.println("运行结果:Counter.count=" + TestVolatile.get());
	}
}
