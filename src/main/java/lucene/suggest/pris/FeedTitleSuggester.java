package lucene.suggest.pris;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import lucene.suggest.QuerySuggester;
import lucene.suggest.QuerySuggester.QuerySuggestion;

import org.apache.log4j.xml.DOMConfigurator;

import util.ConnectionUtil;

public class FeedTitleSuggester {
	
	QuerySuggester querySuggester;
	
	Map<String,String> title2IdMap;
	
	public void init(String beginDateStr,String endDateStr) throws Exception{
		
		int type=0;
		String dicFilePath="suggest/dic_feed";
		Map<String,Integer> clickCountMap=PIRSDataUtil.loadFeedClickCount(beginDateStr,endDateStr);
		
		 Map<String,List<String>> titleFeedIDList=new HashMap<String,List<String>>();
		 Map<String,Long>soureUpdateMap=new HashMap<String,Long>();
		 
		 PIRSDataUtil.loadSourceTitleMap(titleFeedIDList,soureUpdateMap,type);
		 
		 Map<String,String> titleFeedID=PIRSDataUtil.choseMaxSourceID(clickCountMap,titleFeedIDList,soureUpdateMap);
		 
		 Map<String,Integer> titleWithWeightMap=PIRSDataUtil.titleWithWeight( titleFeedID, clickCountMap); 
		
		 PIRSDataUtil. writeToFile(titleWithWeightMap ,dicFilePath,"utf-8");
		 
		 
		querySuggester=new QuerySuggester();
		querySuggester.init(dicFilePath);
		
		title2IdMap=titleFeedID;
		
	}
	
	
	public class FeedTitleSuggestion{
		String title;
		String feedID;
		long clickCount;
		
		public String getTitle(){
			return title;
		}
		public String getFeedID(){
			return feedID;
		}
		public long getClickCount(){
			return clickCount;
		}
		
		
		
		public String toString(){
			return title+"/"+clickCount+"/"+feedID;
		}
	}
	public List<FeedTitleSuggestion>  suggest(String inputStr,int maxResult){
		List<FeedTitleSuggestion>  result=new LinkedList<FeedTitleSuggestion>();
		
		List<QuerySuggestion> querySuggestions=querySuggester.suggest(inputStr, maxResult);
		for(QuerySuggestion querySuggestion:querySuggestions){
			
			FeedTitleSuggestion feedTitleSuggestion=new FeedTitleSuggestion();
			feedTitleSuggestion.title=querySuggestion.key;
			feedTitleSuggestion.feedID=title2IdMap.get(feedTitleSuggestion.title);
			feedTitleSuggestion.clickCount=querySuggestion.value;
			
			result.add(feedTitleSuggestion);
		}
		
		
		return result;
	}
	public static void main(String[] args) throws Exception{
		
		DOMConfigurator.configureAndWatch("conf/log4j.xml");
		ConnectionUtil.init("conf/dbcp_online.properties");
		long startTime=System.currentTimeMillis();
		FeedTitleSuggester feedTitleSuggester=new FeedTitleSuggester();
		feedTitleSuggester.init("2013-11-01","2013-11-21");
		long cost=System.currentTimeMillis()-startTime;
		System.out.println("build cost:"+cost+"'ms");
		
		startTime=System.currentTimeMillis();
		List<FeedTitleSuggestion>  feedTitleSuggestions=feedTitleSuggester.suggest("Эјвз", 1000);
		 cost=System.currentTimeMillis()-startTime;
		System.out.println("suggest cost:"+cost+"'ms");
		for(FeedTitleSuggestion feedTitleSuggestion:feedTitleSuggestions){
			System.out.println(feedTitleSuggestion);
		}
		
	}
}
