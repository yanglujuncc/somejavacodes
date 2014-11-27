package utils.mq.activemq;


import javax.jms.Message;

import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;

import utils.PIDUtil;

public class EmptyActiveMQConsumer {
	private static Logger logger = Logger.getLogger(EmptyActiveMQConsumer.class.getName());

	public static class EmptyMessageHandler implements ActiveMQMsgHandler {

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

		// String activeMQURL = "tcp://newsrec1.photo.163.org:10021";
		// String channelName = "Test";
		logger.info("  activeMQURL:" + activeMQURL);
		logger.info("  channelName:" + channelName);

		ActiveMQConsumeRunner	mqConsumeRunner = new ActiveMQConsumeRunner(new EmptyMessageHandler(), 60000, activeMQURL, channelName);
		Thread	consumeThread = new Thread(mqConsumeRunner);
		consumeThread.start();
		
		PIDUtil.createPidFile("pid");

		
	}

}
