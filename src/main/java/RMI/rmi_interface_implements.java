package RMI;
import justAClass;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class rmi_interface_implements  extends UnicastRemoteObject implements rmi_interface {
	/**
	 * ���붨�幹�췽������ʹ��Ĭ�Ϲ��췽����Ҳ���������ȷ��д��������Ϊ������׳���RemoteException�쳣
	 */
	String message;

	public rmi_interface_implements(String msg) throws RemoteException {
		message = msg;
	}

	/**
	 * Զ�̽ӿڷ�����ʵ��
	 */
	
	public justAClass say() throws RemoteException {
		
		System.out.println("do say(): ");
		justAClass ac=new justAClass("hello",1,2);
		System.out.println("Called by HelloClient");
		
		return ac;
	}
	
	public String say2() throws RemoteException {
		System.out.println("do say2(): ");
		String str=new String("hello");
		System.out.println("Called by HelloClient");
		return str;
	}
	public int say3() throws RemoteException 
	{
		System.out.println("do say3(): ");
		return 1;
	}
}
