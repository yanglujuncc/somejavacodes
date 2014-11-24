package HBase;

import java.io.IOException;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hbase.HBaseConfiguration;  
import org.apache.hadoop.hbase.HColumnDescriptor;  
import org.apache.hadoop.hbase.HTableDescriptor;  
import org.apache.hadoop.hbase.KeyValue;  
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.HBaseAdmin;  
import org.apache.hadoop.hbase.client.HTable;  
import org.apache.hadoop.hbase.client.Result;  
import org.apache.hadoop.hbase.client.ResultScanner;  
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.client.Put;
  
public class HBaseInterface {  

  //  static String hbase_xml = "***/conf/hbase-site.xml";  //配置文件地址
    static Configuration cfg =null;  
   
    
    public static void init(String hbaseAdr,String port){
    	  
    	    	cfg = HBaseConfiguration.create();
    	    //	Configuration conf = new HBaseConfiguration(); 
    	        //conf.addResource(new Path(hbase_xml)); 
    	    	cfg.set("hbase.zookeeper.quorum", hbaseAdr);//HBase 服务器地址
    	    	cfg.set("hbase.zookeeper.property.clientPort", port);//端口
    	      

    }
      
    public static int createTable(String tableName,String[] columnFarilys) throws Exception
    {  
        HBaseAdmin admin = new HBaseAdmin(cfg);  
        if(admin.tableExists(tableName))
        {  
            System.out.println(tableName+" 存在！");  
            return 2;
            //System.exit(0);  
        }
        else
        {  
      
            HTableDescriptor  tableDesc = new HTableDescriptor(tableName);
            for(String columnFarily:columnFarilys)
            {
                tableDesc.addFamily(new HColumnDescriptor(columnFarily));
            }
            admin.createTable(tableDesc);
            System.out.println("succcess to add table" + tableName);  
            return 1;
        }  
    } 
    public static void deleteTable(String tableName) {  
        System.out.println("start create table ......");  
        try {  
            HBaseAdmin hBaseAdmin = new HBaseAdmin(cfg);  
            if (hBaseAdmin.tableExists(tableName)) {// 如果存在要创建的表，那么先删除，再创建  
                hBaseAdmin.disableTable(tableName);  
                hBaseAdmin.deleteTable(tableName);  
                System.out.println(tableName + " is exist,detele....");  
            }  
          
          
        } catch (IOException e) {  
            e.printStackTrace();  
        }        
    }  
    /**
     * pre create regions
     * @param tableName
     * @param columnFarilys
     * @param minKey
     * @param maxKey
     * @param regionNum
     * @return
     * @throws Exception
     */
    public static int createTable(String tableName,String[] columnFarilys,byte[] minKey,byte[] maxKey,int regionNum) throws Exception
    {  
    	if(regionNum<3)
    	{
    		return createTable( tableName,columnFarilys);
    		
    	}
    	
        HBaseAdmin admin = new HBaseAdmin(cfg);  
        if(admin.tableExists(tableName))
        {  
            System.out.println(tableName+" 存在！");  
            return 2;
            //System.exit(0);  
        }
        else
        {  
      
            HTableDescriptor  tableDesc = new HTableDescriptor(tableName);
            for(String columnFarily:columnFarilys)
            {
                tableDesc.addFamily(new HColumnDescriptor(columnFarily));
            }
           // admin.createTable(tableDesc);
            admin.createTable(tableDesc, minKey, maxKey, regionNum);
            System.out.println("succcess to add table" + tableName);  
            return 1;
        }  
    }  
    
    public static HTable getTable(String tableName) throws Exception{
    	
    	return  new HTable(cfg,tableName);
    }
    public static void putData(HTable table,byte[] rowKey,byte[] columnFamily,byte[] column,byte[] data) throws Exception
    {  
  
        
        Put putrow = new Put(rowKey); 
        putrow.add(columnFamily, column,data);  
        
        //一个put
        table.put(putrow);  

    }    
    public static void addData(String tableName,String row,String columnFamily,String column,String data) throws Exception
    {  
        HTable table = new HTable(cfg,tableName);
        
        Put putrow = new Put(row.getBytes()); 
        putrow.add(columnFamily.getBytes(), column.getBytes(), data.getBytes());  
        
        //一个put
        table.put(putrow);  
        
        //同时多个put
        List<Put> putList=new LinkedList<Put>();
        putList.add(putrow);
        table.put(putList);
        
        
        System.out.println("success to add data for table " + tableName + "columnFamily " + columnFamily);
    }  
    public static void addData(String tableName,String row,String columnFamily,String column,long version,String data) throws Exception
    {  
        HTable table = new HTable(cfg,tableName);
        
        Put putrow = new Put(row.getBytes()); 
        putrow.add(columnFamily.getBytes(), column.getBytes(), version,data.getBytes());  
        table.put(putrow);  
        
        System.out.println("success to add data for table " + tableName + "columnFamily " + columnFamily);
    }   
    
    
    public static void getData(String tableName,String row,String columnFamily,String column) throws Exception
    { 
    	  HTable htable = new HTable(cfg,tableName);
          
    	  Get get = new Get(row.getBytes());
         
          Result r = htable.get(get);
          System.out.println("r:"+r);
          byte[] b = r.getValue(columnFamily.getBytes(), column.getBytes());  // returns current version of value      ue          
          System.out.println("b:"+b);
          System.out.println("get result "+new String(b)+",  of table " + tableName + "columnFamily " + columnFamily+" column "+column);
         
    }
    public static void getData(String tableName,String row,String columnFamily,String column,int versionMax) throws Exception
    { 
    	  HTable htable = new HTable(cfg,tableName);
          
    	  Get get = new Get(row.getBytes());
    	  
    	  get.setMaxVersions(versionMax);  // will return last 3 versions of row        
    	  Result r = htable.get(get);
    	   System.out.println("r:"+r);
          List<KeyValue> kvList = r.getColumn(columnFamily.getBytes(),column.getBytes());  // returns all versions of this column      
         System.out.println("kvList:"+kvList.size());
          for(KeyValue kv:kvList){
        	 
        	  //Family
        	 // Qualifier 
        	  System.out.println(new String(kv.getRow())+"\t"+ new String(kv.getFamily())+"\t"+ new String(kv.getQualifier())+"\t"+new String(kv.getValue())+"\t"+ new Date(kv.getTimestamp()).toGMTString());  
         }
    }
    public static void getData(String tableName,String row,String columnFamily,String column,long timeMin,long timeMax) throws Exception
    { 
    	  HTable htable = new HTable(cfg,tableName);
          
    	  Get get = new Get(row.getBytes());
    	  get.setTimeRange(timeMin, timeMax);
    
    	  Result r = htable.get(get);
          
          List<KeyValue> kvList = r.getColumn(columnFamily.getBytes(),column.getBytes());  // returns all versions of this column      
          for(KeyValue kv:kvList){
        	 
        	  //Family
        	 // Qualifier 
        	  System.out.println(new String(kv.getRow())+"\t"+ new String(kv.getFamily())+"\t"+ new String(kv.getQualifier())+"\t"+new String(kv.getValue())+"\t"+ new Date(kv.getTimestamp()).toGMTString());  
         }
    }
    
    public static void getAllData(String tableName) throws Exception
    {  
        HTable table = new HTable(cfg,tableName);  
        Scan scan = new Scan();  
        ResultScanner rs = table.getScanner(scan);  
        for(Result r:rs)
        {  
        	System.out.println(new String(r.getRow()));
            for(KeyValue kv:r.raw())
            {  
            
            	  System.out.println(new String(kv.getRow())+"\t"+ new String(kv.getFamily())+"\t"+ new String(kv.getQualifier())+"\t"+new String(kv.getValue())+"\t"+ new Date(kv.getTimestamp()).toGMTString());  
            	   
            }  
        } 
        rs.close();
        
    }  
    
    //性能优化
    public static void addDataPerformance(String tableName,String row,String columnFamily,String column,String data) throws Exception
    {  
        HTable table = new HTable(cfg,tableName);
        table.setWriteBufferSize(5 * 1024 * 1024); //5MB
        table.setAutoFlush(false);
        
        table.flushCommits();
        
        Put putrow = new Put(row.getBytes()); 
        putrow.add(columnFamily.getBytes(), column.getBytes(), data.getBytes());  
        table.put(putrow);  
        System.out.println("success to add data for table " + tableName + "columnFamily " + columnFamily);
    }  
    public static void main(String[] args)
    {  
    	
    	HBaseInterface.init("10.100.83.124","2181");

    	HBaseInterface.deleteTable("Dialog_2013-08-08");
    	HBaseInterface.deleteTable("Dialog_2013-08-09");
    	HBaseInterface.deleteTable("Dialog_2013-08-10");
    	HBaseInterface.deleteTable("Dialog_2013-08-11");
    	HBaseInterface.deleteTable("Dialog_2013-08-12");
    	HBaseInterface.deleteTable("Dialog_2013-08-13");
    	HBaseInterface.deleteTable("Dialog_2013-08-14");
    	HBaseInterface.deleteTable("Dialog_2013-08-15");
    	HBaseInterface.deleteTable("Dialog_2013-08-19");
        try
        {  
        	/*
           String tableName = "people";
           String[] familynames = {"place","school"};
           HBaseInterface.createTable(tableName, familynames);  
           HBaseInterface.addData(tableName, "xiaowang", "place", "province", "GuangZhou");  
           System.out.println("********************************************************");
           HBaseInterface.getAllData(tableName);
           System.out.println("********************************************************");
           HBaseInterface.getData(tableName, "1", "place", "province");
           System.out.println("********************************************************");
           HBaseInterface.getData(tableName, "1", "place", "province",10);
           System.out.println("********************************************************");
          //  TestHBase.addData(tableName, "2", "place", "province", "Beijing");
         //   TestHBase.getAllData(tableName);
         //   TestHBase.addData(tableName, "1", "school", "high", "MIT");
         //   TestHBase.getAllData(tableName);
           */
        
//
        }
        catch(Exception e)
        {  
            e.printStackTrace();  
        }  
    }  
}  

