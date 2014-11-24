package util.rabbitmq;

public interface RabbitMQDataHandler {
	
	public void handleData(byte[] data);
	public void stopDo();
}
