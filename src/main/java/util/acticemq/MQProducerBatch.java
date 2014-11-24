package util.acticemq;


import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;

import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;

public class MQProducerBatch {

	private static Logger logger = Logger.getLogger(MQProducerBatch.class.getName());

	ConnectionFactory connectionFactory;
	Connection connection;
	Session session;
	Destination dest;
//	MessageConsumer consumer;
	MessageProducer producer;
	
	public int consumeCounter=0;
	
	public void init(String url, String channelName) throws JMSException {

		connectionFactory = new ActiveMQConnectionFactory(url);
		connection = connectionFactory.createConnection();
		connection.start();

		session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		
	
		dest = session.createQueue(channelName);	
		producer=session.createProducer(dest);
		
	}

	public void produceOne(String textMsg) throws Exception {

		try {		
				
			TextMessage	textMessage=session.createTextMessage();			
			textMessage.setText(textMsg);
			producer.send(textMessage);

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
		
		
		String activeMQURL = "tcp://bje2b11.space.163.org:61616";
		String channelName = "MobileClickQueue";
		
		MQProducerBatch producer = new MQProducerBatch();
	
		int minThreadNum=2;
		int maxThreadNum=4;
		int taskQueueSize=100;
		
		producer.init(activeMQURL, channelName);

		//MutiThreadMessageHandlerWarper mutiThreadMessageHandlerWarper=new MutiThreadMessageHandlerWarper(minThreadNum,maxThreadNum,taskQueueSize,simpleMessageHandler);
		String textMsg="a,v,c,d";
		producer.produceOne(textMsg);
		producer.close();
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
