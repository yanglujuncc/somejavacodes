package HBase;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.KeyValue;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;



public class HBasePerformanceTest {

	static String tableName="test";
	static String columnFamily="cf";
	static String column="value";
	
	static	public void putTest(HTable htable,long recordNum) throws Exception {
		
		long timeBegin=System.currentTimeMillis();
		for(int i=0;i<recordNum;i++){
			
			String row=i+"";
			String data="value_"+i;
		    Put putrow = new Put(row.getBytes()); 
	        putrow.add(columnFamily.getBytes(), column.getBytes(), data.getBytes());  
	        
	        htable.put(putrow);  
	        
		}
		if(!htable.isAutoFlush())
			htable.flushCommits();
		
		long timeCost=System.currentTimeMillis()-timeBegin;
		
		long putPerSec=recordNum*1000/timeCost;
		
		System.out.println("Put:"+recordNum+" Cost:"+(timeCost/1000)+"'s"+"      "+putPerSec+"/s");
		
		
	}
	
	static	public void putBatchTest(HTable htable,long recordNum,int putListLength) throws Exception {
		
		htable.setAutoFlush(false);
		
		long timeBegin=System.currentTimeMillis();
		for(int i=0;i<recordNum;i++){
			
			String row=i+"";
			String data="value_"+i;
		    Put putrow = new Put(row.getBytes()); 
	        putrow.add(columnFamily.getBytes(), column.getBytes(), data.getBytes());  
	        
	        htable.put(putrow);  
	        
		}
		
		long timeCost=System.currentTimeMillis()-timeBegin;
		
		long putPerSec=recordNum*1000/timeCost;
		
		System.out.println("Put:"+recordNum+" Cost:"+(timeCost/1000)+"'s"+"      "+putPerSec+"/s");
		
		
	}

	static public void getTest(HTable htable,int recordNum) {

	}

	static public void scanTest(HTable htable,byte[] family,byte[] column) throws Exception {

		   Scan s = new Scan();  
		   s.addFamily(family);
		 //  s.addColumn(family, column);
		   s.setMaxVersions();
		   s.setBatch(1000); //一次返回几条
		   
		   
		   ResultScanner rs = htable.getScanner(s);  
		   for (Result r : rs) {  
		     for (KeyValue kv : r.raw()) {  
		    
		       System.out.print("rowkey : " + new String(kv.getRow(),"utf-8") + "  ");  
		       System.out.println(new String(kv.getFamily()) + ":"  
		           + new String(kv.getQualifier()) + " timestamp " + kv.getTimestamp()  
		           + " => ");  
		       
		       ;
		     }  
		   }  
		
	}

	public static void main(String[] args) throws Exception {
		
		
		Configuration cfg = HBaseConfiguration.create();
		// Configuration conf = new HBaseConfiguration();
		// conf.addResource(new Path(hbase_xml));
		cfg.set("hbase.zookeeper.quorum", "10.100.83.124");// HBase 服务器地址
		cfg.set("hbase.zookeeper.property.clientPort", "2181");// 端口
		
		byte[] family="index".getBytes();
		
		HTable htable = new HTable(cfg,"DialogTargetTokenIndex_2013-06-25");
		scanTest( htable, family,null);
	//	htable.setAutoFlush(true);
	//	putTest(htable,1000);
		//System.out.println("htable.getWriteBufferSize():"+htable.getWriteBufferSize());
		//htable.setAutoFlush(false);
		
		//putTest(htable,1000);
	}

}
