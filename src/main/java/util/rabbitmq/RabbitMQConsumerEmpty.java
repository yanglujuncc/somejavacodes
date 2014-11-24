package util.rabbitmq;

import org.apache.log4j.xml.DOMConfigurator;

public class RabbitMQConsumerEmpty {

	public static void  main(String[] args) throws Exception{
		//amqp://app-68.photo.163.org:5672
		//String url="123.58.181.213:5672";

		//String url=args[0];
	//	String queueName=args[1];
		DOMConfigurator.configureAndWatch("conf/log4j.xml");
		
	
		
		String url="amqp://guest:guest@db-48.photo.163.org:5672";
		String prisQueueName="pris.app11";
	//	String mobileQueueName="mobile.app11";
		
		
		RabbitMQDataConsumer aRabbitMQLogConsumer_pris=new RabbitMQDataConsumer(url,prisQueueName,"consumer_pris",10000,null);
	//	RabbitMQConsumer aRabbitMQLogConsumer_mobile=new RabbitMQConsumer(url,mobileQueueName,"consumer_mobile");
		
		aRabbitMQLogConsumer_pris.init();
	//	aRabbitMQLogConsumer_mobile.init();
		
		aRabbitMQLogConsumer_pris.start();
		//aRabbitMQLogConsumer_mobile.start();
		
		System.out.println("Consumer running ... ");
	}
}
