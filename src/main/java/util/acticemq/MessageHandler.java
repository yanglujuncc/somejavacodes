package util.acticemq;

import javax.jms.Message;

public interface MessageHandler {

	public void handleMessage(Message msg);
	public void doStart();
	public void doFinish();

}
