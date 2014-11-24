package util;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class StringUtil {
	
	public static String[] split(String intput,String separator){
		
		List<String> termList=new LinkedList<String>();
		
		String remain=intput;
		int separatorSize=separator.length();
		int idx=0;
		while( (idx=remain.indexOf(separator))!=-1){
			
			String term=remain.substring(0, idx);
			//System.out.println("term: "+term);
			termList.add(term);
			remain=remain.substring(idx+separatorSize, remain.length());
		}
		termList.add(remain);
		
		return termList.toArray(new String[termList.size()]);
	}
	
	public static void main(String[] args){
		
		String a=":::::";
		System.out.println(Arrays.toString( split(a,":")));
	}
}
