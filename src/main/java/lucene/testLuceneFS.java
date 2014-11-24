package lucene;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
 
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.Filter;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
public class testLuceneFS {
	
	/**
	 * 
	 * @author davenzhang
	 * @email zsnjuim@163.com
	 */

	    //该函数用于创建索引。可以将其写成一个单独的类。
	    public  static void createIndex() throws IOException{
	        //数据存放文件夹
	        File  f_doc=new File("Lucene/data");
	        //索引存放文件夹
	        File  f_idx=new File("Lucene/index");
	         
	        //directory参数，用于传入IndexWriter
	        Directory directory=FSDirectory.open(f_idx);
	        //conf参数，用于传入IndexWriter.
	        IndexWriterConfig conf=new IndexWriterConfig(Version.LUCENE_45,new StandardAnalyzer(Version.LUCENE_45));
	        IndexWriter writer=new IndexWriter(directory,conf);
	         
	        //对数据文件夹下面的所有文件建立索引;
	        File[] textFiles =f_doc.listFiles();
	        for(int i=0;i<textFiles.length;i++)
	        {
	            if(textFiles[i].isFile())//&&textFiles[i].getName().endsWith( ".txt" )  
	            {
	                Document document=new Document();//注意,这个Document必须在循环中创建，博主之前一直在外面创建。
	                System.out.println( " File"+ textFiles[i].getCanonicalPath() +"正在被索引 . " );   
	                FileInputStream temp=new FileInputStream (textFiles[i]);
	                 
	                //获取文件内容
	                int len=temp.available();
	                byte[] buffer=new byte[len];
	                temp.read(buffer);
	                temp.close();
	                String content=new String(buffer);
	 
	                //System.out.println(content);//测试
	                //加入Field
	                document.add(new Field("path", textFiles[i].getPath(), TextField.TYPE_STORED));
	                document.add(new Field("body", content, TextField.TYPE_STORED));          
	                
	                
	                
	                writer.addDocument(document); 
	 
	                writer.commit();//必须先提交，不然的会报错！
	                 
	            }
	        }
	        System.out.println("索引的数目："+writer.numDocs());
	        writer.close();  
	    }
	     
	    //查询函数
	    public static void TestQuery(String querystring) throws IOException,ParseException
	    {
	        //创建一个IndexSearcher
	        File  f=new File("Lucene/index");
	        Directory directory =FSDirectory.open(f);   
	        IndexReader ireader=DirectoryReader.open(directory);
	        IndexSearcher searcher=new IndexSearcher(ireader);//这个和以前的版本有所变化。
	         
	        //查询词解析
	        Analyzer analyzer = new StandardAnalyzer(Version.LUCENE_45);
	    
	        QueryParser parser=new QueryParser(Version.LUCENE_45,"body",analyzer);
	        Query query=parser.parse(querystring);
	         
	        //以前的Hits已经不再使用。直接使用TopDocs即可。
	        Filter filter = null;
	        TopDocs topDocs = searcher.search(query, filter, 10);//最后一个参数，限制查询结果的条目。
	         
	        System.out.println("总共有【" + topDocs.totalHits + "】条匹配结果");
	          
	        //显示结果
	        for (ScoreDoc scoreDoc : topDocs.scoreDocs) {
	            //文档内部编
	            int index = scoreDoc.doc; 
	            //根据编号取出相应的文档
	            Document doc =searcher.doc(index);
	            System.out.println("------------"+index+1+"----------------");
	            System.out.println("path = " + doc.get("path"));
	       //     System.out.println("content = " + doc.get("body"));
	            }
	        ireader.close();
	    }
	         
	 
	 
	  

	  //主函数
    public static void main(String[] args) throws IOException, ParseException{
         
    	//  createIndex(); //该句用于创建索引，第一次运行时将"//"去掉，以后就加上注释，不然索引会重复创建。
        //检索词
        String querystring="mysql";
     
        System.out.println("您的检索词为：【"+querystring+"】");
       
        TestQuery(querystring);
    }
 
}
