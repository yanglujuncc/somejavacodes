package apache_collections;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.collections4.BidiMap;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.bidimap.TreeBidiMap;

public class TestTreeBidiMap {

	public static void main(String[] args){
		

        BidiMap<String,String> bidi = new TreeBidiMap<String,String> ();  
        bidi.put("SIX", "6");  
        bidi.get("SIX");  // returns "6"  
        bidi.getKey("6");  // returns "SIX"  
        //       bidi.removeValue("6");  // removes the mapping  
        System.out.println( bidi.get("SIX"));
        System.out.println( bidi.getKey("6"));
        
        BidiMap<String,String>  inverse = bidi.inverseBidiMap();  // returns a map with keys and values swapped  
        System.out.println(inverse);  
  
	}
}
