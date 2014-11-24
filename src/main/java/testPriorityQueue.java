
import java.util.Comparator;
import java.util.PriorityQueue;


public class testPriorityQueue {
	
	class longReverComparator<Long> implements Comparator<Long>{

		@Override
		public int compare(Object o1, Object o2) {
			// TODO Auto-generated method stub
			return 0;
		}
		
	}
	
	public static void main(String[] args)
	{
		Comparator<Long> reverseComparator = new Comparator<Long>(){
		    public int compare(Long o1, Long o2){
		  
		     return o1.compareTo(o2);
		    }
		};
		PriorityQueue<Long> pq=new PriorityQueue<Long>(5,reverseComparator);
		
		for(int i=10;i>0;i--)
		{
			pq.add(new Long(i));
		}
		
		Long long_1=new Long(1);

		System.out.println(pq.contains(long_1));
		
		pq.remove(new Long(3));
		
		while(pq.size()>0)
		{
			System.out.println(pq.poll());
		}
		System.out.println("--------------------------");
	}

}

