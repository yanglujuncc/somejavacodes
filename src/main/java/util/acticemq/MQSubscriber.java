package util.acticemq;


import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;

public class MQSubscriber {

	private static Logger logger = Logger.getLogger(MQSubscriber.class.getName());

	ConnectionFactory connectionFactory;
	Connection connection;
	Session session;
	Destination dest;
//	MessageConsumer consumer;
	 MessageConsumer consumer;
	
	public int consumeCounter=0;
	
	public void init(String url, String topicName) throws JMSException {

		connectionFactory = new ActiveMQConnectionFactory(url);
		connection = connectionFactory.createConnection();
		connection.start();

		session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		
	
		dest = session.createTopic(topicName);	
		consumer=session.createConsumer(dest);
	

	}
	public Message consume() throws JMSException {
		
		Message message = consumer.receive();
	
		 logger.info("consume got a message :");
		return message;
	
	}
	public void consumeLoop(MessageHandler messageHandler, long maxWait) {

		while (true) {
			try {

				Message message = consumer.receive(maxWait);
				// logger.info("consume :"+counter);

				if (message != null) {

					messageHandler.handleMessage(message);
					consumeCounter++;

				} else {

					logger.info("receive noting, maxWait :" + maxWait + "'ms");
				//	messageHandler.handleMessage(null);
				}

			} catch (JMSException e) {

				e.printStackTrace();
				System.exit(1);
			}
		}

	}

	public void close() throws JMSException {
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
		//String activeMQURL ="tcp://localhost:61616";
	//	String channelName="QNLogQueue2"+"";
		
		
		String activeMQURL = "tcp://app-127.photo.163.org:61616";
		String channelName = "MobileClickModelSnapshot";
		
		MQSubscriber subscriber = new MQSubscriber();
	
		int minThreadNum=2;
		int maxThreadNum=4;
		int taskQueueSize=100;
		
		
		subscriber.init(activeMQURL, channelName);
		SimpleMessageHandler simpleMessageHandler = new SimpleMessageHandler();
		//MutiThreadMessageHandlerWarper mutiThreadMessageHandlerWarper=new MutiThreadMessageHandlerWarper(minThreadNum,maxThreadNum,taskQueueSize,simpleMessageHandler);
		
		
		Thread.sleep(10000);
		
		
		subscriber.consumeLoop(simpleMessageHandler, 5000);
		
		subscriber.close();
		// messageConsum();
		// String channelName="my-topick";
		// new Thread(new subRunable()).start();
		// messageSub( channelName);
		// messagePut(channelName);
		// 10   302/sec
		// 100  1689/sec
		// 500  1865/sec
		
	}
}
