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

public class MQConsumer {

	private static Logger logger = Logger.getLogger(MQConsumer.class.getName());

	ConnectionFactory connectionFactory;
	Connection connection;
	Session session;
	Destination dest;
	MessageConsumer consumer;

	public long consumeCounter = 0;
	
	String url;
	String channelName;

	public void init(String url, String channelName) throws JMSException {

		connectionFactory = new ActiveMQConnectionFactory(url);
		connection = connectionFactory.createConnection();
		connection.start();

		session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		dest = session.createQueue(channelName);
		consumer = session.createConsumer(dest);

		this.channelName=channelName;
		this.url=url;
	}

	public synchronized void reConnect() throws JMSException {

		
		close();


		connectionFactory = new ActiveMQConnectionFactory(url);
		connection = connectionFactory.createConnection();
		connection.start();

		session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		dest = session.createQueue(channelName);
		consumer = session.createConsumer(dest);


	}

	public void consumeOne(ActiveMQMsgHandler messageHandler, long maxWait) throws Exception {

		try {

			Message message = consumer.receive(maxWait);
			messageHandler.handleMessage(message);
			// session.commit();

		} catch (JMSException e) {

			e.printStackTrace();
		}

	}

	public void consumeStart(final ActiveMQMsgHandler messageHandler, final long maxWait) {

		Thread consumeThread = new Thread() {
			public void run() {

				// long timeEnd=System.currentTimeMillis();

				while (true) {
					try {

						Message message = consumer.receive(maxWait);
						// logger.info("consume :"+counter);
						if (message != null) {

							messageHandler.handleMessage(message);
							consumeCounter++;
						} else {

							logger.info("receive noting, maxWait :" + maxWait + "'ms");

						}

					} catch (Exception e) {

						e.printStackTrace();
						System.exit(1);
					}
				}
			}
		};

		consumeThread.start();
	}
	private int reConnectN(int maxTry,long sleepTime){
		
							
		for(int tryCount=1;tryCount<=maxTry;tryCount++){
			try {
				reConnect();
				logger.info("reconnect mq success,try:"+tryCount);
				return 0;
			} catch (JMSException e1) {
				// TODO Auto-generated catch block
				logger.info("reconnect mq failed,try:"+tryCount+" maxTry:"+maxTry+" reconnect again after "+sleepTime+"'ms");
				e1.printStackTrace();
			
				try {			
					Thread.sleep(sleepTime);
				} catch (InterruptedException e) {
				
					e.printStackTrace();
					return -2;
				}
			}
		}
		logger.info("reconnect mq failed,try:"+maxTry+" times. then give up" );
		return -1;
	}
	public void consumeLoop(ActiveMQMsgHandler messageHandler, long maxWait) {

		while (true) {
			try {

				Message message = consumer.receive(maxWait);
				// logger.info("consume :"+counter);

				if (message != null) {

					messageHandler.handleMessage(message);
					consumeCounter++;

				} else {

					logger.info("receive noting, maxWait :" + maxWait + "'ms");
					messageHandler.handleMessage(null);
				}

			} catch (Exception e) {

				e.printStackTrace();
				
				int reConnectResult=reConnectN(60,10000);
				
				if(reConnectResult!=0)
					System.exit(1);
			}
		}

	}

	public void close() {
		try {
			consumer.close();
			session.close();
			connection.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
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

		MQConsumer consumer = new MQConsumer();

		int minThreadNum = 2;
		int maxThreadNum = 4;
		int taskQueueSize = 100;

		consumer.init(activeMQURL, channelName);
		SimpleMessageHandler simpleMessageHandler = new SimpleMessageHandler();
		// MutiThreadMessageHandlerWarper mutiThreadMessageHandlerWarper=new
		// MutiThreadMessageHandlerWarper(minThreadNum,maxThreadNum,taskQueueSize,simpleMessageHandler);

		consumer.consumeLoop(simpleMessageHandler, 5000);

		consumer.close();

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
