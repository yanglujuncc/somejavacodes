package utils.mq.activemq;

import java.io.IOException;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.management.MBeanAttributeInfo;
import javax.management.MBeanInfo;
import javax.management.MBeanOperationInfo;
import javax.management.MBeanParameterInfo;
import javax.management.MBeanServerConnection;
import javax.management.ObjectInstance;
import javax.management.ObjectName;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;

import utils.vm.JmxUrl;

public class TestActiveMQMB {

	public static void testQueue() {
		String jmxUrl = JmxUrl.findJMXUrlByProcessId(756);

		System.out.println(jmxUrl);

		/*
		 * String surl =
		 * "service:jmx:rmi://127.0.0.1/stub/rO0ABXNyAC5qYXZheC5tYW5hZ2VtZW50LnJlbW90ZS5ybWkuUk1JU2VydmVySW1wbF9TdHViAAAAAAAAAAICAAB4cgAaamF2YS5ybWkuc2VydmVyLlJlbW90ZVN0dWLp/tzJi+FlGgIAAHhyABxqYXZhLnJtaS5zZXJ2ZXIuUmVtb3RlT2JqZWN002G0kQxhMx4DAAB4cHc4AAtVbmljYXN0UmVmMgAADTEwLjI0MC4xMzcuMzAAAKk9OUoeEu6x7mo/zIt7AAABR2dU9MiAAQB4"
		 * ; String brokerName = "broker_001"; String destinationType = "Queue";
		 * String destinationName = "my-queue";
		 */

		JMXServiceURL url = null;
		JMXConnector jmxc = null;
		MBeanServerConnection mbsc = null;
		try {

			url = new JMXServiceURL(jmxUrl);
			jmxc = JMXConnectorFactory.connect(url, null);
			mbsc = jmxc.getMBeanServerConnection();

			Set<ObjectInstance> set = mbsc.queryMBeans(null, null);

			ObjectName brokerBeanName = new ObjectName("org.apache.activemq:type=Broker,brokerName=broker_001");
			// MBeanInfo info = mbsc.getMBeanInfo(brokerBeanName);
			// System.out.println("Class: " + info.getClassName());

			for (Iterator<ObjectInstance> it = set.iterator(); it.hasNext();) {
				ObjectInstance oi = (ObjectInstance) it.next();

				String queueNameContain = "destinationName=ActiveMQ.Advisory.Producer.Queue";
				String name = oi.getObjectName().toString();
				String destName="destinationName=";
				int idxOf=name.indexOf(destName);
				
				String queueName=name.substring(idxOf+destName.length());
				
				String className = oi.getClassName();
				if (name.contains("destinationType=Queue") && className.equals("org.apache.activemq.broker.jmx.QueueView")) {
				
					ObjectName mbeanName = new ObjectName(name);
					MBeanInfo info = mbsc.getMBeanInfo(mbeanName);
					System.out.println("Class:" + info.getClassName());
					System.out.println("queueName:" + queueName);

					if (info.getAttributes().length > 0) {
						for (MBeanAttributeInfo m : info.getAttributes()) {
							// System.out.println("\t ==> Attriber：" +
							// m.getName());

						}
					}
					if (info.getOperations().length > 0) {
						for (MBeanOperationInfo m : info.getOperations()) {
							if (m.getName().contains("removeQueue")) {
								System.out.println("\t ==> Operation：" + m.getName());
								System.out.println("\t              ：" + m.getDescription());
								System.out.println("\t              ：" + m.getSignature());
							}
						}

					}

					// String objectNameStr =
					// "org.apache.activemq:type=Broker,brokerName=" +
					// brokerName + ",destinationType=" + destinationType +
					// ",destinationName=" + destinationName + "";
					// ObjectName mbeanName = new ObjectName(objectNameStr);

					long queueSize = (Long) mbsc.getAttribute(mbeanName, "QueueSize");
					long ConsumerCount = (Long) mbsc.getAttribute(mbeanName, "ConsumerCount");
					long ProducerCount = (Long) mbsc.getAttribute(mbeanName, "ProducerCount");
					System.out.println("\t" + oi.getObjectName() + "\t" + oi.getClassName());
					System.out.println("QueueSize：" + queueSize + " ConsumerCount：" + ConsumerCount + " ProducerCount:" + ProducerCount);

					if (ConsumerCount == 0) {
						String	operationName="removeQueue";
						Object[] params=new Object[1]; 
						params[0]=queueName;
						String[] signature=new String[1];
						signature[0]="java.lang.String";
						
						mbsc.invoke(brokerBeanName, operationName, params, signature);
					//	 mbsc.invoke(brokerBeanName, "removeQueue", params,
						// signature)
					}

				}

			}

		} catch (Exception e) {

			e.printStackTrace();
			System.exit(1);

		} finally {
			try {
				jmxc.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public static void testMB() {
		String jmxUrl = JmxUrl.findJMXUrlByProcessId(756);

		System.out.println(jmxUrl);

		/*
		 * String surl =
		 * "service:jmx:rmi://127.0.0.1/stub/rO0ABXNyAC5qYXZheC5tYW5hZ2VtZW50LnJlbW90ZS5ybWkuUk1JU2VydmVySW1wbF9TdHViAAAAAAAAAAICAAB4cgAaamF2YS5ybWkuc2VydmVyLlJlbW90ZVN0dWLp/tzJi+FlGgIAAHhyABxqYXZhLnJtaS5zZXJ2ZXIuUmVtb3RlT2JqZWN002G0kQxhMx4DAAB4cHc4AAtVbmljYXN0UmVmMgAADTEwLjI0MC4xMzcuMzAAAKk9OUoeEu6x7mo/zIt7AAABR2dU9MiAAQB4"
		 * ; String brokerName = "broker_001"; String destinationType = "Queue";
		 * String destinationName = "my-queue";
		 */

		JMXServiceURL url = null;
		JMXConnector jmxc = null;
		MBeanServerConnection mbsc = null;
		try {

			url = new JMXServiceURL(jmxUrl);
			jmxc = JMXConnectorFactory.connect(url, null);
			mbsc = jmxc.getMBeanServerConnection();

			Set<ObjectInstance> set = mbsc.queryMBeans(null, null);
			for (Iterator<ObjectInstance> it = set.iterator(); it.hasNext();) {
				ObjectInstance oi = (ObjectInstance) it.next();
				
				if (oi.getClassName().toString().contains("org.apache.activemq.broker.jmx.QueueView")
						|| oi.getClassName().toString().contains("org.apache.activemq.broker.jmx.TopicView")
						|| oi.getClassName().toString().contains("org.apache.activemq.broker.jmx.TopicSubscriptionView")
						|| oi.getClassName().toString().contains("org.apache.activemq.broker.jmx.ConnectionView")
						|| oi.getClassName().toString().contains("org.apache.activemq.broker.jmx.SubscriptionView")
						|| oi.getClassName().toString().contains("org.apache.activemq.broker.jmx.ConnectorView")) {

					continue;
				}
				ObjectName brokerBeanName = new ObjectName("org.apache.activemq:type=Broker,brokerName=broker_001");
				
				if(!"org.apache.activemq:type=Broker,brokerName=broker_001".equalsIgnoreCase(oi.getObjectName().toString())){
					continue;
				}
				System.out.println(oi.getClassName() + " " + oi.getObjectName());

				// org.apache.activemq.broker.jmx.BrokerView
				// org.apache.activemq:type=Broker,brokerName=broker_001
				// removeQueue
				String name = oi.getObjectName().toString();

				ObjectName mbeanName = new ObjectName(name);
				MBeanInfo info = mbsc.getMBeanInfo(mbeanName);

				if (info.getAttributes().length > 0) {
					for (MBeanAttributeInfo m : info.getAttributes()) {
						System.out.println("\t ==> Attriber：" + m.getName());

					}
				}
				if (info.getOperations().length > 0) {
					for (MBeanOperationInfo m : info.getOperations()) {
						System.out.println("\t ==> Operation：" + m.getName());
						if (m.getName().contains("removeQueue")) {
							System.out.println("\t ==> Operation：" + m.getName());
							System.out.println("\t              ：" + m.getDescription());
							MBeanParameterInfo[]  parameters=m.getSignature();
							System.out.println("\t              ：" +parameters.length+ Arrays.toString(m.getSignature()));
							
							
						}
						if (m.getName().contains("removeTopic")) {
							System.out.println("\t ==> Operation：" + m.getName());
							System.out.println("\t              ：" + m.getDescription());
							MBeanParameterInfo[]  parameters=m.getSignature();
							System.out.println("\t              ：" +parameters.length+ Arrays.toString(m.getSignature()));
							
							
						}
					}

				}

				// String objectNameStr =
				// "org.apache.activemq:type=Broker,brokerName=" + brokerName +
				// ",destinationType=" + destinationType + ",destinationName=" +
				// destinationName + "";
				// ObjectName mbeanName = new ObjectName(objectNameStr);

			}

		} catch (Exception e) {

			e.printStackTrace();
			System.exit(1);

		} finally {
			try {
				jmxc.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public static void main(String[] args) {

		// String surl = "service:jmx:rmi:///jndi/rmi://localhost:1099/mymq";
		// String surl = "service:jmx:rmi:///jndi/rmi://localhost:1099/jmxrmi";
		// String surl = "service:jmx:rmi:///jndi/rmi://localhost:2099/mymq";

		// String surl =
		// "service:jmx:rmi:///jndi/rmi://newsrec1.photo.163.org:10038/mq_04";

		// String surl =args[1];

		testMB();
	//	 testQueue();
	}

}
