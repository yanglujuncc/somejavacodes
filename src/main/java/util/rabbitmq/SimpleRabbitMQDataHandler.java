package util.rabbitmq;

import java.io.UnsupportedEncodingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SimpleRabbitMQDataHandler implements RabbitMQDataHandler {
	protected static Logger logger = LoggerFactory.getLogger(RabbitMQDataConsumer.class.getName());

	public void handleData(byte[] data) {
	
		try {
			String rawLog=new String(data,"utf-8");
			logger.info(rawLog.replace("\\t", "\t"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void stopDo() {
		// TODO Auto-generated method stub
		
	}

}
