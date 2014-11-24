import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;


public class testHashSet {
	
	public static void main(String[] args)
	{
		
	
		HashSet<Long> longSet=new  HashSet<Long>();
		
		longSet.add((long) 1);
		longSet.add((long) 2);
		longSet.add((long) 3);
		
		System.out.println("longSet.contains(1)="+longSet.contains(1));
		System.out.println("longSet.contains(2)="+longSet.contains(2));
		System.out.println("longSet.contains(3)="+longSet.contains(3));
		
		System.out.println("longSet.remove(1)="+longSet.remove(1));
		System.out.println("longSet.remove(2)="+longSet.remove(2));
		System.out.println("longSet.remove(3)="+longSet.remove(3));
		
		System.out.println("longSet.remove(1L)="+longSet.remove(1L));
		System.out.println("longSet.remove(2L)="+longSet.remove(2L));
		System.out.println("longSet.remove(3L)="+longSet.remove(3L));
		
		System.out.println("longSet.contains(1L)="+longSet.contains(1L));
		System.out.println("longSet.contains(2L)="+longSet.contains(2L));
		System.out.println("longSet.contains(3L)="+longSet.contains(3L));
		
		longSet.add((long) 1);
		longSet.add((long) 2);
		longSet.add((long) 3);
		
		System.out.println("longSet.contains(1L)="+longSet.contains(1L));
		System.out.println("longSet.contains(2L)="+longSet.contains(2L));
		System.out.println("longSet.contains(3L)="+longSet.contains(3L));
		
		
		HashSet<Long> longSet2=new  HashSet<Long>(longSet);
		
		System.out.println("longSet2.contains(1L)="+longSet2.contains(1L));
		System.out.println("longSet2.contains(2L)="+longSet2.contains(2L));
		System.out.println("longSet2.contains(3L)="+longSet2.contains(3L));
		
		System.out.println("longSet2.remove(1L)="+longSet2.remove(1L));
		System.out.println("longSet2.remove(2L)="+longSet2.remove(2L));
		System.out.println("longSet2.remove(3L)="+longSet2.remove(3L));
		
		System.out.println("longSet2.contains(1L)="+longSet2.contains(1L));
		System.out.println("longSet2.contains(2L)="+longSet2.contains(2L));
		System.out.println("longSet2.contains(3L)="+longSet2.contains(3L));
		
		System.out.println("longSet.contains(1L)="+longSet.contains(1L));
		System.out.println("longSet.contains(2L)="+longSet.contains(2L));
		System.out.println("longSet.contains(3L)="+longSet.contains(3L));
		System.out.println("**********************************************************8");
		Map<Long ,String > maop=new HashMap<Long ,String>();
		maop.put(1L, "1");
		maop.put(2L, "2");
		maop.put(3L, "3");
		HashSet<Long> longSet3=new  HashSet<Long>(maop.keySet());
		System.out.println("longSet3.contains(1L)="+longSet3.contains(1L));
		System.out.println("longSet3.contains(2L)="+longSet3.contains(2L));
		System.out.println("longSet3.contains(3L)="+longSet3.contains(3L));
		
		
		longSet3.remove(2L);
		System.out.println("longSet3.contains(1L)="+longSet3.contains(1L));
		System.out.println("longSet3.contains(2L)="+longSet3.contains(2L));
		System.out.println("longSet3.contains(3L)="+longSet3.contains(3L));
		
		longSet3=new  HashSet<Long>(maop.keySet());
		System.out.println("longSet3.contains(1L)="+longSet3.contains(1L));
		System.out.println("longSet3.contains(2L)="+longSet3.contains(2L));
		System.out.println("longSet3.contains(3L)="+longSet3.contains(3L));
		
		
	}
}
