package DBCompare;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class testMySQL {

	Connection conn;

	String insertSQL = "insert into testTable(column_1,column_2) values(?,?)";
	PreparedStatement insertPreparedStatement;

	String deleteSQL = "delete from testTable where column_1=?";
	PreparedStatement deletePreparedStatement;

	public void testInsert(int maxKey) {
		long timeBegin = System.currentTimeMillis();
		String data = "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa";
		for (int i = 0; i < maxKey; i++) {
			try {

				insertPreparedStatement.setString(1, i + "");
				insertPreparedStatement.setString(2, data);
				int result = insertPreparedStatement.executeUpdate();
				if (result != 1) {
					System.out.println("result:" + result + " when insert:" + i + "," + data);
					System.exit(1);
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.exit(1);
			}

		}
		long timeEnd = System.currentTimeMillis();
		long cost = timeEnd - timeBegin;
		System.out.println("Insert Cost:" + cost + "'ms,total:" + maxKey);
		double perOper = ((double) maxKey) / cost;
		System.out.println("Insert perOper:" + perOper + "/ms");
		double operPer = ((double)cost)/ maxKey;
		System.out.println("Delete operPer:" + operPer + "'ms/");
	}

	public void testRemove(int maxKey) {

		long timeBegin = System.currentTimeMillis();
		for (int i = 0; i < maxKey; i++) {
			try {

				deletePreparedStatement.setString(1, i + "");
				int result = insertPreparedStatement.executeUpdate();
				if (result != 1) {
					System.out.println("result:" + result + " when delete:" + i);
					System.exit(1);
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			
				System.exit(1);
			}

		}
		long timeEnd = System.currentTimeMillis();
		long cost = timeEnd - timeBegin;
		System.out.println("Delete Cost:" + cost + "'ms,total:" + maxKey);
		double perOper = ((double) maxKey) / cost;
		System.out.println("Delete perOper:" + perOper + "/ms");
		double operPer = ((double)cost)/ maxKey;
		System.out.println("Delete operPer:" + operPer + "'ms/");
	}

	public void initDB(String url, String user, String password)  {

		String driver = "com.mysql.jdbc.Driver"; // 驱动程序名
		try {
			Class.forName(driver);

			conn = DriverManager.getConnection(url, user, password);
			insertPreparedStatement = conn.prepareStatement(insertSQL);
			deletePreparedStatement = conn.prepareStatement(deleteSQL);

			if (!conn.isClosed()) {
				return;

			} else
				return;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.err.println("initDB error");
			System.exit(1);
		} // 加
	}

	public static void main(String argvs[]) {
		testMySQL atestMySQL = new testMySQL();
		String url = "jdbc:mysql://ir-11.space.163.org/NLP?useUnicode=true&characterEncoding=gbk";
		String user = "garbage";
		String password = "antispam";
		
		atestMySQL.initDB(url, user, password);
		atestMySQL.testInsert(1000);
		atestMySQL.testRemove(1000);
	}
}
