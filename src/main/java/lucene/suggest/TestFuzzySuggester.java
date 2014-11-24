package lucene.suggest;

import org.apache.lucene.search.suggest.analyzing.FuzzySuggester;
import org.apache.lucene.util.automaton.LevenshteinAutomata;

public class TestFuzzySuggester {
	 public static void main(String[] args) throws Exception {
		    
		  
	    	String inputStr="ÌÚÑ¶Íø¿Æ¼¼";
	    	
	    	System.out.println("       DEFAULT_MAX_EDITS:"+FuzzySuggester.DEFAULT_MAX_EDITS);
	    	System.out.println("DEFAULT_MIN_FUZZY_LENGTH:"+FuzzySuggester.DEFAULT_MIN_FUZZY_LENGTH);
	    	System.out.println("DEFAULT_NON_FUZZY_PREFIX:"+FuzzySuggester.DEFAULT_NON_FUZZY_PREFIX);
	    	System.out.println("  DEFAULT_TRANSPOSITIONS:"+FuzzySuggester.DEFAULT_TRANSPOSITIONS);
	    	System.out.println("   DEFAULT_UNICODE_AWARE:"+FuzzySuggester.DEFAULT_UNICODE_AWARE);
	    	System.out.println("   MAXIMUM_SUPPORTED_DISTANCE:"+LevenshteinAutomata.MAXIMUM_SUPPORTED_DISTANCE);
	    	
	    	
	    	
	    }
}
