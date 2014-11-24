package util;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.Map.Entry;

public class MapUtil {

	public static<T> void addIntCountMap(Map<T, Integer> strCountMap, T key, int add) {

		Integer keyCounter = strCountMap.get(key);
		if (keyCounter == null) {
			keyCounter = new Integer(1);
			strCountMap.put(key, keyCounter);
		} else {
			strCountMap.put(key, keyCounter + add);
		}
	}
	public static<T> void addIntCountMapMap(Map<T, Map<T, Integer>> strCountMapMap, T key1 , T key2,int add) {

		Map<T, Integer> strCountMap = strCountMapMap.get(key1);
		if (strCountMap == null) {
			strCountMap = new HashMap<T, Integer>();
			strCountMapMap.put(key1, strCountMap);
		} 	
		
		strCountMap.put(key2, add);
	
	}

	public static<T> int mapMapElementSize( Map<T, Map<T, Integer>> mapMap){
	
		int count=0;
		for(Entry<T, Map<T, Integer>> entry:mapMap.entrySet()){			
			Map<T, Integer> map=entry.getValue();
			count+=map.size();					
		}
		
		return count;
	}
	public static void addLongCountMap(Map<String, Long> strCountMap, String key, long add) {

		Long keyCounter = strCountMap.get(key);
		if (keyCounter == null) {
			keyCounter = new Long(1);
			strCountMap.put(key, keyCounter);
		} else {
			strCountMap.put(key, keyCounter + add);
		}
	}

	public static List<Entry<String, Integer>> sortStrCountMap(Map<String, Integer> strCountMap, boolean ASC) {

		return sortStrCountMap(strCountMap, ASC, Integer.MAX_VALUE);

	}

	public static List<Entry<String, Integer>> sortStrCountMap(Map<String, Integer> strCountMap, boolean ASC, int limit) {

		LinkedList<Entry<String, Integer>> resultList = new LinkedList<Entry<String, Integer>>();
		TreeMap<Integer, List<Entry<String, Integer>>> sortMap = new TreeMap<Integer, List<Entry<String, Integer>>>();
		Entry<String, Integer> totalEntry = null;

		for (Entry<String, Integer> entry : strCountMap.entrySet()) {

			// spaciel entry
			if (entry.getKey().equalsIgnoreCase("total")) {
				totalEntry = entry;
				continue;
			}

			List<Entry<String, Integer>> entryList = sortMap.get(entry.getValue());
			if (entryList == null) {
				entryList = new LinkedList<Entry<String, Integer>>();
				sortMap.put(entry.getValue(), entryList);
			}
			entryList.add(entry);
		}

		while (!sortMap.isEmpty()) {

			Entry<Integer, List<Entry<String, Integer>>> firstEntry = sortMap.pollFirstEntry();
			for (Entry<String, Integer> entry : firstEntry.getValue()) {
				if (ASC)
					resultList.addLast(entry);
				else
					resultList.addFirst(entry);
			}

		}

		if (limit < Integer.MAX_VALUE) {
			LinkedList<Entry<String, Integer>> resultListLimited = new LinkedList<Entry<String, Integer>>();
			while (resultList.size() > 0) {

				if (resultListLimited.size() == limit) {
					break;
				}

				resultListLimited.add(resultList.pollFirst());

			}

			resultList = resultListLimited;
		}

		if (totalEntry != null)
			resultList.addFirst(totalEntry);

		return resultList;
	}
	public static List<Entry<String, Long>> sortStrCountMapLong(Map<String, Long> strCountMap, boolean ASC, int limit) {

		LinkedList<Entry<String, Long>> resultList = new LinkedList<Entry<String, Long>>();
		TreeMap<Long, List<Entry<String, Long>>> sortMap = new TreeMap<Long, List<Entry<String, Long>>>();
		Entry<String, Long> totalEntry = null;

		for (Entry<String, Long> entry : strCountMap.entrySet()) {

			// spaciel entry
			if (entry.getKey().equalsIgnoreCase("total")) {
				totalEntry = entry;
				continue;
			}

			List<Entry<String, Long>> entryList = sortMap.get(entry.getValue());
			if (entryList == null) {
				entryList = new LinkedList<Entry<String, Long>>();
				sortMap.put(entry.getValue(), entryList);
			}
			entryList.add(entry);
		}

		while (!sortMap.isEmpty()) {

			Entry<Long, List<Entry<String, Long>>> firstEntry = sortMap.pollFirstEntry();
			for (Entry<String, Long> entry : firstEntry.getValue()) {
				if (ASC)
					resultList.addLast(entry);
				else
					resultList.addFirst(entry);
			}

		}

		if (limit < Integer.MAX_VALUE) {
			LinkedList<Entry<String, Long>> resultListLimited = new LinkedList<Entry<String, Long>>();
			while (resultList.size() > 0) {

				if (resultListLimited.size() == limit) {
					break;
				}

				resultListLimited.add(resultList.pollFirst());

			}

			resultList = resultListLimited;
		}

		if (totalEntry != null)
			resultList.addFirst(totalEntry);

		return resultList;
	}
	
	public static Map<String, Long> toLongMap(Map<String, String> stringValueMap) {

		if (stringValueMap == null)
			return null;

		Map<String, Long> longValueMap = new HashMap<String, Long>();

		for (Entry<String, String> entry : stringValueMap.entrySet()) {

			long value = Long.parseLong(entry.getValue());
			longValueMap.put(entry.getKey(), value);
		}

		return longValueMap;
	}
	
	
	public static<T>  long totalCount(Map<T, Long> countMap ){
		long total=0;
		
		for(Entry<T, Long> entry:countMap.entrySet()){
			total+=entry.getValue();
		}
		
		return total;
	}
	public static<T>  long totalCountInt(Map<T, Integer> countMap ){
		long total=0;
		
		for(Entry<T, Integer> entry:countMap.entrySet()){
			total+=entry.getValue();
		}
		
		return total;
	}
	
	public static<T>  long totalCountOfMapMap(Map<T, Map<T, Integer>> countMapMap ){
		long total=0;
		
		for(Entry<T, Map<T, Integer>> entry:countMapMap.entrySet()){
			total+=totalCountInt(entry.getValue());
		}
		
		return total;
	}
	

	
	public static  Map<String, Double> toPercentMap(Map<String, Long> countMap,long total ){
		
		 Map<String, Double>percentMap=new  HashMap<String, Double>();
		for(Entry<String, Long> entry:countMap.entrySet()){
			String key=entry.getKey();
			double percent=entry.getValue()*1.0/total;
			percentMap.put(key, percent);
		}
		
		return percentMap;
	}
	public static void main(String[] args) {

		Map<String, Integer> strCountMap = new HashMap<String, Integer>();
		strCountMap.put("a", 1);
		strCountMap.put("b", 2);
		strCountMap.put("c", 3);
		System.out.println(sortStrCountMap(strCountMap, true));
		System.out.println(sortStrCountMap(strCountMap, true, 4));
		System.out.println(sortStrCountMap(strCountMap, false));
		System.out.println(sortStrCountMap(strCountMap, false, 0));
	}
}
