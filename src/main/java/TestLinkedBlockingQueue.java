import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;


public class TestLinkedBlockingQueue {
	
	public static void NoBlockingAPI_add(){
		LinkedBlockingQueue<String> alist=new LinkedBlockingQueue<String>(2);
		
		System.out.println(alist.size());
		alist.add("a");
		alist.add("b");
		alist.add("c");
		
		
	}
	public static void NoBlockingAPI_pool(){
		LinkedBlockingQueue<String> alist=new LinkedBlockingQueue<String>(2);
		
		System.out.println(alist.size());
		String element=alist.poll();
		System.out.println(alist.size());
	}
	
	public static void BlockingAPI_put(){
		LinkedBlockingQueue<String> alist=new LinkedBlockingQueue<String>(2);
		
		System.out.println(alist.size());
		try {
			alist.put("a");
			alist.put("b");
			alist.put("c");
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	public static void BlockingAPI_take(){
		LinkedBlockingQueue<String> alist=new LinkedBlockingQueue<String>(2);
		
		System.out.println(alist.size());
		try {
			String element=alist.take();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public static void main(String[] args)
	{
		BlockingAPI_put();
		
	}
}
