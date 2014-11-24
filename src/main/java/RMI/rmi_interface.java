package RMI;
import justAClass;

import java.rmi.Remote;
import java.rmi.RemoteException;


public interface rmi_interface   extends Remote   {
	
	 public justAClass say() throws RemoteException;   
	 public String say2() throws RemoteException ;
	 public int say3() throws RemoteException ;
	 
}
