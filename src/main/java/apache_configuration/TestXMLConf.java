package apache_configuration;


import org.apache.commons.configuration.XMLConfiguration;

public class TestXMLConf {

	public static void main(String[] args) throws Exception{
		
		XMLConfiguration config =new XMLConfiguration("conf/testConf.xml");
		// 127.0.0.1
		String url=config.getString("database.url"); 
		// 1521
		int port=config.getInt("database.port");
		
		System.out.println("url:"+url);
		System.out.println("port:"+port);
	}
}
