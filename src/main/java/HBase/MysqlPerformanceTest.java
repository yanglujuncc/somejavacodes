package HBase;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Put;

public class MysqlPerformanceTest {

	static String tableName = "test";
	
	static String keyField = "myKey";
	static String valueField = "myValue";

	static	public void insertTest(Connection conn,long recordNum) throws Exception {
		
		String insertQuery="insert into "+tableName+" ("+keyField+","+valueField+")"+" values (?,?)";
		PreparedStatement  preparedStatement=conn.prepareStatement(insertQuery);
		long timeBegin=System.currentTimeMillis();
		
		for(int i=0;i<recordNum;i++){
			
			String key="key_"+i;
			String data="value_"+i;
			preparedStatement.setString(1, key);
			preparedStatement.setString(2, data);
			preparedStatement.executeUpdate();
			
		}
		long timeCost=System.currentTimeMillis()-timeBegin;
		long putPerSec=recordNum*1000/timeCost;
		
		System.out.println("Insert:"+recordNum+" Cost:"+(timeCost/1000)+"'s"+"      "+putPerSec+"/s");
		
		
	}

	public static void main(String[] args) throws Exception {

	
		String url="jdbc:mysql://ir-11.space.163.org/1492gasp?useUnicode=true&characterEncoding=utf-8";
		String user="garbage";
		String password="antispam";
		long recordNum=10000;
		Connection conn=connectDB( url,  user,  password);
		insertTest( conn, recordNum) ;
		
	}

	public static Connection connectDB(String url, String user, String password) throws SQLException {

		String driver = "com.mysql.jdbc.Driver"; // Çý¶¯³ÌÐòÃû
		try {
			Class.forName(driver);
		} catch (ClassNotFoundException e) {
			
			e.printStackTrace();
			return null;
		} 

		Connection conn = DriverManager.getConnection(url, user, password);

		if (!conn.isClosed()) {
			return conn;
		} 
		
		return null;

	}

}
