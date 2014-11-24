package PostgreSQL;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;

import java.sql.Statement;

public class ConnectionDemo {
	
	
	 public static void main(String[] args){
	    	
		   System.out.print( " this is a test " );
	         try 
	          {
	            Class.forName( "org.postgresql.Driver" ).newInstance();
	            String url = "jdbc:postgresql://localhost:5432/db1" ;
	            Connection con = DriverManager.getConnection(url, "postgres" , "123456" );
	            Statement st = con.createStatement();
	            String sql = " select * from public.score " ;
	            ResultSet rs = st.executeQuery(sql);
	             while (rs.next())
	             {
	                System.out.print(rs.getInt( 1 ));
	                System.out.println(rs.getString( 2 ));
	            } 
	            rs.close();
	            st.close();
	            con.close();
	            

	        } 
	         catch (Exception ee)
	         {
	        	 ee.printStackTrace();
	            System.out.print(ee.getMessage());
	        } 
	    }
}
