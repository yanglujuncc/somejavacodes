package lucene.suggest.pris;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import lucene.suggest.QuerySuggester;
import lucene.suggest.QuerySuggester.QuerySuggestion;
import lucene.suggest.pris.FeedTitleSuggester.FeedTitleSuggestion;

import org.apache.log4j.xml.DOMConfigurator;

import util.ConnectionUtil;

public class SearchKeywordSuggester {
	
	QuerySuggester querySuggester;	
	
	public void init(String beginDateStr,String endDateStr) throws Exception{
		
		String dicFilePath="suggest/dic_search";
		Map<String,Integer> clickCountMap=PIRSDataUtil.loadKeySearchCount(beginDateStr,endDateStr);
		
		PIRSDataUtil. writeToFile(clickCountMap ,dicFilePath,"utf-8"); 
		 
		querySuggester=new QuerySuggester();
		querySuggester.init(dicFilePath);
		
		
	}
	
	
	public class SearchKeywordSuggestion{
		
		String keyword;
		long searchCount;
		
		public String getKeyword(){
			return keyword;
		}

		public long getSearchCount(){
			return searchCount;
		}
		
		public String toString(){
			return keyword+"/"+searchCount;
		}
	}
	public List<SearchKeywordSuggestion>  suggest(String inputStr,int maxResult){
		List<SearchKeywordSuggestion>  result=new LinkedList<SearchKeywordSuggestion>();
		
		List<QuerySuggestion> querySuggestions=querySuggester.suggest(inputStr, maxResult);
		for(QuerySuggestion querySuggestion:querySuggestions){
			
			SearchKeywordSuggestion searchKeywordSuggestion=new SearchKeywordSuggestion();
			searchKeywordSuggestion.keyword=querySuggestion.key;
			searchKeywordSuggestion.searchCount=querySuggestion.value;

			
			result.add(searchKeywordSuggestion);
		}
		
		
		return result;
	}
	public static void main(String[] args) throws Exception{
		
		DOMConfigurator.configureAndWatch("conf/log4j.xml");
		ConnectionUtil.init("conf/dbcp_online.properties");
		long startTime=System.currentTimeMillis();
		SearchKeywordSuggester searchKeywordSuggester=new SearchKeywordSuggester();
		searchKeywordSuggester.init("2013-11-01","2013-11-21");
		long cost=System.currentTimeMillis()-startTime;
		System.out.println("build cost:"+cost+"'ms");
		
		startTime=System.currentTimeMillis();
		List<SearchKeywordSuggestion>  searchKeywordSuggestions=searchKeywordSuggester.suggest("Эјвз", 1000);
		 cost=System.currentTimeMillis()-startTime;
		System.out.println("suggest cost:"+cost+"'ms");
		for(SearchKeywordSuggestion searchKeywordSuggestion:searchKeywordSuggestions){
			System.out.println(searchKeywordSuggestion);
		}
		
	}
}
