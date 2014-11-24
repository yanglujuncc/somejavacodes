package lucene.suggest;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;




import java.io.InputStreamReader;
import java.util.Collections;
import java.util.List;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.search.suggest.FileDictionary;
import org.apache.lucene.search.suggest.Lookup;
import org.apache.lucene.search.suggest.Lookup.LookupResult;
import org.apache.lucene.search.suggest.analyzing.AnalyzingSuggester;
import org.apache.lucene.search.suggest.analyzing.FuzzySuggester;
import org.apache.lucene.search.suggest.fst.FSTCompletionLookup;
import org.apache.lucene.search.suggest.fst.WFSTCompletionLookup;
import org.apache.lucene.search.suggest.jaspell.JaspellLookup;
import org.apache.lucene.search.suggest.tst.TSTLookup;
import org.apache.lucene.search.spell.Dictionary;
import org.apache.lucene.util.Version;
public class testLuceneSuggest {

	
	public static void testTSTLookup(Dictionary  dictionary, String inputStr) throws Exception{
		
		   Lookup tstLookup=new TSTLookup();
	       tstLookup.build(dictionary);
	       
	       
	       List<LookupResult> lookupResults= tstLookup.lookup(inputStr, true, 1000);
	       System.out.println("TSTLookup lookupResults:"+lookupResults.size());
	 //      Collections.sort(lookupResults);
	       for(LookupResult lookupResult:lookupResults){
	    	   System.out.println(lookupResult);
	       }
	}
	public static void testJaspellLookup(Dictionary  dictionary, String inputStr) throws Exception{
		
		   Lookup lookup=new JaspellLookup();
		   lookup.build(dictionary);
	
	       
	       List<LookupResult> lookupResults= lookup.lookup(inputStr, true, 1000);
	       System.out.println("JaspellLookup lookupResults:"+lookupResults.size());
	 //      Collections.sort(lookupResults);
	       for(LookupResult lookupResult:lookupResults){
	    	   System.out.println(lookupResult);
	       }
	}
	
	public static void testFSTCompletionLookup(Dictionary  dictionary, String inputStr) throws Exception{
		
		   Lookup lookup=new FSTCompletionLookup();
		   lookup.build(dictionary);
	       
	       
	       List<LookupResult> lookupResults= lookup.lookup(inputStr, true, 1000);
	       System.out.println("FSTCompletionLookup lookupResults:"+lookupResults.size());
	    //   Collections.sort(lookupResults);
	       for(LookupResult lookupResult:lookupResults){
	    	   System.out.println(lookupResult);
	       }
	}
	public static void testWFSTCompletionLookup(Dictionary  dictionary, String inputStr) throws Exception{
		
		  long begin=System.currentTimeMillis();
		
		   Lookup lookup=new WFSTCompletionLookup();
		   lookup.build(dictionary);
	       
		   long cost=System.currentTimeMillis()-begin;
		    System.out.println("WFSTCompletionLookup build:"+cost+"'ms");
		    
		    
			begin=System.currentTimeMillis();
	       List<LookupResult> lookupResults= lookup.lookup(inputStr,false, 1000);
	     
	       cost=System.currentTimeMillis()-begin;
	       System.out.println("lookup cost:"+cost+"'ms");
	       

	       System.out.println("lookupResults:"+lookupResults.size());
	 //      Collections.sort(lookupResults);
	       for(LookupResult lookupResult:lookupResults){
	    	   System.out.println(lookupResult);
	       }
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
	public static Dictionary getDictionary() throws Exception{
		File dicFile=new File("dic_v");
    	FileInputStream fileInputStream=new FileInputStream(dicFile);
    	InputStreamReader inputStreamReader=new InputStreamReader(fileInputStream,"utf-8");
    	BufferedReader bufferedReader=new BufferedReader(inputStreamReader);
    //	PlainTextDictionary  dictionary=new PlainTextDictionary(dicFile);
    	FileDictionary  dictionary=new FileDictionary(bufferedReader);
    	
    	return dictionary;
	}
    public static void main(String[] args) throws Exception {
    
  
    	String inputStr="ÌÚÑ¶Íø¿Æ¼¼";
    	
    	testTSTLookup(   getDictionary() ,  inputStr);
    	testJaspellLookup(getDictionary(),  inputStr);
    	testFSTCompletionLookup(   getDictionary() ,  inputStr);
        testWFSTCompletionLookup(   getDictionary() ,  inputStr);
  
    	
    	testAnalyzingSuggester(  getDictionary(),  inputStr);	
    	testFuzzySuggester(   getDictionary() ,  inputStr);
    }
    
    
    
    
}
