package utils.mq.activemq;

import java.net.MalformedURLException;

import javax.management.MBeanServerConnection;
import javax.management.ObjectName;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;

import utils.vm.JmxUrl;

public class TestActiveMQUtil {
	
	
	public static void main(String[] args) throws Exception {
		
		String jmxUrl = JmxUrl.findJMXUrlByProcessId(756);

		System.out.println(jmxUrl);
	

		JMXServiceURL	url = new JMXServiceURL(jmxUrl);
		JMXConnector	jmxc = JMXConnectorFactory.connect(url, null);
		MBeanServerConnection	mbsc = jmxc.getMBeanServerConnection();
		

		ActiveMQUtil.rmNoComsumeQueue(mbsc);
		ActiveMQUtil.rmNoComsumeTopic(mbsc);
	//	System.out.println(objectName);
	}
}
