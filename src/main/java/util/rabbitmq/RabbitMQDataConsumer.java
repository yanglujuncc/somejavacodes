package util.rabbitmq;









import org.apache.log4j.xml.DOMConfigurator;
import  org.slf4j.Logger;
import  org.slf4j.LoggerFactory;

//import org.apache.log4j.xml.DOMConfigurator;






//import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.QueueingConsumer;
import com.rabbitmq.client.QueueingConsumer.Delivery;



public class RabbitMQDataConsumer  extends Thread {

	protected static Logger logger = LoggerFactory.getLogger(RabbitMQDataConsumer.class.getName());
	
	
	
	
	private String uri;
	private String queueName;
	
	//private int poolsize;
	private final int masterQueueLen = 500;

	//private DataHandler dataHandler;
	private RabbitMQDataHandler dataHandler;
	private long maxWait=1000;
	
	private boolean running=false;
	
	private long consumCounter=0;
	//private Thread comsumeThread;
	
	public RabbitMQDataConsumer(String uri,String queueName,String threadName,int maxWait,RabbitMQDataHandler dataHandler){
		this.uri=uri;
		this.queueName=queueName;
		this.dataHandler=dataHandler;
		this.maxWait=maxWait;
		this.setName(threadName);
	//	this.dataHandler=dataHandler;
	}
	

	public void init() throws Exception {
		
		
		logger.info("uri=" + uri);
		logger.info("queueName=" + queueName);
		
	
		
	
		
	}
	
	public long getConsumCounter(){
		return consumCounter;
	}
	public void consumeLoop() throws Exception {
		
		running=true;
		
		ConnectionFactory factory = new ConnectionFactory();
		factory.setUri(uri);
		
		Connection connection=null;
		Channel channel = null;
		
		
		logger.info ("consumeLoop begin... ");
		try{
			connection = factory.newConnection();
			channel = connection.createChannel();
			if(channel == null) {
				logger.error("can not get a channel");
				throw new RuntimeException("null channel");
			}
			//channel.queueDeclare(queueName, false, false, false, null);
			
			QueueingConsumer consumer = new QueueingConsumer(channel);
		    channel.basicConsume(queueName, true, consumer); 
		  
			while (running) {  
				
					//logger.info ("wait...  for message ,maxWait:"+maxWait+"'ms");
					
				  	Delivery delivery = consumer.nextDelivery(maxWait);   
					if(delivery!=null)
					{				
					//	logger.info("got delivery :"+count);
							if(dataHandler!=null)
								dataHandler.handleData(delivery.getBody());
							
							consumCounter++;
												
					}else{
						logger.info ("null delivery  ,wait "+maxWait+"'ms"+" for message ");
					
					}
					
					//	String dataStr=new String(data);
					//	System.out.println(dataStr);
			}
			
			/**
			 * do stop running
			 */
			logger.info (" call dataHandler stopDo()");
			dataHandler.stopDo();
			
			
		}catch(Exception e){
			e.printStackTrace();
			System.exit(1);
			
		}finally {
			if(channel != null&&channel.isOpen())
				channel.close();
			
			if(connection != null&&connection.isOpen())
				connection.close();
			
		
		}
		
		
	}
	
	public void setStop(){
		running=false;
	}
	
	@Override
	 public void run() {
    
		try {
			consumeLoop();
		} catch (Exception e) {
			// TODO Auto-generated catch block
		
			e.printStackTrace();
			System.exit(1);
		}
		logger.info(" Terminate . return 0");
		System.exit(0);
  }
	
	public static void  main(String[] args) throws Exception{
		//amqp://app-68.photo.163.org:5672
		//String url="123.58.181.213:5672";

		//String url=args[0];
	//	String queueName=args[1];
		DOMConfigurator.configureAndWatch("conf/log4j.xml");
		
	
		
		String url="amqp://guest:guest@app-118.photo.163.org:5672";
		String queueName="recsys.news.daily";
		//String mobileQueueName="mobile.app11";
		
		
		RabbitMQDataConsumer aRabbitMQLogConsumer_pris=new RabbitMQDataConsumer(url,queueName,"consumer_0",1000,new SimpleRabbitMQDataHandler());
	//	RabbitMQConsumer aRabbitMQLogConsumer_mobile=new RabbitMQConsumer(url,mobileQueueName,"consumer_mobile");
		
		aRabbitMQLogConsumer_pris.init();
	//	aRabbitMQLogConsumer_mobile.init();
		
		aRabbitMQLogConsumer_pris.start();
		//aRabbitMQLogConsumer_mobile.start();
		
		System.out.println("Consumer running ... ");
	}
}
