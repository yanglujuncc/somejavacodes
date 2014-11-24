import java.net.UnknownHostException;


public class testLocalAddress {
	
	public static void main(String[] args) throws UnknownHostException
	{
		java.net.InetAddress ad=java.net.InetAddress.getLocalHost();
		System.out.println(ad.getHostAddress());
	}

}
