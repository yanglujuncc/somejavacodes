package Reflection;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;


public class printInvocationHandler implements InvocationHandler{

	@Override
	public Object invoke(Object proxy, Method method, Object[] args)
			throws Throwable {
	

		 if(method.getName().equals("print"))
			 System.out.println("class v2");
		 
		 
		return null;
	}

}
