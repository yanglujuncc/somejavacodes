package util.acticemq;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.Session;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;

public class MQConsumeRunner implements Runnable {

	private static Logger logger = Logger.getLogger(MQConsumeRunner.class.getName());

	ConnectionFactory connectionFactory;
	Connection connection;
	Session session;
	Destination dest;
	MessageConsumer consumer;

	MessageHandler messageHandler;
	long maxWait = 10000;

	long consumeCounter = 0;

	boolean running = true;

	String mqUrl;
	String mqChannelName;

	public MQConsumeRunner(MessageHandler messageHandler, long maxReceiveWait, String mqUrl, String mqChannelName) {
		this.messageHandler = messageHandler;
		this.maxWait = maxReceiveWait;
		this.mqUrl = mqUrl;
		this.mqChannelName = mqChannelName;
	}
	
	public long getConsumeCounter(){
		return consumeCounter;
	}

	private void initMQ(String url, String channelName) throws JMSException {

		
			connectionFactory = new ActiveMQConnectionFactory(url);
			connection = connectionFactory.createConnection();
			connection.start();

			session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			dest = session.createQueue(channelName);
			consumer = session.createConsumer(dest);
		

	}

	
	public void setStop(){
		running=false;
	}
	
	public void run() {
		logger.info(" run start");

		// long timeEnd=System.currentTimeMillis();
		
		running=true;
		
		logger.info(" init MQ connection");
		try {
			initMQ(mqUrl,mqChannelName);
		} catch (JMSException e1) {
			e1.printStackTrace();
			System.exit(1);
		}
		
		
		logger.info(" call messageHander doStart() ...");		
		messageHandler.doStart();
		
		
		logger.info(" start running consume loop ...");
		
		Thread currentThread=Thread.currentThread();
		while (running) {
			try {

				Message message = consumer.receive(maxWait);
				// logger.info("consume :"+counter);
				if (message != null) {

					messageHandler.handleMessage(message);
					consumeCounter++;
				} else {
					// counter=0;
					// timeBegin=System.currentTimeMillis();
					logger.info("receive nothing, maxWait :" + maxWait + "'ms");

				}
				
				
				if (currentThread.isInterrupted()) {
					logger.info("isInterrupted true. ");
					break;
				}
				
				if(consumeCounter%1000==0){
					try {
						Thread.sleep(5000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}

			} catch (JMSException e) {

				e.printStackTrace();
				System.exit(1);
			}
		}
		
		logger.info(" call messageHander doFinish() ...");
		
		messageHandler.doFinish();
		
		logger.info(" close MQ connection ...");
		try {
			closeMQ();
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		logger.info(" run finish");
	}

	private void closeMQ() throws JMSException {
		consumer.close();
		session.close();
		connection.close();
	}

	public static void main(String[] args) throws Exception {

		// http://localhost:8161/admin/queues.jsp admin admin
		// http://localhost:8161/admin/topics.jsp admin admin
		// guest
		//
		DOMConfigurator.configureAndWatch("conf/log4j.xml");
		// String activeMQURL ="tcp://localhost:61616";
		// String channelName="QNLogQueue2"+"";

		String activeMQURL = "tcp://bje2b9.space.163.org:61616";
		// String activeMQURL ="tcp://localhost:61616";
		String channelName = "QNLogQueue";

	
		int minThreadNum = 2;
		int maxThreadNum = 4;
		int taskQueueSize = 100;

		
		SimpleMessageHandler simpleMessageHandler = new SimpleMessageHandler();
		// MutiThreadMessageHandlerWarper mutiThreadMessageHandlerWarper=new
		// MutiThreadMessageHandlerWarper(minThreadNum,maxThreadNum,taskQueueSize,simpleMessageHandler);
		MQConsumeRunner consumer = new MQConsumeRunner(simpleMessageHandler,5000,activeMQURL,channelName);
	
		
		// messageConsum();
		// String channelName="my-topick";
		// new Thread(new subRunable()).start();
		// messageSub( channelName);
		// messagePut(channelName);
		// 10 302/sec
		// 100 1689/sec
		// 500 1865/sec

	}
}
