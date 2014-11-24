package httpserver.test;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import com.sun.net.httpserver.HttpContext;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;



public class HttpRequestHandlerTemp {

	
	
	
	ThreadPoolExecutor threadPool;
	
	public HttpRequestHandlerTemp(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue){
		threadPool = new ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime,  
				unit, workQueue,  
                new ThreadPoolExecutor.DiscardOldestPolicy());  
	}
	

	
	
	public void addTask(HttpExchange xchg){
		Task newTask=new Task(xchg);
		 System.out.println("addTask..  newTask.taskID:" +newTask.taskID);  
	     threadPool.execute(newTask);  
	     
	}
	
   public static class Task implements Runnable{  
       // 保存任务所需要的数据  
	   static int nextTaskID;
	   
       private HttpExchange xchg;  
       private int taskID;  
 
       Task(HttpExchange xchg) {  
           this.xchg = xchg;  
           taskID=nextTaskID;
           nextTaskID++;
       }  
 
       public void run() {  
           // 处理一个任务，这里的处理方式太简单了，仅仅是一个打印语句  
           System.out.println("run..."+taskID+" start .." +xchg.getRemoteAddress());  
           try {  
               //便于观察，等待一段时间  
        	   System.out.println("run..."+taskID+" Sleep .. 100's" );  
               Thread.sleep(100000);  
               sendResult( xchg, "taskID:"+taskID);
               
           } catch (Exception e) {  
               e.printStackTrace();  
           }  
         //  threadPoolTaskData = null;  
           
     
           System.out.println("run..."+taskID+" end .." +xchg.getRemoteAddress());  
       }  
 
    
   } 
	public static void sendResult(HttpExchange xchg,String msg){
		
		System.out.println("run..."+"Send Result To :"+xchg.getRemoteAddress()+" msg:"+msg);

		try {
			byte[] datas = msg.getBytes("utf-8");
			xchg.sendResponseHeaders(200, datas.length);
			OutputStream os = xchg.getResponseBody();
			os.write(datas);
			os.close();

		} catch (Exception e) {
			
			e.printStackTrace();
		}

	
	}
	
	public static void main(String[] args) throws IOException {

		//允许最大连接数
		int backLog = 2;
		HttpServer server = HttpServer.create(new InetSocketAddress("localhost", 8080), backLog);

		HttpContext context = server.createContext("/search", new SearchHandler());
		context.getFilters().add(new ParameterFilter("utf-8"));
		server.setExecutor(null); // creates a default executor
		server.start();
		System.out.println("OK");

	}
}
