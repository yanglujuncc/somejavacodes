package testThreadPool;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class TestThreadPool {

	private static int produceTaskSleepTime = 1;
	private static int produceTaskMaxNumber = 10;

	/**
	 * 线程池执行的任务
	 */
	public static class ThreadPoolTask implements Runnable {
		// 保存任务所需要的数据
		private Object threadPoolTaskData;

		ThreadPoolTask(Object tasks) {
			this.threadPoolTaskData = tasks;
		}

		public void run() {
			// 处理一个任务，这里的处理方式太简单了，仅仅是一个打印语句
			System.out.println("start .." + threadPoolTaskData);
			try {
				// 便于观察，等待一段时间
				Thread.sleep(2000);
			} catch (Exception e) {
				e.printStackTrace();
			}
			// threadPoolTaskData = null;

			System.out.println("end .." + threadPoolTaskData);
		}

		public Object getTask() {
			return this.threadPoolTaskData;
		}
	}

	public static void main(String[] args) {
		// 构造一个线程池
		/*
		 * ThreadPoolExecutor threadPool = new ThreadPoolExecutor(2, 4, 3,
		 * TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(3), new
		 * ThreadPoolExecutor.DiscardOldestPolicy());
		 */
		ThreadPoolExecutor threadPool = new ThreadPoolExecutor(2, 4, 3, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>(3), new ThreadPoolExecutor.DiscardOldestPolicy());

		// BlockingQueue<Runnable> blockingQueue=new
		// ArrayBlockingQueue<Runnable>(3);

		for (int i = 1; i <= produceTaskMaxNumber; i++) {
			try {
				// 产生一个任务，并将其加入到线程池
				String task = "task@ " + i;
				System.out.println("put " + task);

				threadPool.execute(new ThreadPoolTask(task));

				// 便于观察，等待一段时间
				Thread.sleep(produceTaskSleepTime);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		System.out.println("active:" + threadPool.getActiveCount() + " queue:" + threadPool.getQueue().size());

		try {
			threadPool.shutdown();
			threadPool.awaitTermination(Long.MAX_VALUE, TimeUnit.HOURS);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
