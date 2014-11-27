package utils.mq.activemq;


import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import javax.management.MBeanAttributeInfo;
import javax.management.MBeanInfo;
import javax.management.MBeanOperationInfo;
import javax.management.MBeanServerConnection;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectInstance;
import javax.management.ObjectName;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;

import utils.vm.JmxUrl;



public class ActiveMQUtil {
	public static List<String> rmNoComsumeQueue(MBeanServerConnection mbsc) {
		
		List<String> queneNames=new LinkedList<String>();

		try {

			
		
			Set<ObjectInstance> set = mbsc.queryMBeans(null, null);

			ObjectName brokerBeanName = getBrokerName( mbsc);
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
			
					long queueSize = (Long) mbsc.getAttribute(mbeanName, "QueueSize");
					long ConsumerCount = (Long) mbsc.getAttribute(mbeanName, "ConsumerCount");
					long ProducerCount = (Long) mbsc.getAttribute(mbeanName, "ProducerCount");
				
					System.out.println( "\t" + "Queue:" + queueSize + " Consumer:" + ConsumerCount + " Producer:" + ProducerCount+"\t" + oi.getObjectName());
					
					if (ConsumerCount == 0) {
						
						String	operationName="removeQueue";
						Object[] params=new Object[1]; 
						params[0]=queueName;
						String[] signature=new String[1];
						signature[0]="java.lang.String";
											
						mbsc.invoke(brokerBeanName, operationName, params, signature);		
						System.out.println("remove queue:"+queueName);
						queneNames.add(queueName);
			
					}

				}

			}

		} catch (Exception e) {

			e.printStackTrace();	

		} 
		
		return queneNames;
	}
	
	public static List<String> rmNoComsumeTopic(MBeanServerConnection mbsc) {
		
		List<String> topicNames=new LinkedList<String>();

		try {

			
		
			Set<ObjectInstance> set = mbsc.queryMBeans(null, null);


			ObjectName brokerBeanName = getBrokerName( mbsc);
			// MBeanInfo info = mbsc.getMBeanInfo(brokerBeanName);
			// System.out.println("Class: " + info.getClassName());

			for (Iterator<ObjectInstance> it = set.iterator(); it.hasNext();) {
				
				ObjectInstance oi = (ObjectInstance) it.next();

				String name = oi.getObjectName().toString();
				String destName="destinationName=";
				int idxOf=name.indexOf(destName);
				
				String topicName=name.substring(idxOf+destName.length());
				
				String className = oi.getClassName();
				if (name.contains("destinationType=Topic") && className.equals("org.apache.activemq.broker.jmx.TopicView")) {
				
					ObjectName mbeanName = new ObjectName(name);
					MBeanInfo info = mbsc.getMBeanInfo(mbeanName);
					
					long queueSize = (Long) mbsc.getAttribute(mbeanName, "QueueSize");
					long ConsumerCount = (Long) mbsc.getAttribute(mbeanName, "ConsumerCount");
					long ProducerCount = (Long) mbsc.getAttribute(mbeanName, "ProducerCount");
				
					System.out.println("\t" +" QueueSize:" + queueSize + " ConsumerCount:" + ConsumerCount + " ProducerCount:" + ProducerCount+"\t" + oi.getObjectName()  );
				
					/*
					if (info.getAttributes().length > 0) {
						for (MBeanAttributeInfo m : info.getAttributes()) {
							 System.out.println("\t ==> Attriber：" +
							 m.getName());

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
					*/
					if (ConsumerCount == 0) {
						
						String	operationName="removeTopic";
						Object[] params=new Object[1]; 
						params[0]=topicName;
						String[] signature=new String[1];
						signature[0]="java.lang.String";
											
						mbsc.invoke(brokerBeanName, operationName, params, signature);			
						System.out.println("remove topic:"+topicName);
						topicNames.add(topicName);
			
					}
					

				}

			}

		} catch (Exception e) {

			e.printStackTrace();	

		} 
		
		return topicNames;
	}
	
	public static ObjectName getBrokerName(MBeanServerConnection mbsc){
		

		try {
			Set<ObjectInstance> set = mbsc.queryMBeans(null, null);
			for (Iterator<ObjectInstance> it = set.iterator(); it.hasNext();) {
				ObjectInstance oi = (ObjectInstance) it.next();
				
				String className = oi.getClassName().toString();
				String objectName = oi.getObjectName().toString();
				//System.out.println("class: " + className+"  object: " + objectName);
				
				if("org.apache.activemq.broker.jmx.BrokerView".equalsIgnoreCase(className)){
					ObjectName beanName = new ObjectName(objectName);
					return beanName;
				}
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MalformedObjectNameException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	// MBeanInfo info = mbsc.getMBeanInfo(brokerBeanName);
		// System.out.println("Class: " + info.getClassName());

	
	}
	public static long getQueueSize(MBeanServerConnection mbCon, String brokerName, String destinationType, String destinationName) throws Exception {

		String objectNameStr = "org.apache.activemq:type=Broker,brokerName=" + brokerName + ",destinationType=" + destinationType + ",destinationName=" + destinationName + "";
		ObjectName mbeanName = new ObjectName(objectNameStr);

		long queueSize = (Long) mbCon.getAttribute(mbeanName, "QueueSize");
		// System.out.println("queueSize:"+queueSize);

		return queueSize;
	}
	
	public static long getQueueConsumerCount(MBeanServerConnection mbCon, String brokerName, String destinationType, String destinationName) throws Exception {

		String objectNameStr = "org.apache.activemq:type=Broker,brokerName=" + brokerName + ",destinationType=" + destinationType + ",destinationName=" + destinationName + "";
		ObjectName mbeanName = new ObjectName(objectNameStr);
	
		long ConsumerCount = (Long) mbCon.getAttribute(mbeanName, "ConsumerCount");
		// System.out.println("queueSize:"+queueSize);

		return ConsumerCount;
	}
	
	public static void removeTopic(MBeanServerConnection mbCon, String brokerName, String topicName) throws Exception {

		ObjectName brokerBeanName = new ObjectName("org.apache.activemq:type=Broker,brokerName="+brokerName);
		
		
		String	operationName="removeTopic";
		Object[] params=new Object[1]; 
		params[0]=topicName;
		String[] signature=new String[1];
		signature[0]="java.lang.String";
		
		mbCon.invoke(brokerBeanName, operationName, params, signature);

		return ;
	}
	
	public static void removeQueue(MBeanServerConnection mbCon, String brokerName, String queueName) throws Exception {

		ObjectName brokerBeanName = new ObjectName("org.apache.activemq:type=Broker,brokerName="+brokerName);
		
		
		String	operationName="removeQueue";
		Object[] params=new Object[1]; 
		params[0]=queueName;
		String[] signature=new String[1];
		signature[0]="java.lang.String";
		
		mbCon.invoke(brokerBeanName, operationName, params, signature);

		return ;
	}
	
	public static List<String> getDestNames(MBeanServerConnection mbCon, String brokerName, String destinationType) throws Exception {

		String objectNamePrefix = "org.apache.activemq:type=Broker,brokerName=" + brokerName + ",destinationType=" + destinationType  + "";
		String flag="destinationName=";
		
		List<String> destinationNames=new LinkedList<String>();
		Set<ObjectInstance> set = mbCon.queryMBeans(null, null);
		for (Iterator<ObjectInstance> it = set.iterator(); it.hasNext();) {
			ObjectInstance oi = (ObjectInstance) it.next();
			
			if(oi.getObjectName().toString().startsWith(objectNamePrefix))
			{
				String objectNameStr=oi.getObjectName().toString();
				
				String destinationName=objectNameStr.substring(objectNameStr.indexOf(flag)+flag.length());
				
				if(destinationName.contains(","))
					continue;
				destinationNames.add(destinationName);
			}
			
		}
		// System.out.println("queueSize:"+queueSize);

		return destinationNames;
	}
	public static void usage() {
		System.out.println("input:  type<QueueSize|*> v1 v2 v3 ...");
		System.out.println("        <QueueSize>  surl/pid  brokerName  destinationType destinationName");
		System.out.println("        <QueuesName> surl/pid  brokerName  destinationType ");
	}
	

	public static void main(String[] args) {

		// String surl = "service:jmx:rmi:///jndi/rmi://localhost:1099/mymq";
		// String surl = "service:jmx:rmi:///jndi/rmi://localhost:1099/jmxrmi";
		// String surl = "service:jmx:rmi:///jndi/rmi://localhost:2099/mymq";

		// String surl =
		// "service:jmx:rmi:///jndi/rmi://newsrec1.photo.163.org:10038/mq_04";

		if(args.length<=2){
			usage() ;
			System.exit(1);
		}
		
		try {
		//	test();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		String type =args[0];
		String surl =args[1];
		
		if(!surl.startsWith("service:jmx:rmi:")){
			String pidStr=surl;
			int pid=Integer.parseInt(pidStr);
			String jmxUrl=JmxUrl.findJMXUrlByProcessId(pid);
			surl=jmxUrl;
		}
		
		/*
		String surl = "service:jmx:rmi://127.0.0.1/stub/rO0ABXNyAC5qYXZheC5tYW5hZ2VtZW50LnJlbW90ZS5ybWkuUk1JU2VydmVySW1wbF9TdHViAAAAAAAAAAICAAB4cgAaamF2YS5ybWkuc2VydmVyLlJlbW90ZVN0dWLp/tzJi+FlGgIAAHhyABxqYXZhLnJtaS5zZXJ2ZXIuUmVtb3RlT2JqZWN002G0kQxhMx4DAAB4cHc4AAtVbmljYXN0UmVmMgAADTEwLjI0MC4xMzcuMzAAAKk9OUoeEu6x7mo/zIt7AAABR2dU9MiAAQB4";
		String brokerName = "broker_001";
		String destinationType = "Queue";
		String destinationName = "my-queue";
	*/
		
		JMXServiceURL url = null;
		JMXConnector jmxc = null;
		MBeanServerConnection mbsc = null;
		try {
			
			url = new JMXServiceURL(surl);
			jmxc = JMXConnectorFactory.connect(url, null);
			mbsc = jmxc.getMBeanServerConnection();

			
			if("QueueSize".equalsIgnoreCase(type)){
				
			
				if(args.length<5){
					System.out.println("        <QueueSize>  surl/pid  brokerName  destinationType destinationName");
					System.exit(1);
				}
				String brokerName = args[2];
				String destinationType = args[3];
				String destinationName = args[4];
				
				
				long queueSize = getQueueSize(mbsc, brokerName, destinationType, destinationName);
				System.out.println( queueSize);
				
			}else if("QueuesName".equalsIgnoreCase(type)){
				if(args.length<4){
					System.out.println("        <QueuesName> surl/pid  brokerName  destinationType ");
					System.exit(1);
				}

				String brokerName = args[2];
				String destinationType = args[3];
			
				List<String>destNames=getDestNames(mbsc, brokerName, destinationType);
				for(String  destName:destNames){
					System.out.println( destName);
				}
			//	System.out.print( queueSize);
				
			}else{
				
				System.out.print( "unsupport type:"+type);
				System.exit(1);
			}
			
				
			System.exit(0);
			
		}catch (Exception e) {
			
			e.printStackTrace();
			System.exit(1);

		}finally{
			try {
				jmxc.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	public static void test() throws Exception {
		String surl = "service:jmx:rmi://127.0.0.1/stub/rO0ABXNyAC5qYXZheC5tYW5hZ2VtZW50LnJlbW90ZS5ybWkuUk1JU2VydmVySW1wbF9TdHViAAAAAAAAAAICAAB4cgAaamF2YS5ybWkuc2VydmVyLlJlbW90ZVN0dWLp/tzJi+FlGgIAAHhyABxqYXZhLnJtaS5zZXJ2ZXIuUmVtb3RlT2JqZWN002G0kQxhMx4DAAB4cHc4AAtVbmljYXN0UmVmMgAADTEwLjI0MC4xMzcuMzAAAKk9OUoeEu6x7mo/zIt7AAABR2dU9MiAAQB4";

		JMXServiceURL url = new JMXServiceURL(surl);
		JMXConnector jmxc = JMXConnectorFactory.connect(url, null);
		MBeanServerConnection mbsc = jmxc.getMBeanServerConnection();

		System.out.println("Domains:---------------");
		String domains[] = mbsc.getDomains();
		for (int i = 0; i < domains.length; i++) {
			System.out.println("\tDomain[" + i + "] = " + domains[i]);
		}

		System.out.println("all ObjectName：---------------");
		Set<ObjectInstance> set = mbsc.queryMBeans(null, null);
		for (Iterator<ObjectInstance> it = set.iterator(); it.hasNext();) {
			ObjectInstance oi = (ObjectInstance) it.next();
			System.out.println("\t" + oi.getObjectName()+"\t"+oi.getClassName());
			
		}

		System.out.println("org.apache.activemq:BrokerName=localhost,Type=Broker：---------------");
		// ObjectName mbeanName = new
		// ObjectName("org.apache.activemq:type=Broker,brokerName=localhost,destinationType=Queue,destinationName=my-queue");
		ObjectName mbeanName = new ObjectName("org.apache.activemq:type=Broker,brokerName=broker_001,destinationType=Queue,destinationName=my-queue");

		MBeanInfo info = mbsc.getMBeanInfo(mbeanName);
		System.out.println("Class: " + info.getClassName());
		if (info.getAttributes().length > 0) {
			for (MBeanAttributeInfo m : info.getAttributes()) {
				System.out.println("\t ==> Attriber：" + m.getName());
			}
		}
		if (info.getOperations().length > 0) {
			for (MBeanOperationInfo m : info.getOperations())
				System.out.println("\t ==> Operation：" + m.getName());
		}

		long queueSize = (Long) mbsc.getAttribute(mbeanName, "QueueSize");
		System.out.println("queueSize:" + queueSize);
		// queueSize = (Long)
		// mBeanServerConnection.getAttribute(objectNameRequest, "QueueSize");

		jmxc.close();
	}

}