package util.acticemq;


import javax.jms.Message;

import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;

public class EmptyMQConsumer {
	private static Logger logger = Logger.getLogger(EmptyMQConsumer.class.getName());

	public static class EmptyMessageHandler implements MessageHandler {

		private static Logger logger = Logger.getLogger(EmptyMessageHandler.class.getName());

		long handleCounter = 0;
		long timeBegin = System.currentTimeMillis();

		
		public void handleMessage(Message msg) {

			handleCounter++;

			if (handleCounter % 10000 == 0) {

				long now = System.currentTimeMillis();
				long costTime = now - timeBegin;
				long speed = 10000 * 1000 / costTime;
				logger.info("handled:" + handleCounter + " speed:" + speed + "/s");
				timeBegin = now;

			}
			return;

		}


		public void doFinish() {
			// TODO Auto-generated method stub
			
		}


		public void doStart() {
			// TODO Auto-generated method stub
			
		}
	}

	public static void usage() {
		System.out.println("input: activeMQURL channelName");
	}

	public static void main(String[] args) throws Exception {

		DOMConfigurator.configureAndWatch("conf/log4j.xml");

		if (args.length < 2) {

			usage();
			System.exit(1);
			;
		}

		String activeMQURL = args[0];
		String channelName = args[1];

		// String activeMQURL = "tcp://bje2b11.space.163.org:61616";
		// String channelName = "MobileClickQueue";
		logger.info("  activeMQURL:" + activeMQURL);
		logger.info("  channelName:" + channelName);

		MQConsumer consumer = new MQConsumer();

		consumer.init(activeMQURL, channelName);
		EmptyMessageHandler emptyMessageHandler = new EmptyMessageHandler();
		consumer.consumeLoop(emptyMessageHandler, 5000);
		consumer.close();

	}

}
