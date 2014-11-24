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

public class TestDBCP {

	 private static DataSource ds=null;
	 static{

	        try{

	            Properties prop =new Properties();
	              
	            try {
	              
	                                
	                File file = new File("conf/dbcp.properties");
	                
	                InputStream is = new FileInputStream(file);
	                    
	                prop.load(is);
	       
	                
	            } catch (IOException e) {
	                
	                e.printStackTrace();
	            }
    
	            ds = BasicDataSourceFactory.createDataSource(prop);

	        }catch (Exception e) {

	            throw new RuntimeException(e);

	        }

	    }

	    public static Connection getConnection() throws SQLException{

	        return ds.getConnection();
	        
	    }  
	    
	    public static void main(String[] args){
	    	
	    	try {
	    		String sql_query="select count(*) from ActivityDescription";
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
