package util;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.Map.Entry;

public class MapSort {

	public static List<Entry<String,Integer>> sortStrCountMap(Map<String,Integer> strCountMap,boolean ASC){
		
		LinkedList<Entry<String,Integer>> resultList=new LinkedList<Entry<String,Integer>>();
		TreeMap<Integer,List<Entry<String, Integer>>> sortMap=new TreeMap<Integer,List<Entry<String, Integer>>> ();
		Entry<String, Integer> totalEntry=null;
		for(Entry<String, Integer> entry:strCountMap.entrySet()){	
			
			//spaciel entry
			if(entry.getKey().equalsIgnoreCase("total")){
				totalEntry=entry;
				continue;
			}
			
			List<Entry<String, Integer>> entryList=sortMap.get(entry.getValue());
			if(entryList==null)
			{
				entryList=new LinkedList<Entry<String, Integer>>();
				sortMap.put(entry.getValue(), entryList);
			}
			entryList.add(entry);
		}
		
		while(!sortMap.isEmpty()){
			Entry<Integer,List<Entry<String, Integer>>> firstEntry=sortMap.pollFirstEntry();
			for(Entry<String, Integer> entry:firstEntry.getValue()){
				if(ASC)
					resultList.addLast(entry);
				else
					resultList.addFirst(entry);
			}
			
		//	System.out.println(firstEntry);
		}
				
		if(totalEntry!=null)
			resultList.addFirst(totalEntry);
		
		
		return resultList;
	}
	
	public static void main(String[] args){
		Map<String,Integer> strCountMap=new HashMap<String,Integer>();
		strCountMap.put("a", 1);
		strCountMap.put("b", 2);
		System.out.println( sortStrCountMap(strCountMap,true));
		System.out.println( sortStrCountMap(strCountMap,false));
	}
}
