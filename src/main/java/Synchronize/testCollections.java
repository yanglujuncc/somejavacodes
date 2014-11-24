package Synchronize;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;




public class testCollections {
	
	public static void main(String[] args){
		List<String> aList =  Collections.synchronizedList(new LinkedList<String>());
		System.out.println(aList.add("1"));
		System.out.println(aList.add("2"));
		System.out.println(aList.add("3"));
		System.out.println(aList.add("4"));
		
		System.out.println("size="+aList.size());	
		
		System.out.println("*********************************");
		System.out.println("size="+aList.size());		
		System.out.println(aList.remove(0));
		
		System.out.println("size="+aList.size());	
		System.out.println(aList.remove(0));
		
		System.out.println("size="+aList.size());	
		System.out.println(aList.remove(0));
		
		System.out.println("size="+aList.size());	
		System.out.println(aList.remove(0));
		

	}

}
