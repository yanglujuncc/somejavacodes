package concurrent;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;


public class TestFuture {

	class MyFuture<V> implements Future<V>{

		public boolean cancel(boolean mayInterruptIfRunning) {
			// TODO Auto-generated method stub
			return false;
		}

		public V get() throws InterruptedException, ExecutionException {
			// TODO Auto-generated method stub
			return null;
		}

		public V get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
			// TODO Auto-generated method stub
			return null;
		}

		public boolean isCancelled() {
			// TODO Auto-generated method stub
			return false;
		}

		public boolean isDone() {
			// TODO Auto-generated method stub
			return false;
		}
		
	}
	
	public static void main(String[] args){
		
		//jdk的FutureTask 比较适合 计算型 任务，还是会阻塞线程，
		//想要不阻塞线程（如等待IO的任务），还得自己实现Future
		
		ExecutorService executor = Executors.newSingleThreadExecutor();   
		FutureTask<String> future =   
		       new FutureTask<String>(new Callable<String>() {
		    	   //使用Callable接口作为构造参数   
		         public String call() {
					return "hello";   
		           //真正的任务在这里执行，这里的返回值类型为String，可以为任意类型   
		        	 
		       }});   
		executor.execute(future);   
		//在这里可以做别的任何事情   
		String result=null;
		try {   
		    result = future.get(5000, TimeUnit.MILLISECONDS); //取得结果，同时设置超时执行时间为5秒。同样可以用future.get()，不设置执行超时时间取得结果   
		} catch (InterruptedException e) {   
			future.cancel(true);   
		} catch (ExecutionException e) {   
			future.cancel(true);   
		} catch (TimeoutException e) {   
			future.cancel(true);   
		} finally {   
		    executor.shutdown();   
		}  
		System.out.println("result:"+result);
	}
}
