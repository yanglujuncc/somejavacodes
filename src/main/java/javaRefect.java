import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class javaRefect {
	public static void main(String[] argvs) {
		try {
			Class c = Class.forName("justAClass");
			Class[] ptype = new Class[] { String.class, long.class };

			Constructor ctor = c.getConstructor(ptype);
			Object[] obj = new Object[] { new String("hellos"), 123L };
			Object object;

			object = ctor.newInstance(obj);
			justAClass aobj=(justAClass)object;
			aobj.print();

		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
