package apache_collections;

import org.apache.commons.collections4.OrderedMap;
import org.apache.commons.collections4.map.LinkedMap;

public class TestLinkedMap {

	public static void main(String[] args){
	
			OrderedMap<String,String> map = new LinkedMap<String,String>();  
	        map.put("FIVE", "5");  
	        map.put("SIX", "6");  
	        map.put("SEVEN", "7");  
	        
	        map.firstKey(); // returns "FIVE"  
	        map.nextKey("FIVE"); // returns "SIX"  
	        map.nextKey("SIX"); // returns "SEVEN" 
	        
	        System.out.println(map.firstKey());
	        System.out.println(map.nextKey("FIVE"));
	        System.out.println(map.nextKey("SIX"));
	}
}
