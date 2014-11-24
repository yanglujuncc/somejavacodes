package lucene.suggest;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.search.spell.Dictionary;
import org.apache.lucene.search.suggest.FileDictionary;
import org.apache.lucene.search.suggest.Lookup.LookupResult;
import org.apache.lucene.search.suggest.analyzing.AnalyzingSuggester;
import org.apache.lucene.search.suggest.analyzing.FuzzySuggester;
import org.apache.lucene.util.Version;



public class QuerySuggester {

	protected static Logger logger = Logger.getLogger(QuerySuggester.class.getName());

	
	AnalyzingSuggester analyzingSuggester;
	FuzzySuggester fuzzySuggester;
	
	public class QuerySuggestion{
		public String key;
		public  long value;
	}
	
	public void init(String dicPath) throws Exception{
		
		 Analyzer analyzerOfFuzzy = new StandardAnalyzer(Version.LUCENE_45);
		 fuzzySuggester=new FuzzySuggester(analyzerOfFuzzy);
		 fuzzySuggester.build(getDictionary(dicPath));
		 
		 Analyzer analyzerOfanalyzing = new StandardAnalyzer(Version.LUCENE_45);
		 analyzingSuggester=new AnalyzingSuggester(analyzerOfanalyzing);
		 analyzingSuggester.build(getDictionary(dicPath));
		 
	}
	
	private  List<LookupResult> lookup(String inputStr,int maxResult){
		
		 LinkedList<LookupResult> lookupResults=new  LinkedList<LookupResult>();
		  List<LookupResult> lookupResultsOfAnalyzing=analyzingSuggester.lookup(inputStr, false, maxResult);
		  if(lookupResultsOfAnalyzing!=null)
			  lookupResults.addAll(lookupResultsOfAnalyzing);
		  
		  
		  logger.info("AnalyzingResult:"+lookupResultsOfAnalyzing.size());
		  if(lookupResults.size()<maxResult){
			  //do FuzzySuggester
			  int need=maxResult-lookupResults.size();
			  
			  logger.info("need more result,do FuzzySuggester,need:"+need);
			  
			  Set<String> exitsKeySet=new HashSet<String>();
			  for(LookupResult lookupResult:lookupResults){
				  exitsKeySet.add(lookupResult.key.toString());
			  }
			  
			
			  List<LookupResult> lookupResultsOfFuzzy=fuzzySuggester.lookup(inputStr, false,  maxResult);
			  logger.info("FuzzyResult:"+lookupResultsOfFuzzy.size());
			  int addOfFuzzy=0;
			  if(lookupResultsOfFuzzy!=null){
			
				 for(LookupResult lookupResultOfFuzzy: lookupResultsOfFuzzy){
					 if(!exitsKeySet.contains(lookupResultOfFuzzy.key.toString()))
					 {		
						 lookupResults.add(lookupResultOfFuzzy);
						 addOfFuzzy++;			 
						 if(addOfFuzzy==need)
							 break;
					 }
				 }
			 }
			  logger.info("Add FuzzyResult:"+addOfFuzzy);
			  
		  }
		  
		  return lookupResults;
	}
	public  List<QuerySuggestion> suggest(String inputStr,int maxResult){
		
			List<QuerySuggestion> querySuggestions=new  LinkedList<QuerySuggestion>();
		 
			List<LookupResult> lookupResults=lookup( inputStr, maxResult);
			
			for(LookupResult lookupResult:lookupResults){
				
				QuerySuggestion aQuerySuggestion=new QuerySuggestion();
				aQuerySuggestion.key=lookupResult.key.toString();
				aQuerySuggestion.value=lookupResult.value;
				
				querySuggestions.add(aQuerySuggestion);
			}
		 
		 return querySuggestions;
	}
	public static void testFuzzySuggester(Dictionary  dictionary, String inputStr) throws Exception{
		
		long begin=System.currentTimeMillis();
		Analyzer analyzer = new StandardAnalyzer(Version.LUCENE_45);
		FuzzySuggester fuzzySuggester=new FuzzySuggester(analyzer);
		fuzzySuggester.build(dictionary);
		
		   System.out.println("       DEFAULT_MAX_EDITS:"+FuzzySuggester.DEFAULT_MAX_EDITS);
		   System.out.println("DEFAULT_MIN_FUZZY_LENGTH:"+FuzzySuggester.DEFAULT_MIN_FUZZY_LENGTH);
		   System.out.println("DEFAULT_NON_FUZZY_PREFIX:"+FuzzySuggester.DEFAULT_NON_FUZZY_PREFIX);
		   System.out.println("  DEFAULT_TRANSPOSITIONS:"+FuzzySuggester.DEFAULT_TRANSPOSITIONS);
		   System.out.println("   DEFAULT_UNICODE_AWARE:"+FuzzySuggester.DEFAULT_UNICODE_AWARE);
		   
	    long cost=System.currentTimeMillis()-begin;
	    System.out.println("build:"+cost+"'ms");
	       
		begin=System.currentTimeMillis();
      
       List<LookupResult> lookupResults=fuzzySuggester.lookup(inputStr, false, 1000);
       
   
       cost=System.currentTimeMillis()-begin;
       System.out.println("lookup cost:"+cost+"'s");
       
       
       System.out.println("FuzzySuggester lookupResults:"+lookupResults.size());
 //      Collections.sort(lookupResults);
       for(LookupResult lookupResult:lookupResults){
    	   System.out.println(lookupResult);
    	  // System.out.println(lookupResult.key);
    	  // System.out.println(lookupResult.value);
    	   //System.out.println(lookupResult.payload);
       }
}
public static void testAnalyzingSuggester(Dictionary  dictionary, String inputStr) throws Exception{
	
	long begin=System.currentTimeMillis();
	Analyzer analyzer = new StandardAnalyzer(Version.LUCENE_45);
	AnalyzingSuggester analyzingSuggester=new AnalyzingSuggester(analyzer);
	analyzingSuggester.build(dictionary);
	   

    long cost=System.currentTimeMillis()-begin;
    System.out.println("build:"+cost+"'ms");
       
	begin=System.currentTimeMillis();
  
   List<LookupResult> lookupResults=analyzingSuggester.lookup(inputStr, false, 1000);
   
   cost=System.currentTimeMillis()-begin;
   System.out.println("lookup cost:"+cost+"'s");
   
   
   System.out.println("AnalyzingSuggester lookupResults:"+lookupResults.size());
  // Collections.sort(lookupResults);
   
   for(LookupResult lookupResult:lookupResults){
	   System.out.println(lookupResult);
   }
}
public static Dictionary getDictionary(String filePath) throws Exception{
	File dicFile=new File(filePath);
	FileInputStream fileInputStream=new FileInputStream(dicFile);
	InputStreamReader inputStreamReader=new InputStreamReader(fileInputStream,"utf-8");
	BufferedReader bufferedReader=new BufferedReader(inputStreamReader);
//	PlainTextDictionary  dictionary=new PlainTextDictionary(dicFile);
	FileDictionary  dictionary=new FileDictionary(bufferedReader);
	
	//bufferedReader.close();
	return dictionary;
}
public static void main(String[] args) throws Exception {


	String inputStr="Èý¹ú";
	String filePath="0_title_weight";
//	Dictionary dictionary=getDictionary(filePath);
//	testAnalyzingSuggester( dictionary,  inputStr);	
	//testFuzzySuggester(   getDictionary() ,  inputStr);
	QuerySuggester aQuerySuggester=new QuerySuggester();
	long startTime=System.currentTimeMillis();
	aQuerySuggester.init(filePath);
	long cost=System.currentTimeMillis()-startTime;
	System.out.println("build cost:"+cost+"'ms");
	
	startTime=System.currentTimeMillis();
	List<LookupResult> lookupResults=aQuerySuggester.lookup(inputStr, 100);
	cost=System.currentTimeMillis()-startTime;
	System.out.println("query cost:"+cost+"'ms");
	
	
	for(LookupResult lookupResult:lookupResults){
		   System.out.println(lookupResult);
	}
	
	
}
}
