import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;


public class testLog4J {
	private static Logger logger  = Logger.getLogger(testLog4J.class.getName());
	
	public static void  main(String[] args)
	{
		//BasicConfigurator.configure ();  自动快速地使用缺省Log4j环境。 
		//DOMConfigurator.configure ( String filename ) ：读取XML形式的配置文件。
		PropertyConfigurator.configure("conf/log4j.properties");
		logger.info("logger.info");
		logger.error("logger.error");
	}
	
}
