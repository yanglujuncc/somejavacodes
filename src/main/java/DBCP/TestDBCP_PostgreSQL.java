package DBCP;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSourceFactory;

public class TestDBCP_PostgreSQL {

	 private static DataSource ds=null;
	    //既然是工具类，那就是拿来就能用的，不用new它

	    //这个静态代码块确保其内容只运行一次，这里在第一次调用的时候，获取一个工厂

	 static{

	        try{

	            //读取资源文件
	            Properties prop =new Properties();
	            
	   
	            try {
	              
	                                
	                File file = new File("conf/dbcp.postgresql.properties");
	                
	                InputStream is = new FileInputStream(file);
	                    
	                prop.load(is);
	       
	                
	            } catch (IOException e) {
	                
	                e.printStackTrace();
	            }
	//DBCP的连接池工厂

	            //通过这个工厂，我们获得一个根据资源文件配置的数据库连接池
	            
	            ds = BasicDataSourceFactory.createDataSource(prop);

	        }catch (Exception e) {

	            throw new RuntimeException(e);

	        }

	    }
	  //返回一个数据库连接

	    public static Connection getConnection() throws SQLException{

	        //从DataSource中获取一个空闲得连接并返回给调用它的方法
	        return ds.getConnection();
	        
	    }  
	    
	    public static void main(String[] args){
	    	
	    	try {
	    		String sql_query="select * from public.score";
	    		System.out.println("sql_query:"+sql_query);
	    		
				Connection connection=getConnection();
				if(connection==null){
					System.out.println("connection==null");
				}else{
					System.out.println("connection!=null");
				}
					
				
				Statement statement=connection.createStatement();
				ResultSet rs= statement.executeQuery(sql_query);
			
				while(rs.next()){
					System.out.println("rs.int="+rs.getInt(1));
				}
				
				
				rs.close();
				statement.close();
				connection.close();
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    }
}
