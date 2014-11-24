import java.lang.reflect.Method;


public class MethInvok {
	
	 public static void main(String[] args) {
		    
		 MethInvok cls = new MethInvok();
	     Class c = cls.getClass();

	     try {                
	        // parameter type is null
	        Method m = c.getMethod("show", null);
	        System.out.println("method = " + m.toString());      
	        System.out.println();
	     }
	    
	     catch(NoSuchMethodException e) {
	        System.out.println(e.toString());
	     }
	        
	     try {
	        // method Long
	        Class[] cArg = new Class[1];
	        cArg[0] = Long.class;
	        Method lMethod = c.getMethod("showLong", cArg);
	        System.out.println("method = " + lMethod.toString());
	     }
	     catch(NoSuchMethodException e) {
	        System.out.println(e.toString());
	     }
	     try {
		        // method Long
		        Class[] cArg = new Class[2];
		        cArg[0]=MethInvok[].class;
		        cArg[1] = Long.TYPE;
		        Method lMethod = c.getMethod("showLong2", cArg);
		      
		        System.out.println("method = " + lMethod.toString());
		     }
		     catch(NoSuchMethodException e) {
		        System.out.println(e.toString());
		     }
	   }

	   public Integer show() {
	      return 1;
	   }
	   public void showLong2(MethInvok[] invocks,long l) {
		      this.l = l;
		  }
	   public void showLong(Long l) {
	      this.l = l;
	   }
	   long l = 78655;
}
