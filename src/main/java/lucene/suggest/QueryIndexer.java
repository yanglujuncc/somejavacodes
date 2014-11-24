package lucene.suggest;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import lucene.suggest.QuerySuggester.QuerySuggestion;


import org.apache.log4j.Logger;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.RAMDirectory;
import org.apache.lucene.util.Version;

public class QueryIndexer {

	
	
	protected static Logger logger = Logger.getLogger(QueryIndexer.class.getName());

	
	Analyzer analyzer;
	Directory directory ;
    DirectoryReader ireader ;
	IndexSearcher isearcher;
	QueryParser parser;
	
	 public void init(List<String> terms){
		 
		 analyzer = new StandardAnalyzer(Version.LUCENE_45);
		 
	        // Store the index in memory:
	      directory = new RAMDirectory();
	  	try {  
	        // To store an index on disk, use this instead:
	        //Directory directory = FSDirectory.open("/tmp/testindex");
	      IndexWriterConfig config = new IndexWriterConfig(Version.LUCENE_45, analyzer);
	      IndexWriter iwriter = new IndexWriter(directory, config);
		 
	      for(String term: terms){
	
	    	    Document doc = new Document();   	    
		        Field queryField= new   TextField("text",term,Store.YES ); 
		        doc.add(queryField);
		        iwriter.addDocument(doc);
		      
	      }
	      iwriter.commit();
	      iwriter.close();
	      
	      ireader = DirectoryReader.open(directory);
	      isearcher = new IndexSearcher(ireader);
	         
	        // Parse a simple query that searches for "text":
	       parser = new QueryParser(Version.LUCENE_45, "title", analyzer);
	  	} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			 	      
	     
	 }
	 public void init(Map<String,String> termMap){
		 
		List<String> terms=new LinkedList<String>();
		for(Entry<String,String> entry:termMap.entrySet()){
			terms.add(entry.getKey());
		}			 	    
		init(terms);
		
	 }
	public  List<String> query(String inputStr,int maxResult){
			
			List<String> resultList=new LinkedList<String>();
					
			Query query;
			 ScoreDoc[] hits=null;
			try {
				query = parser.parse(inputStr);
				hits =isearcher.search(query, null, 1000).scoreDocs;
				if(hits!=null){
					for(ScoreDoc scoreDoc:hits){					
					    Document hitDoc=null;			
						hitDoc = isearcher.doc(scoreDoc.doc);
						
						if(resultList.size()<maxResult)
							resultList.add(hitDoc.get("text")) ;				
					}
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			return resultList;
		}

}
