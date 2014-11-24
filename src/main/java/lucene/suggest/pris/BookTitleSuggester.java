package lucene.suggest.pris;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;










import java.util.Set;

import lucene.suggest.QueryIndexer;
import lucene.suggest.QuerySuggester;
import lucene.suggest.QuerySuggester.QuerySuggestion;

import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;

import util.ConnectionUtil;

public class BookTitleSuggester {

	protected static Logger logger = Logger.getLogger(BookTitleSuggester.class.getName());

	
	QuerySuggester querySuggester;
	QueryIndexer queryIndexer;
	
	Map<String,String> title2IdMap;
	
	public void init(String beginDateStr,String endDateStr) throws Exception{
		
		int type=1;
		String dicFilePath="suggest/dic_book";
		Map<String,Integer> clickCountMap=PIRSDataUtil.loadFeedClickCount(beginDateStr,endDateStr);
		
		 Map<String,List<String>> titleFeedIDList=new HashMap<String,List<String>>();
		 Map<String,Long>soureUpdateMap=new HashMap<String,Long>();
		 
		 PIRSDataUtil.loadSourceTitleMap(titleFeedIDList,soureUpdateMap,type);
		 
		 Map<String,String> titleFeedID=PIRSDataUtil.choseMaxSourceID(clickCountMap,titleFeedIDList,soureUpdateMap);
		 
		 Map<String,Integer> titleWithWeightMap=PIRSDataUtil.titleWithWeight( titleFeedID, clickCountMap); 
		
		 PIRSDataUtil. writeToFile(titleWithWeightMap ,dicFilePath,"utf-8");
		 
		 
		querySuggester=new QuerySuggester();
		querySuggester.init(dicFilePath);
		
		queryIndexer=new QueryIndexer();
		queryIndexer.init(titleFeedID);
		
		title2IdMap=titleFeedID;
		
	}
	
	
	public class BookTitleSuggestion{
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
	public List<BookTitleSuggestion>  suggest(String inputStr,int maxResult){
		
		logger.info(" call suggest,args  inputStr:"+inputStr+" maxResult:"+maxResult);
		Set<String> termsSet=new HashSet<String>();
		
		List<BookTitleSuggestion>  result=new LinkedList<BookTitleSuggestion>();
		
		List<QuerySuggestion> querySuggestions=querySuggester.suggest(inputStr, maxResult);
		for(QuerySuggestion querySuggestion:querySuggestions){
			
			if(!termsSet.contains(querySuggestion.key)){
				
				BookTitleSuggestion bookTitleSuggestion=new BookTitleSuggestion();
				bookTitleSuggestion.title=querySuggestion.key;
				bookTitleSuggestion.feedID=title2IdMap.get(bookTitleSuggestion.title);
				bookTitleSuggestion.clickCount=querySuggestion.value;
				result.add(bookTitleSuggestion);
				
				termsSet.add(querySuggestion.key);		
				
			}
			
		}
		
		logger.info(" get "+result.size()+" results from querySuggester ");
		//if 
		if(result.size()<maxResult){
			int need=maxResult-result.size();
			logger.info(" need more results,call queryIndexer , need:"+need);
			int accept=0;
			 List<String> termResults=queryIndexer.query(inputStr, maxResult);
			 logger.info(" get "+termResults.size()+" results from queryIndexer ");
			 for(String term:termResults){
				 
					if(!termsSet.contains(term)){
						
						BookTitleSuggestion bookTitleSuggestion=new BookTitleSuggestion();
						bookTitleSuggestion.title=term;
						bookTitleSuggestion.feedID=title2IdMap.get(bookTitleSuggestion.title);
						bookTitleSuggestion.clickCount=0;
						result.add(bookTitleSuggestion);
						
						termsSet.add(term);	
						accept++;
						
						if(result.size()>=maxResult){
							break;
						}
					}
					
			 }
			 logger.info(" accept "+accept+" results from queryIndexer ");
		}
		
		
		return result;
	}
	public static void main(String[] args) throws Exception{
		
		DOMConfigurator.configureAndWatch("conf/log4j.xml");
		ConnectionUtil.init("conf/dbcp_online.properties");
		long startTime=System.currentTimeMillis();
		BookTitleSuggester aBookTitleSuggester=new BookTitleSuggester();
		aBookTitleSuggester.init("2013-11-01","2013-11-21");
		long cost=System.currentTimeMillis()-startTime;
		System.out.println("build cost:"+cost+"'ms");
		
		startTime=System.currentTimeMillis();
		List<BookTitleSuggestion>  bookTitleSuggestions=aBookTitleSuggester.suggest("Èý¹ú", 1000);
		 cost=System.currentTimeMillis()-startTime;
		System.out.println("suggest cost:"+cost+"'ms");
		for(BookTitleSuggestion bookTitleSuggestion:bookTitleSuggestions){
			System.out.println(bookTitleSuggestion);
		}
		
	}
}
