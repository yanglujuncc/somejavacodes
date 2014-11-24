package RMI;
import java.rmi.Naming;
import java.rmi.RMISecurityManager;
import java.rmi.registry.LocateRegistry;


public class RMI_Server {
	 /**  
	    * ���� RMI ע����񲢽��ж���ע��  
	    */  
	   public static void main(String[] argv)   
	   {   
	      try  
	      {   
	         //����RMIע�����ָ���˿�Ϊ1099����1099ΪĬ�϶˿ڣ�   
	         //Ҳ����ͨ������ ��java_home/bin/rmiregistry 1099����   
	         //���������ַ�ʽ�������ٴ�һ��DOS����   
	         //����������rmiregistry����ע����񻹱���������RMIC���һ��stub��Ϊ������   
	          System.out.println("here");   
	          System.out.flush();
	         LocateRegistry.createRegistry(1099);   
	           
	         //����Զ�̶����һ������ʵ��������hello����   
	         //�����ò�ͬ����ע�᲻ͬ��ʵ��   
	         System.out.println("here");   
	          System.out.flush();
	      
	         rmi_interface hello = new rmi_interface_implements("Hello, world!");   
	           
	         //��helloע�ᵽRMIע��������ϣ�����ΪHello   
	         Naming.rebind("Hello", hello);   
	            
	         //���Ҫ��helloʵ��ע�ᵽ��һ̨������RMIע�����Ļ�����   
	         //Naming.rebind("//192.168.1.105:1099/Hello",hello);   
	           
	         System.out.println("Hello Server is ready.");   
	         
	       
	      }   
	      catch (Exception e)   
	      {   
	         System.out.println("Hello Server failed: " + e);   
	      }   
	      
	   }   
}
