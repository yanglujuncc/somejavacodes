import java.util.LinkedList;
import java.util.List;
import java.util.Queue;


public class testLinkedList {
	
	public static void main(String[] args)
	{
		List<String> alist=new LinkedList<String>();
		
		System.out.println(alist.size());
		alist.add(null);
		System.out.println(alist.size());
		
		alist.add(null);
		System.out.println(alist.size());
		
		Queue<String> aQueue=(Queue<String>) alist;
		
		System.out.println("*********************************");
		System.out.println(aQueue.size());	
		System.out.println(aQueue.poll());
		System.out.println(aQueue.size());	
		System.out.println(aQueue.poll());
		System.out.println(aQueue.size());	
		System.out.println(aQueue.poll());
		System.out.println(aQueue.size());	
		System.out.println(aQueue.poll());
	}
}
