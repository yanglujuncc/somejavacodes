package utils.mq.activemq;

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

public class ActiveMQConsumer  {

	private static Logger logger = Logger.getLogger(ActiveMQConsumer.class.getName());

	ConnectionFactory connectionFactory;
	Connection connection;
	Session session;
	Destination dest;
	MessageConsumer consumer;


//	long consumCounter = 0;

	boolean running = true;

	String mqUrl;
	String mqChannelName;

	public ActiveMQConsumer( String mqUrl, String mqChannelName) {
		
		this.mqUrl = mqUrl;
		this.mqChannelName = mqChannelName;
	}


	public void initMQ() throws JMSException {

		connectionFactory = new ActiveMQConnectionFactory(mqUrl);
		connection = connectionFactory.createConnection();
		connection.start();

		session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		dest = session.createQueue(mqChannelName);
		
		consumer = session.createConsumer(dest);
		
	}

	public void setStop() {
		running = false;
	}

	public Message getMessage(long maxWait) throws JMSException{
		return  consumer.receive(maxWait);
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
		// String activeMQURL ="tcp://localhost:61616";
		// String channelName="QNLogQueue2"+"";

		String activeMQURL = "tcp://bje2b9.space.163.org:61616";
		// String activeMQURL ="tcp://localhost:61616";
		String channelName = "QNLogQueue";


	}
}
