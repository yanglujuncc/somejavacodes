import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;


public class testSort {

	public  static void main(String[] args){
		
		
		Map<Integer, Integer> counterMap=new HashMap<Integer, Integer>();
		
		counterMap.put(1, 1);
		
		counterMap.put(4, 4);
		counterMap.put(5, 5);
		counterMap.put(2, 2);
		counterMap.put(3, 3);
		
		ArrayList<Map.Entry<Integer, Integer>> entryList = new ArrayList<Map.Entry<Integer, Integer>>(
				counterMap.entrySet());

		System.out.println("before sort");
		for(Map.Entry<Integer, Integer> entry:entryList){
			
			System.out.println(entry.getKey()+":"+entry.getValue());
		}
		
		Collections.sort(entryList,
				new Comparator<Map.Entry<Integer, Integer>>() {
					public int compare(Map.Entry<Integer, Integer> o1,
							Map.Entry<Integer, Integer> o2) {
						return (o2.getKey() - o1.getKey());
					}
				});
		
		
		System.out.println("after sort");
		for(Map.Entry<Integer, Integer> entry:entryList){
			
			System.out.println(entry.getKey()+":"+entry.getValue());
		}
		
	}
}
