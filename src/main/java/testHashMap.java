import java.util.HashMap;
import java.util.Map;



public class testHashMap {
	public static void main(String[] argvs)
	{
		 HashMap<String,Long > l2s=new HashMap<String,Long>();  
		 
		 l2s.put( "hello",1L);
		 System.out.println(l2s.size());
		 l2s.put( "word",2L);
		 System.out.println(l2s.size());
		 l2s.put("hello2",3L);
		 System.out.println(l2s.size());
		 
		 l2s.put("hello",3L);
		 System.out.println(l2s.size());
		 
		 System.out.println(l2s.get("hello"));
		 
		
		 
	}
}
