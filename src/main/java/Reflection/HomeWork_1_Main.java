package Reflection;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;


public class HomeWork_1_Main {

	public static void main(String[] argvs) throws Exception, NoSuchMethodException{
		
		 PrintHello aPrintHello=new PrintHello();
		
		 /*
		 aPrintHello.print();
		
		 Class ownerClass = aPrintHello.getClass(); 
	     Class[] argsClass =null;
	     Object[] args = null;
	     String methodName="print";
	     Method method = ownerClass.getMethod(methodName, argsClass);
	     method.setAccessible(true);  //bad smell
	     method.invoke(aPrintHello, args);
	     */
	     System.out.println("************proxy*******************");
	     
	     printer proxy = (printer) Proxy.newProxyInstance(  
	    		 aPrintHello.getClass().getClassLoader(),  
	    		 aPrintHello.getClass().getInterfaces(),  
	                new printInvocationHandler());  
	     
	     aPrintHello.print();
	     proxy.print(); 
	}
}
