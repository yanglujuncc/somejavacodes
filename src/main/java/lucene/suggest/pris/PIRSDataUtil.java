package lucene.suggest.pris;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.lucene.search.spell.Dictionary;
import org.apache.lucene.search.suggest.FileDictionary;

import util.ConnectionUtil;

public class PIRSDataUtil {


	public static List<String> loadTerms(int type){
		
		List<String> resultList=new LinkedList<String>();
		Connection conn = null;
		try {
			conn = ConnectionUtil.getConnection();
			String queryDeviceInfoSql = "select sourceID,title from SourceMap where type="+type+";";

			Statement queryIndexStatement = conn.createStatement();

			ResultSet resultSet = queryIndexStatement.executeQuery(queryDeviceInfoSql);
			while (resultSet.next()) {

				
				String  sourceID= resultSet.getString(1);
				String  title= resultSet.getString(2);									
				resultList.add(title);

			}
			return resultList;
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
		
	}
	public static void  loadSourceTitleMap(Map<String,List<String>>titleSoureIDMap ,Map<String,Long>soureUpdateMap,int type){
		
		
		Connection conn = null;
		try {
			conn = ConnectionUtil.getConnection();
			String queryDeviceInfoSql = "select sourceID,title,updateTime from SourceMap where type="+type+";";

			Statement queryIndexStatement = conn.createStatement();

			ResultSet resultSet = queryIndexStatement.executeQuery(queryDeviceInfoSql);
			while (resultSet.next()) {

				
				String  sourceID= resultSet.getString(1).toLowerCase();
				String  title= resultSet.getString(2);		
				long updateTime=resultSet.getLong(3);
				List<String> sourceIDList=titleSoureIDMap.get(title);
				if(sourceIDList==null)
				{
					sourceIDList=new LinkedList<String>();
					titleSoureIDMap.put(title, sourceIDList);
				}
				sourceIDList.add(sourceID);
				
				soureUpdateMap.put(sourceID, updateTime);
				
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return ;
		
	}
	
	public static void writeToFile(List<String> terms,String filePath,String encoding) throws Exception{
		
		FileOutputStream fileOutputStream=new FileOutputStream(filePath);
		OutputStreamWriter outputStreamWriter=new OutputStreamWriter(fileOutputStream,encoding);
		BufferedWriter bufferedWriter=new BufferedWriter(outputStreamWriter);
		
		int i=1;
		for(String term: terms){
			
			bufferedWriter.append(term+"\t"+i+"\n");
			i++;
		}
		bufferedWriter.close();
			
		
	}
	public static void writeToFile(Map<String,Integer> map ,String filePath,String encoding) throws Exception{
		
		FileOutputStream fileOutputStream=new FileOutputStream(filePath);
		OutputStreamWriter outputStreamWriter=new OutputStreamWriter(fileOutputStream,encoding);
		BufferedWriter bufferedWriter=new BufferedWriter(outputStreamWriter);
		
		for(Entry<String,Integer> entry: map.entrySet()){
			
			String key=entry.getKey();
			Integer value=entry.getValue();
			bufferedWriter.append(key+"\t"+value+"\n");
		}
		bufferedWriter.close();
			
		
	}
	
	public static Map<String,String> choseMaxSourceID( Map<String,Integer> clickCountMap,Map<String,List<String>> titleFeedidMap,Map<String,Long>soureUpdateMap){
		
		
		Map<String,String> titleFeedID=new HashMap<String,String>();
		
		for(Entry<String,List<String>> entry: titleFeedidMap.entrySet()){
			
			String title=entry.getKey();
			List<String> sourceIDList=entry.getValue();	
			String sourceIDChosen=sourceIDList.get(0);
			int sourceWeightChosen=0;
			Integer weightObj=clickCountMap.get(sourceIDChosen);
			if(weightObj!=null)
				sourceWeightChosen=weightObj;
			
			for(String tempSourceID:sourceIDList){
				 weightObj=clickCountMap.get(tempSourceID);
				 if(weightObj!=null){
					 if(weightObj.intValue()>sourceWeightChosen){
						 sourceWeightChosen=weightObj.intValue();
						 sourceIDChosen=tempSourceID;
					 }else if(weightObj.intValue()==sourceWeightChosen){
						 long recordUpdateTime_Chosen=soureUpdateMap.get(sourceIDChosen);
						 long recordUpdateTime_temp=soureUpdateMap.get(tempSourceID);
						 
						 if(recordUpdateTime_temp>recordUpdateTime_Chosen){
							 sourceWeightChosen=weightObj.intValue();
							 sourceIDChosen=tempSourceID;
						 }
					 }
					 
				 }
			}
			
			//if(sourceIDList.size()>1)
			//	System.out.println("title:"+title+" sourceID:"+sourceIDChosen+" weightObj:"+sourceWeightChosen+" sourceIDList:"+sourceIDList.size());
			titleFeedID.put(title, sourceIDChosen);
			
		}
		
		
		return titleFeedID;
		
		
	}
	
	public static Map<String,Integer> loadFeedClickCount(String beginDateStr,String endDateStr){
		
		Map<String,Integer> clickCountMap=new HashMap<String,Integer>();
		Connection conn = null;
		try {
			conn = ConnectionUtil.getConnection();
			String queryDeviceInfoSql = "select feedID,count from ClickDataOfMac where buildDate>='"+beginDateStr+"' and buildDate<='"+endDateStr+"';";

			Statement queryIndexStatement = conn.createStatement();

			ResultSet resultSet = queryIndexStatement.executeQuery(queryDeviceInfoSql);
			while (resultSet.next()) {

				
				String  sourceID= resultSet.getString(1).toLowerCase();
				int  clickCount= resultSet.getInt(2);			
				
				Integer countObj=clickCountMap.get(sourceID);
				if(countObj==null)
					countObj=new Integer(0);
				clickCountMap.put(sourceID, clickCount+countObj);
				
			}
			
			return clickCountMap;
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
		
	}
	public static Map<String,Integer> loadKeySearchCount(String beginDateStr,String endDateStr){
		
		Map<String,Integer> searchCountMap=new HashMap<String,Integer>();
		Connection conn = null;
		try {
			conn = ConnectionUtil.getConnection();
			String queryDeviceInfoSql = "select keyword,count from SearchActionData where buildDate>='"+beginDateStr+"' and buildDate<='"+endDateStr+"';";

			Statement queryIndexStatement = conn.createStatement();

			ResultSet resultSet = queryIndexStatement.executeQuery(queryDeviceInfoSql);
			while (resultSet.next()) {

				
				String  keyword= resultSet.getString(1).toLowerCase();
				int  searchCount= resultSet.getInt(2);			
				
				if(keyword.equals(""))
					continue;
				
				keyword=keyword.replaceAll("\t", " ");
				
				Integer countObj=searchCountMap.get(keyword);
				if(countObj==null)
					countObj=new Integer(0);
				searchCountMap.put(keyword, searchCount+countObj);
				

			}
			return searchCountMap;
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
		
	}
	
	public static  Map<String,Integer> titleWithWeight( Map<String,String> titleFeedID, Map<String,Integer> clickCountMap){
		Map<String,Integer> titleWeightMap=new HashMap<String,Integer>();
		
		for(Entry<String,String> entry: titleFeedID.entrySet()){
			String title=entry.getKey();
			String sourceID=entry.getValue();
			int weight=0;
			Integer clickObj=clickCountMap.get(sourceID);
			if(clickObj!=null)
				weight=clickObj.intValue();
			
			titleWeightMap.put(title, weight);
		}
		return titleWeightMap;
	}
	
	
	public static void buildDictionaryOfSource(int type,Map<String,Integer> clickCountMap) throws Exception{
	
		// List<String> terms=loadTerms(type);
		
		// Map<String,Integer> clickCountMap=loadFeedClickCount("2013-10-19","2013-11-19");
		 
		 Map<String,List<String>> titleFeedIDList=new HashMap<String,List<String>>();
		 Map<String,Long>soureUpdateMap=new HashMap<String,Long>();
		 loadSourceTitleMap(titleFeedIDList,soureUpdateMap,type);
		 
		 Map<String,String> titleFeedID=choseMaxSourceID(clickCountMap,titleFeedIDList,soureUpdateMap);
		 
		 Map<String,Integer> titleWithWeightMap=titleWithWeight( titleFeedID, clickCountMap); 
		 
		 writeToFile(titleWithWeightMap ,type+"_title_weight","utf-8");
		 
		 
	}
	/*
	public static void buildDictionaryOfBook() throws Exception{
		int type=1;
		Map<String,Integer> clickCountMap=null;
		 buildDictionaryOfSource( type,clickCountMap) ;
		
	}
	public static void buildDictionaryOfFeed() throws Exception{
		int type=0;
		Map<String,Integer> clickCountMap=null;
		buildDictionaryOfSource( type,clickCountMap) ;
		
	}
	public static void buildDictionaryOfSearchKey(String beginDateStr,String endDateStr){
		
		 Map<String,Integer> keySearchCount=loadKeySearchCount(beginDateStr,endDateStr);
		 writeToFile(keySearchCount ,"3_keyword","utf-8");
	}
	*/

	
	public static void main(String[] args) throws Exception{
		

		ConnectionUtil.init("conf/dbcp_online.properties");
		/*
		 int type=1;
		 List<String> terms=loadTerms(type);
		 
		 String filePath="dic_v";
		 writeToFile(terms,filePath,"utf-8");
		 Map<String,Integer> clickCountMap=loadFeedClickCount("2013-10-19","2013-11-19");
		 Map<String,List<String>> titleFeedIDList=new HashMap<String,List<String>>();
		 Map<String,Long>soureUpdateMap=new HashMap<String,Long>();
		 loadSourceTitleMap(titleFeedIDList,soureUpdateMap);
		 Map<String,String> titleFeedID=choseMaxSourceID(clickCountMap,titleFeedIDList,soureUpdateMap);
		 Map<String,Integer> titleWithWeightMap=titleWithWeight( titleFeedID, clickCountMap); 
		 writeToFile(titleWithWeightMap ,type+"_title_weight","utf-8");
		 */
		 Map<String,Integer> keySearchCount=loadKeySearchCount("2013-10-19","2013-11-19");
		 writeToFile(keySearchCount ,"3_keyword","utf-8");
	}
	
	
}
