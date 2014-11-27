package utils.mq.activemq;

import javax.jms.Message;

public interface ActiveMQMsgHandler {

	public void handleMessage(Message msg)throws Exception;
	public void doStart()throws Exception;
	public void doFinish();

}
