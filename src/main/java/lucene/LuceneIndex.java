package lucene;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.FloatField;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.Filter;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

public class LuceneIndex {
	
	
	Directory directory;
	IndexWriter indexWriter;
	IndexReader indexReader;
	IndexSearcher indexSearcher;

	public LuceneIndex( String indexPath){
		
	
        File  f_idx=new File(indexPath);
         
    
		try {
			directory = FSDirectory.open(f_idx);
			IndexWriterConfig conf=new IndexWriterConfig(Version.LUCENE_47,new StandardAnalyzer(Version.LUCENE_47));
			indexWriter=new IndexWriter(directory,conf);
		    
			DirectoryReader.open(indexWriter, false);
		//	indexReader=DirectoryReader.open(directory);
			indexReader=DirectoryReader.open(indexWriter, true);
			indexSearcher=new IndexSearcher(indexReader);
		      
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
  
      
	}
	
	public void removeDoc(String canonicalPath ){
		

        try {    
        	indexWriter.deleteDocuments(new Term("path", canonicalPath));
        	indexWriter.commit();
        	//indexWriter.close();
        } catch (IOException e) {
		
			e.printStackTrace();
        } 
       
        /** update indexSearcher**/
       updateIndexReaderIfNeed();
        
	}
	public void updateDoc(String canonicalPath  ,String body){
		
		Document document=new Document();
		document.add(new StringField("path", canonicalPath,Store.YES));
		document.add(new TextField("body", body,Store.YES));   
		try {
			indexWriter.updateDocument(new Term("path", canonicalPath), document);	
			indexWriter.commit();
			
		} catch (IOException e) {
			e.printStackTrace();
		}   
		
	     /** update indexSearcher**/
        updateIndexReaderIfNeed();
     
	}
	public void addDoc(String canonicalPath ,String body){
		
		   Document document=new Document();
           document.add(new StringField("path", canonicalPath,Store.YES));
           document.add(new TextField("body", body,Store.YES));   
           try {
        	   
        	   indexWriter.addDocument(document);
        	   indexWriter.commit();
           } catch (IOException e) {
			
        	   e.printStackTrace();
           } 
           
           /** update indexSearcher**/
           updateIndexReaderIfNeed();
	}
	
    public   void buildIndex(String dataPath) throws IOException{
  
        File  f_doc=new File(dataPath);
      
         
     
     
        File[] textFiles =f_doc.listFiles();
        for(int i=0;i<textFiles.length;i++)
        {
            if(textFiles[i].isFile())//&&textFiles[i].getName().endsWith( ".txt" )  
            {
                
                System.out.println( " File"+ textFiles[i].getCanonicalPath() +"" );   
                FileInputStream temp=new FileInputStream (textFiles[i]);
                 
               
                int len=temp.available();
                byte[] buffer=new byte[len];
                temp.read(buffer);
                temp.close();
                String content=new String(buffer);
         
                Document document=new Document();
             //   document.add(new Field("path", textFiles[i].getPath(), FieldType.));
                document.add(new StringField("path", textFiles[i].getPath(),Store.YES ));
                document.add(new TextField("body", content, Store.YES));          
                
                /*
                 * just test
                 */
                document.add(new FloatField("score", 59.9F, Store.YES));
                
                indexWriter.addDocument(document); 
                indexWriter.commit();
              
            }
        }
        System.out.println("indexWriter.numDocs:"+indexWriter.numDocs());
       
        /** update indexSearcher**/
        updateIndexReaderIfNeed();
    }
    
    public void findPath(String pathStr)throws IOException,ParseException{
    	
    	   Term pathTerm=new Term("path", pathStr);
    	   Query query=new TermQuery(pathTerm);
           Filter filter = null;
           TopDocs topDocs = indexSearcher.search(query, filter, 10);
           
           System.out.println("total hits:" + topDocs.totalHits + "");
             
           
           for (ScoreDoc scoreDoc : topDocs.scoreDocs) {
            
               int index = scoreDoc.doc; 
             
           
               Document doc =indexSearcher.doc(index);
               System.out.println("------------"+index+1+"----------------");
               System.out.println("path = " + doc.get("path"));
               System.out.println("shardIndex = " +  scoreDoc.shardIndex);
             
           }
           
    }
    public  void queryPath(String querystring) throws IOException,ParseException
    {
  
      
        Analyzer analyzer = new StandardAnalyzer(Version.LUCENE_47);
    
        QueryParser parser=new QueryParser(Version.LUCENE_47,"path",analyzer);
        Query query=parser.parse(querystring);
         
        //Hits TopDocs
        Filter filter = null;
        TopDocs topDocs = indexSearcher.search(query, filter, 10);
         
        System.out.println("hits:" + topDocs.totalHits + "");
     
        for (ScoreDoc scoreDoc : topDocs.scoreDocs) {
          
            int index = scoreDoc.doc; 
          
           
            Document doc =indexSearcher.doc(index);
            System.out.println("------------"+index+1+"----------------");
            System.out.println("path = " + doc.get("path"));
            System.out.println("shardIndex = " +  scoreDoc.shardIndex);
          
        }
       
    }
    
    
    public  void queryBody(String querystring) throws IOException,ParseException
    {
    	
        //
        Analyzer analyzer = new StandardAnalyzer(Version.LUCENE_47);
    
        QueryParser parser=new QueryParser(Version.LUCENE_47,"body",analyzer);
        Query query=parser.parse(querystring);
         
        //
        Filter filter = null;
        TopDocs topDocs = indexSearcher.search(query, filter, 10);//���һ���������Ʋ�ѯ������Ŀ��
         
        System.out.println("totalhists:" + topDocs.totalHits + "");
          
        //
        for (ScoreDoc scoreDoc : topDocs.scoreDocs) {
            //
            int index = scoreDoc.doc; 
          
            //
            Document doc =indexSearcher.doc(index);
            System.out.println("------------"+index+1+"----------------");
            System.out.println("path = " + doc.get("path"));
            System.out.println("shardIndex = " +  scoreDoc.shardIndex);
          
        }
       
    }
         
    private  void updateIndexReaderIfNeed(){
    	try {
            IndexReader newIndexReader = DirectoryReader.openIfChanged((DirectoryReader) indexReader, indexWriter, false);//reader.reopen();      // ���������ӵ������������ݣ�����ʵʱ��������
            if (newIndexReader != null) {
            	
            	System.out.println("Index Changed update indexReader");
            	
            	indexReader.close();
            	indexReader = newIndexReader;
                indexSearcher = new IndexSearcher(indexReader);
            }
           
        }catch (IOException e) {
        	e.printStackTrace();
        }
    }
    
    public void info(){
        try {
            IndexReader indexReader = DirectoryReader.open(directory);
            System.out.println("numDocs:" + indexReader.numDocs());
            System.out.println("maxDoc:" + indexReader.maxDoc());
            System.out.println("numDeletedDocs:" + indexReader.numDeletedDocs());
            indexReader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void close(){
    	
    	
    	 try {
			 indexReader.close();
	    	 indexWriter.close();
	    	 directory.close();
	    	 
    	 } catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		};
    
    }
    public static void main(String[] args) throws IOException, ParseException {
      
    	LuceneIndex aLuceneIndex=new LuceneIndex("Lucene/index");
    	aLuceneIndex.buildIndex("Lucene/data");
    	//
    	//aLuceneIndex.removeDoc("new Path5");
    	aLuceneIndex.addDoc("new Path5", "noting");
    	aLuceneIndex.findPath("new Path5");
    	aLuceneIndex.queryPath("new Path5");
    	aLuceneIndex.queryBody("创建");
    	
    //	aLuceneIndex.removeDoc("new Path5");
    	aLuceneIndex.info();
    	aLuceneIndex.close();
    //	aLuceneIndex.removeDoc("new Path5");
    //	aLuceneIndex.info();
 
    }
}
