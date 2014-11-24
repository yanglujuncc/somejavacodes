package lucene;


import java.io.IOException;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.RAMDirectory;
import org.apache.lucene.util.Version;
public class testLuceneRAM {


		
		 /**
	     * @param args
	     * @throws IOException 
	     * @throws ParseException 
	     */
	    public static void main(String[] args) throws IOException, ParseException {
	        // TODO Auto-generated method stub
	 
	        Analyzer analyzer = new StandardAnalyzer(Version.LUCENE_45);
	 
	        // Store the index in memory:
	        Directory directory = new RAMDirectory();
	         
	        // To store an index on disk, use this instead:
	        //Directory directory = FSDirectory.open("/tmp/testindex");
	        IndexWriterConfig config = new IndexWriterConfig(Version.LUCENE_45, analyzer);
	        IndexWriter iwriter = new IndexWriter(directory, config);
	        
	        //create document 
	        Document doc = new Document();
	        String text = "This is the text to be indexed.";
	        doc.add(new Field("content",text,TextField.TYPE_STORED));
	        doc.add(new Field("title","atitle",TextField.TYPE_STORED));
	       
	        //add document  to index
	        iwriter.addDocument(doc);
	        iwriter.close();
	         
	        
	        // Now search the index:
	        DirectoryReader ireader = DirectoryReader.open(directory);
	        IndexSearcher isearcher = new IndexSearcher(ireader);
	         
	        // Parse a simple query that searches for "text":
	        QueryParser parser = new QueryParser(Version.LUCENE_45, "title", analyzer);
	        Query query = parser.parse("atitle");
	      
	     
	        ScoreDoc[] hits = isearcher.search(query, null, 1000).scoreDocs;
	       
	        // Iterate through the results:
	        for (int i = 0; i < hits.length; i++) {
	        
	          Document hitDoc = isearcher.doc(hits[i].doc);
	          System.out.println("filecontent:"+ hitDoc.get("content"));
	        }
	        ireader.close();
	        directory.close();
	   
		    
	}
}
