package util.acticemq;


import java.util.Date;

import javax.jms.BytesMessage;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;


public class MQPublisher {

	private static Logger logger = Logger.getLogger(MQPublisher.class.getName());

	ConnectionFactory connectionFactory;
	Connection connection;
	Session session;
	Destination dest;
	MessageProducer producer;
	
	public int consumeCounter=0;
	
	public void init(String url, String channelName) throws JMSException {

		connectionFactory = new ActiveMQConnectionFactory(url);
		connection = connectionFactory.createConnection();
		connection.start();

		session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		
		dest = session.createTopic(channelName);
		
		producer = session.createProducer(dest);
		
	}
	public void publishBytes(byte[] byteData) throws Exception {

		try {		
			 BytesMessage  bytesMessage=session.createBytesMessage();
			 bytesMessage.writeBytes(byteData);
			
			producer.send(bytesMessage);

		} catch (JMSException e) {

			e.printStackTrace();
		}

	}
	
	


	public void close() throws JMSException {
		producer.close();
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
		
		MQPublisher publisher = new MQPublisher();
	
		int minThreadNum=2;
		int maxThreadNum=4;
		int taskQueueSize=100;
		
		publisher.init(activeMQURL, channelName);

		//MutiThreadMessageHandlerWarper mutiThreadMessageHandlerWarper=new MutiThreadMessageHandlerWarper(minThreadNum,maxThreadNum,taskQueueSize,simpleMessageHandler);
	
		
		for(int i=0;i<10;i++){
			String textMsg="i am publisher, make "+i;
			publisher.publishBytes(textMsg.getBytes("utf-8"));
			Thread.sleep(3000);
		}
		
		publisher.close();
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
