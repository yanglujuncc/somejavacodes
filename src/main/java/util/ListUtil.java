package util;

import java.util.LinkedList;
import java.util.List;

public class ListUtil {

	public static String strListToString(List<String> rawStrList,String split){
		
		StringBuilder aStringBuilder = new StringBuilder();
		aStringBuilder.append("");
		
		if(rawStrList.size()==0)
			return aStringBuilder.toString();
		
		for(String element:rawStrList){
			aStringBuilder.append(element+ split);
		}
		aStringBuilder.deleteCharAt(aStringBuilder.length()-1);
		
		return aStringBuilder.toString();
		
	}
	
	public static void main(String[] args){
		
		List<String> rawStrList=new LinkedList<String>();
		rawStrList.add("a");
		rawStrList.add("b");
		System.out.println( strListToString(rawStrList,"|"));
	}
}
