import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class readProperties {
	public static void main(String[] args) {
		try {
			Properties myProperties = new Properties();
			InputStream is = new FileInputStream(new File("log4j.properties"));

			myProperties.load(is);
			
			

			String value=myProperties.getProperty("log4j.appender.DRFA.layout.ConversionPattern");
			System.out.println(value);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
