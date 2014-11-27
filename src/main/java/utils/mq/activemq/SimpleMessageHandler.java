package utils.mq.activemq;



import javax.jms.BytesMessage;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;

public class SimpleMessageHandler implements ActiveMQMsgHandler{

	private static Logger logger = Logger.getLogger(SimpleMessageHandler.class.getName());


	

	public void handleMessage(Message msg) {
		
		
		
		if(msg instanceof TextMessage ){
			TextMessage textMessage = (TextMessage)  msg;
			try {
				System.out.println(textMessage.getText());
			} catch (JMSException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}else if(msg instanceof BytesMessage){
			BytesMessage bytesMessage = (BytesMessage)  msg;
			try {
				byte[] byteArr = new byte[(int)bytesMessage.getBodyLength()];
				bytesMessage.readBytes(byteArr);
				System.out.println(new String(byteArr,"utf-8"));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
		
	
		
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
		// String activeMQURL ="tcp://localhost:61616";
		String channelName = "MobileClickQueue";
		
		MQConsumer consumer = new MQConsumer();
	
		int minThreadNum=2;
		int maxThreadNum=4;
		int taskQueueSize=100;
		
		consumer.init(activeMQURL, channelName);
		SimpleMessageHandler simpleMessageHandler = new SimpleMessageHandler();
		//MutiThreadMessageHandlerWarper mutiThreadMessageHandlerWarper=new MutiThreadMessageHandlerWarper(minThreadNum,maxThreadNum,taskQueueSize,simpleMessageHandler);

		consumer.consumeLoop(simpleMessageHandler, 5000);
		
		
		
	
		consumer.close();
		
		
		// messageConsum();
		// String channelName="my-topick";
		// new Thread(new subRunable()).start();
		// messageSub( channelName);
		// messagePut(channelName);
		// 10   302/sec
		// 100  1689/sec
		// 500  1865/sec
		
	}

	public void doFinish() {
		// TODO Auto-generated method stub
		
	}

	public void doStart() {
		// TODO Auto-generated method stub
		
	}

}
