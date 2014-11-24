package MySQL;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;

import java.sql.SQLException;





public class testMysqlConnection {
	public static Connection conn;
	
	
	public static void creatTableExample(){
		try {
			Statement statement = (Statement) conn.createStatement();
			 String creat_table_sql = "create table newTable(id int,name varchar(200))";    
			
			 int result = statement.executeUpdate(creat_table_sql);
			 System.out.println("result="+result);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}               // statement用来执行SQL语句
	}
	//使用文本串 
	public static void statementInsertExample1(){
		
		  try { 
		 	Statement statement = (Statement) conn.createStatement();               // statement用来执行SQL语句
	       
	        String sql = "select * from atable";                  // 要执行的SQL语句
	       
	        ResultSet rs = statement.executeQuery(sql);       // 结果集
	      
	        System.out.println("-----------------------------------------");
	        System.out.println("执行结果如下所示:");
	        System.out.println("-----------------------------------------");
	        System.out.println(" id" + "\t" + "name" + "\t" + "newField");
	        System.out.println("-----------------------------------------");
	      
	        String name = null;
	        while(rs.next()) {    
	         System.out.println(rs.getInt("id") + "\t" + rs.getString("name") + "\t" + rs.getString("newField"));        // 输出结果
	        }
	        rs.close();
	        
	        String insert_sql = "insert into atable (id,name,newField) values (3,\"thrid\",\"nothing\" )";      
	        int rs2= statement.executeUpdate(insert_sql);
	        System.out.println("affected rows  "+rs2);
	        
		  }  catch(Exception e) {

		        e.printStackTrace();

		       } 
	}
	public static void preparedStatementInsertExample2(){
		  try { 
			  String sql_statement="insert into atable (id,name,newField) values (?,?,?)";
			  PreparedStatement preparedStatement =(PreparedStatement) conn.prepareStatement(sql_statement);
			  preparedStatement.setString(2, "namevalue:"+0);
			  for(int i=20;i<25;i++)
			  {
				  System.out.println("****************************");
				  preparedStatement.setInt(1, i);
				//  preparedStatement.setString(2, "namevalue:"+i);
				  preparedStatement.setString(3, "newFieldValue:"+i);
				 int result= preparedStatement.executeUpdate();
				 System.out.println("result="+result);
			  } 
		  }
		  catch(Exception e) {

		        e.printStackTrace();

		       } 
	}
	
	
	public static void connect(){
		  String driver = "com.mysql.jdbc.Driver";         // 驱动程序名
	       String url = "jdbc:mysql://localhost/qn_test";     // URL指向要访问的数据库名scutcs          
	   
	       String user = "root";       // MySQL配置时的用户名
	       String password = "123456";// MySQL配置时的密码
	       
	       try { 
	        
	        Class.forName(driver);    // 加载驱动程序
	       
	        conn =  DriverManager.getConnection(url, user, password);      // 连续数据库
	        if(!conn.isClosed()) 
	        {
	          System.out.println("Succeeded connecting to the Database!");     //验证是否连接成功
	          System.out.println("Succeeded connecting to the Database!");     //验证是否连接成功
	        }
	         else
	        	return;
	       }  catch(Exception e) {

	        e.printStackTrace();

	       } 	
	}
	public static void getMetaData(){
		 try { 
		
				Statement statement = (Statement) conn.createStatement();               // statement用来执行SQL语句
			       
		        String sql = "select * from classesmining";                  // 要执行的SQL语句
		       
		        ResultSet rs = statement.executeQuery(sql);       // 结果集
		        ResultSetMetaData metaData= rs.getMetaData();
		        int columnCount = metaData.getColumnCount();
		        for(int i=1;i<=columnCount;i++)
		        {
		        	System.out.println("column "+i+" "+metaData.getColumnName(i)+" Type:"+metaData.getColumnTypeName(i));
		        }
		  }
		  catch(Exception e) {

		        e.printStackTrace();

		       } 
	}
	public static void closeConnect(){
		 try {
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static void main(String[] args){
		
		connect();
		//creatTableExample();
		getMetaData();
		closeConnect();
	}
}
