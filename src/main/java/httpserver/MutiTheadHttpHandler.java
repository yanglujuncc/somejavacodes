package httpserver;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;

import com.sun.net.httpserver.HttpContext;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;



public class MutiTheadHttpHandler implements HttpHandler {

	protected static Logger logger = Logger.getLogger(MutiTheadHttpHandler.class.getName());


	ThreadPoolExecutor threadPool=null;
	int taskNum=0;
	
	HttpHandler exchangeHandler;
	
	public MutiTheadHttpHandler(int threadNum,int maxThreadNum,int taskQueueSize,HttpHandler exchangeHandler){
		this.exchangeHandler=exchangeHandler;
		threadPool= new ThreadPoolExecutor(threadNum, maxThreadNum, 300, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(taskQueueSize),  new ThreadPoolExecutor.DiscardOldestPolicy());  
		logger.info("Create ThreadPool :"+"CorePoolSize:"+threadNum+" MaxPoolSize"+threadNum*2+" KeepAlice:1min"+" TaskQueue:"+taskQueueSize+" Police:DiscardOldestPolicy");
	
		
	}
	
	
	@Override
	public void handle(HttpExchange xchg) throws IOException {
		
		  int taskID=++taskNum;
		  logger.info("MutiTheadHttpHandler called  taskID:"+taskID+", ClientAddress:"+  xchg.getRemoteAddress());  
		
		  threadPool.execute(new ExchangeTask(xchg,taskID));  
		  
		  logger.info("MutiTheadHttpHandler handel end, "+" ClientAddress:"+  xchg.getRemoteAddress());  
		  
	}
	 
   
    /** 
     * 线程池执行的任务 
     */  
    
	
	
    public  class ExchangeTask implements Runnable{  
    	
        // 保存任务所需要的数据  
        private HttpExchange xchg;  
        private int taskID=0;
        
       public ExchangeTask(){
        	
        }
       public void setHttpExchange(HttpExchange xchg){    	   
    	   	this.xchg=xchg;
       }
       public void setTaskID(int taskID){    	   
   	   	this.taskID=taskID;
      }
       public   ExchangeTask(HttpExchange xchg,int taskID) {  
            this. xchg =   xchg;  
            this.taskID=taskID;
        }  
       
       
        public void run() {  
            // 处理一个任务
        	try {
        		 logger.info("Runing , ExchangeTask, taskID:"+taskID);  
				 exchangeHandler.handle(xchg);
				 logger.info("Complete , ExchangeTask, taskID:"+taskID);  
				 
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }  
  
        public Object getTask() {  
            return this.xchg;  
        }  
    }     
     public  static class  SampleHttpExchangeHandler implements HttpHandler{

		@Override
		public void handle(HttpExchange xchg) throws IOException {
			// TODO Auto-generated method stub
			
		}
    	 
     }
    public static void main(String[] args) throws IOException {  
        // 构造一个线程池  
     
     
    	// 允许最大连接数
     		int backLog = 100;
     		String serverIP=InetAddress.getLocalHost().getHostAddress();
			int port=8080;
		
		
			HttpServer server= HttpServer.create(new InetSocketAddress(serverIP,port), backLog);


     		HttpContext context = server.createContext("/search", new MutiTheadHttpHandler(1,1,port, new SampleHttpExchangeHandler()));
     		context.getFilters().add(new ParameterFilter("utf-8"));
     		server.setExecutor(null); // creates a default executor
     		server.start();
     		System.out.println("  Server start ...");
    		System.out.println("  serverIP:" + serverIP);
    		System.out.println("serverPort:" + port);
    		System.out.println("   backLog:" + backLog);

    }  
}
