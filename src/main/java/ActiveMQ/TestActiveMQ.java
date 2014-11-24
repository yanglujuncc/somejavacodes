package ActiveMQ;

import java.util.Date;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.log4j.xml.DOMConfigurator;

public class TestActiveMQ {

	public static  String url ="tcp://bje2b9.space.163.org:61616";
	
	public static class MyMessageListener implements MessageListener{

		@Override
		public void onMessage(Message msg) {
			MapMessage message = (MapMessage)  msg;
			try {
				System.out.println(Thread.currentThread().getName()+" �յ���Ϣ��" + new Date(message.getLong("count")));
			} catch (JMSException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
	}
	public static void messagePub(String channelName) throws Exception {

		ConnectionFactory connectionFactory = new ActiveMQConnectionFactory();
	
		Connection connection = connectionFactory.createConnection();
		connection.start();

		Session session = connection.createSession(Boolean.TRUE, Session.AUTO_ACKNOWLEDGE);
		Destination destination = session.createTopic(channelName);
	

		MessageProducer producer = session.createProducer(destination);
		
		 /* 
         * DeliverMode��2�ַ�ʽ�� 
         *  
         * public interface DeliveryMode { static final int NON_PERSISTENT = 
         * 1;//���־û�������������֮����Ϣ��� 
         *  
         * static final int PERSISTENT = 2;//�־û�������������֮�󣬸���Ϣ�Դ��� } 
         */  
        producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);  
        
        
		for (int i = 0; i < 3; i++) {
		
			
			MapMessage message = session.createMapMessage();
			message.setLong("count", new Date().getTime());
		//	message.setB
			
			Thread.sleep(1000);
			// ͨ����Ϣ����߷�����Ϣ
			producer.send(message);
		}
		session.commit();
		session.close();
		connection.close();
	}
	public static void messageSub(String channelName) {  
        // ����ߵ���Ҫ����  
        Connection connection = null;  
  
        try {  
            // 1.��ʼ��connection����  
            ConnectionFactory connectionFactory = new ActiveMQConnectionFactory();  
  
            // 2.����Connection  
            connection = connectionFactory.createConnection();  
  
            // 3.������  
            connection.start();  
  
            // 4.����session  
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);  
  
            // 5.������ϢĿ��  
            Destination destination = session.createTopic(channelName);  
  
            // 6.���������  
            MessageConsumer consumer = session.createConsumer(destination);  
  
            // 7.���ü���  
            consumer.setMessageListener(new MyMessageListener());  
  
        } catch (JMSException e) {  
            e.printStackTrace();  
        }  
    }  
	public static void messageSub2(String channelName) {  
        // ����ߵ���Ҫ����  
        Connection connection = null;  
  
        try {  
            // 1.��ʼ��connection����  
            ConnectionFactory connectionFactory = new ActiveMQConnectionFactory();  
  
            // 2.����Connection  
            connection = connectionFactory.createConnection();  
  
            // 3.������  
            connection.start();  
  
            // 4.����session  
            Session session = connection.createSession(true, Session.AUTO_ACKNOWLEDGE);  
  
            // 5.������ϢĿ��  
            Destination destination = session.createTopic(channelName);  
  
            // 6.���������  
            MessageConsumer consumer = session.createConsumer(destination);  
  
            // 7.���ü���  
            int i = 0;
    		while (i < 3) {
    			i++;
    			
    		
    			MapMessage message = (MapMessage) consumer.receive();
    			session.commit();

    			// TODO something....
    			System.out.println(Thread.currentThread().getName()+" �յ���Ϣ��" + new Date(message.getLong("count")));
    		}

  
        } catch (JMSException e) {  
            e.printStackTrace();  
        }  
    }  
	
	public static void messageProduce() throws Exception {

		ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(url);
		Connection connection = connectionFactory.createConnection();
		connection.start();

		Session session = connection.createSession(Boolean.TRUE, Session.AUTO_ACKNOWLEDGE);
		Destination destination = session.createQueue("my-queue");
	

		MessageProducer producer = session.createProducer(destination);
		for (int i = 0; i < 100; i++) {
		
			
			MapMessage message = session.createMapMessage();
			message.setLong("count", new Date().getTime());
			
		//	Thread.sleep(1000);
			// ͨ����Ϣ����߷�����Ϣ
			producer.send(message);
		}
		
		session.commit();
		session.close();
		connection.close();
		
	}

	public static void messageConsum() throws Exception {

		ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(url);
		Connection connection = connectionFactory.createConnection();
		connection.start();

		 Session session = connection.createSession(true, Session.SESSION_TRANSACTED);
	
		Destination destination = session.createQueue("my-queue");

		MessageConsumer consumer = session.createConsumer(destination);
		/*
		 * //listener ��ʽ consumer.setMessageListener(new MessageListener() {
		 * 
		 * public void onMessage(Message msg) { MapMessage message =
		 * (MapMessage) msg; //TODO something.... System.out.println("�յ���Ϣ��" +
		 * new Date(message.getLong("count"))); session.commit(); }
		 * 
		 * }); Thread.sleep(30000);
		 */
		int i = 10000;
		while (i >0) {
			
			
		
			MapMessage message = (MapMessage) consumer.receive();
			// TODO something....
			System.out.println("�յ���Ϣ��" + new Date(message.getLong("count")));
			i--;
		}

		//session.commit();
		session.close();
		connection.close();
	}
	public static void textMessageConsum(String channel) throws Exception {

		ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(url);
		Connection connection = connectionFactory.createConnection();
		connection.start();

		 Session session = connection.createSession(true, Session.SESSION_TRANSACTED);
	
		Destination destination = session.createQueue(channel);

		MessageConsumer consumer = session.createConsumer(destination);
		/*
		 * //listener ��ʽ consumer.setMessageListener(new MessageListener() {
		 * 
		 * public void onMessage(Message msg) { MapMessage message =
		 * (MapMessage) msg; //TODO something.... System.out.println("�յ���Ϣ��" +
		 * new Date(message.getLong("count"))); session.commit(); }
		 * 
		 * }); Thread.sleep(30000);
		 */
		int i = 10000;
		while (i >0) {
			
			
		
			TextMessage message = (TextMessage) consumer.receive();
			// TODO something....
			System.out.println(message.getText());
			i--;
		}

		//session.commit();
		session.close();
		connection.close();
	}
	
	public static void consumAll(String url,String channel) throws Exception {

		ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(url);
		Connection connection = connectionFactory.createConnection();
		connection.start();

		 Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
	
		Destination destination = session.createQueue(channel);

		MessageConsumer consumer = session.createConsumer(destination);
		/*
		 * //listener ��ʽ consumer.setMessageListener(new MessageListener() {
		 * 
		 * public void onMessage(Message msg) { MapMessage message =
		 * (MapMessage) msg; //TODO something.... System.out.println("�յ���Ϣ��" +
		 * new Date(message.getLong("count"))); session.commit(); }
		 * 
		 * }); Thread.sleep(30000);
		 */
		int count=0;
		long timeStart=System.currentTimeMillis();
		long timeBegin=System.currentTimeMillis();
		long lastCount=0;
		while (true) {
			
			
		
			TextMessage message = (TextMessage) consumer.receive();
			System.out.println(message.getText());
			count++;
			long timeNow=System.currentTimeMillis();
			if(timeNow-timeBegin>=1000){
				long timeCost=timeNow-timeBegin;
				timeBegin=System.currentTimeMillis();
				if(timeCost==0)
					timeCost=1;
				int add=(int) (count-lastCount);
				int speed=(int) (add*1000/timeCost);
				
				long timeCostGlobal=timeNow-timeStart;
				if(timeCostGlobal==0)
					timeCostGlobal=1;
				int globalSpeed=(int) (count*1000/timeCostGlobal);
				System.out.println(count+" Add:"+add+" speed:"+speed+"/s ,speedG="+globalSpeed+"/s");
				lastCount=count;
			}
		}

		
		
	}
	public static class subRunable implements Runnable{

		@Override
		public void run() {
			
			messageSub2("my-topick");
		}
		
	}
	public static void main(String[] args) throws Exception {
		
		//http://localhost:8161/admin/queues.jsp  admin admin 
		//http://localhost:8161/admin/topics.jsp  admin admin 
		//guest
	//	
		DOMConfigurator.configureAndWatch("conf/log4j.xml");
		String	url ="tcp://bje2b9.space.163.org:61616";
		//String	url ="tcp://bje2b9.space.163.org:61616?jms.prefetchPolicy.queuePrefetch=10000";
		
		//messageProduce();
		consumAll(url,"QNLogQueue");
		//String channelName="my-topick";
		//new Thread(new subRunable()).start();
		// messageSub( channelName);
		 //messagePut(channelName);
	}

}
