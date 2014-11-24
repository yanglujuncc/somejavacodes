package apache_collections;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.collections4.BidiMap;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.bidimap.TreeBidiMap;

public class TestCollectionUtils {
public static void main(String[] args){
		

        /** 
         * 得到两个集合中相同的元素 
         */  
        List<String> list1 = new ArrayList<String>();  
        list1.add("1");  
        list1.add("2");  
        list1.add("3");  
        List<String> list2 = new ArrayList<String>();  
        list2.add("2");  
        list2.add("3");  
        list2.add("5");  
        Collection c = CollectionUtils.retainAll(list1, list2);  
        System.out.println(c);  
	}
}
