package util;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.util.Arrays;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.KeyValue;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;

public class HBaseUtil {

	// static String column="value";

	static public void putData(HTable htable, String rawKey, String columnFamily, String qualifier, String value) throws Exception {

		long timeBegin = System.currentTimeMillis();

		Put putrow = new Put(rawKey.getBytes());
		putrow.add(columnFamily.getBytes(), qualifier.getBytes(), value.getBytes());

		htable.put(putrow);

		if (!htable.isAutoFlush())
			htable.flushCommits();

		long timeCost = System.currentTimeMillis() - timeBegin;

	//	System.out.println("Put:" + 1 + " Cost:" + (timeCost / 1000) + "'s");

	}
	static public byte[] getData(HTable htable, byte[] rawKey, byte[] columnFamily, byte[] qualifier) throws IOException{

	long timeBegin = System.currentTimeMillis();

    
	  Get get = new Get(rawKey);
   
    Result r = htable.get(get);
  //  System.out.println("r:"+r);
    byte[] b = r.getValue(columnFamily, qualifier);  // returns current version of value      ue     
    
    long timeCost = System.currentTimeMillis() - timeBegin;
	//System.out.println("Get:" + 1 + " Cost:" + (timeCost / 1000) + "'s");
    return b;
  
}
	static public void putData(HTable htable, byte[] rawKey, byte[] columnFamily, byte[] qualifier, byte[] value) throws Exception {

		long timeBegin = System.currentTimeMillis();

		Put putrow = new Put(rawKey);
		putrow.add(columnFamily, qualifier, value);

		htable.put(putrow);

		if (!htable.isAutoFlush())
			htable.flushCommits();

		long timeCost = System.currentTimeMillis() - timeBegin;

		//System.out.println("Put:" + 1 + " Cost:" + (timeCost / 1000) + "'s");

	}



	

	
	
	public static void scanDeviceid(HTable htable,byte[] family,byte[] startRowkey,byte[] stopRowkey) throws IOException{
		
		   Scan s = new Scan();  
		   s.addFamily(family);
		 //  s.addColumn(family, column);
		   s.setMaxVersions();
		   s.setBatch(1000); //一次返回几条
		   s.setStartRow(startRowkey);
		   s.setStopRow(stopRowkey);
		   
		   ResultScanner rs = htable.getScanner(s);  
		   for (Result r : rs) {  
			   System.out.println("rowkey :   "+ new String(r.getRow(),"utf-8") + "  ");  
		     for (KeyValue kv : r.raw()) {  
		    
		     
		       System.out.println("          "+new String(kv.getFamily(),"utf-8") + ":"  
		            
		    		   + bytesToLong(kv.getQualifier()) 
		           + " => "+new String(kv.getValue(),"utf-8") + "  timestamp:" + kv.getTimestamp());  
		       
		       ;
		     }  
		   }  
		
	}
	 public static void scanTest(HTable htable,byte[] family) throws Exception {

		   Scan s = new Scan();  
		   s.addFamily(family);
		 //  s.addColumn(family, column);
		   s.setMaxVersions();
		   s.setBatch(1000); //一次返回几条
		   
		   
		   ResultScanner rs = htable.getScanner(s);  
		   for (Result r : rs) {  
			   System.out.println("rowkey :   "+ new String(r.getRow(),"utf-8") + "  ");  
		     for (KeyValue kv : r.raw()) {  
		    
		     
		       System.out.println("          "+new String(kv.getFamily(),"utf-8") + ":"  
		            
		    		   + bytesToLong(kv.getQualifier()) 
		           + " => "+new String(kv.getValue(),"utf-8") + "  timestamp:" + kv.getTimestamp());  
		       
		       ;
		     }  
		   }  
		
	}
	// byte 数组与 long 的相互转换
			public static byte[] longToBytes(long x) {
				ByteBuffer buffer = ByteBuffer.allocate(8);
				buffer.putLong(0, x);
				return buffer.array();
			}

			public static long bytesToLong(byte[] bytes) {
				ByteBuffer buffer = ByteBuffer.allocate(8);
				buffer.put(bytes, 0, bytes.length);
				buffer.flip();// need flip
				return buffer.getLong();
			}
			 public static byte[] byteMerger(byte[] byte_1, byte[] byte_2){  
			        byte[] byte_3 = new byte[byte_1.length+byte_2.length];  
			        System.arraycopy(byte_1, 0, byte_3, 0, byte_1.length);  
			        System.arraycopy(byte_2, 0, byte_3, byte_1.length, byte_2.length);  
			        return byte_3;  
			    }  
	public static void main(String[] args) throws Exception {

		 String tableName = "news_mobile_device_action";
		 String columnFamily = "click";

		
		Configuration cfg = HBaseConfiguration.create();
		// Configuration conf = new HBaseConfiguration();
		// conf.addResource(new Path(hbase_xml));
	//	cfg.set("hbase.zookeeper.quorum", "app-68.photo.163.org");// HBase 服务器地址
	//	cfg.set("hbase.zookeeper.property.clientPort", "2182");// 端口
		//app-68.photo.163.org 2182
		//inspur251.photo.163.org 2181
		cfg.set("hbase.zookeeper.quorum", "inspur251.photo.163.org,inspur252.photo.163.org,inspur253.photo.163.org,inspur254.photo.163.org,inspur255.photo.163.org");// HBase 服务器地址
		cfg.set("hbase.zookeeper.property.clientPort", "2181");// 端口
		
		

		byte[] family = "index".getBytes();

		HTable htable = new HTable(cfg, "news_mobile_device_action");
	//	+/EoxDWNwsI8x6IGfCKzW1fBA5G7Zkmq0jBuVM3wl4Q0ZOz80JIZJCM4qW+Te8Mv        1398588684000|9QR5TEKV00031GVS 
		
		String rawKey="+/EoxDWNwsI8x6IGfCKzW1fBA5G7Zkmq0jBuVM3wl4Q0ZOz80JIZJCM4qW+Te8Mv";
		byte[] rawKeyByte=rawKey.getBytes("utf-8");
		String dateStr1="2014-04-28";
		String dateStr2="2010-04-28";
		
		byte[] dateStr1Byte=dateStr1.getBytes("utf-8");
		byte[] dateStr2Byte=dateStr2.getBytes("utf-8");
		
		byte[] merged=byteMerger(rawKeyByte, dateStr1Byte);
		
		System.out.println(rawKeyByte.length);
		System.out.println(dateStr1Byte.length);
		System.out.println(dateStr2Byte.length);
		
		System.out.println(merged.length);
		
		System.out.println(Arrays.toString(rawKeyByte));
		System.out.println(Arrays.toString(dateStr1Byte));
		System.out.println(Arrays.toString(dateStr2Byte));
		System.out.println(Arrays.toString(byteMerger(rawKeyByte, dateStr1Byte)));
		System.out.println(Arrays.toString(byteMerger(rawKeyByte, dateStr2Byte)));
		
		byte[] rawkeyByte=rawKey.getBytes("utf-8");
		byte[] columnFamilyByte=columnFamily.getBytes("utf-8");
		long time=1398588684000L;
		byte[] timeByte=longToBytes(time);
		String docid="9QR5TEKV00031GVS";
		byte[] docidByte=docid.getBytes("utf-8");
		
		
	
		
//		scanTest( htable,columnFamilyByte);
		//
		String user2="kCrbd3K80YfMFLzsuZYgy+r8qh/tCIUaJnpCMw+Y6z37zdnaarO1InvhMizHaUWj";
		String use="sCgcfdePPm5kzwlrOYqj0g==";
		byte[] startRowkeyByte="sCgcfdePPm5kzwlrOYqj0g==".getBytes("utf-8");
		byte[] stopRowkeyByte="sCgcfdePPm5kzwlrOYqj0g==_2014-05-30".getBytes("utf-8");
		
		scanDeviceid( htable, columnFamilyByte,startRowkeyByte,stopRowkeyByte);
		
		htable.close();
		//htable.
	//	putData( htable,  rawkeyByte, columnFamilyByte, timeByte, docidByte);
		
		
	//	byte[] value=getData( htable,  rawkeyByte, columnFamilyByte, timeByte);
		
		
	//	System.out.println(new String(value,"utf-8"));
		// htable.setAutoFlush(true);
		// putTest(htable,1000);
		// System.out.println("htable.getWriteBufferSize():"+htable.getWriteBufferSize());
		// htable.setAutoFlush(false);

		// putTest(htable,1000);
	}
}
