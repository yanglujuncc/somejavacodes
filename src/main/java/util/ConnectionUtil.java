package util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;

import java.sql.SQLException;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSourceFactory;
import org.apache.log4j.Logger;

public class ConnectionUtil {

	protected static Logger logger = Logger.getLogger(ConnectionUtil.class.getName());

	private static DataSource ds = null;

	public static void init(String configFilePath) {
		try {

			logger.info(" load conf file :" + configFilePath);

			Properties prop = new Properties();

			try {

				File file = new File(configFilePath);

				InputStream is = new FileInputStream(file);

				prop.load(is);

			} catch (IOException e) {

				e.printStackTrace();
			}

			ds = BasicDataSourceFactory.createDataSource(prop);

		} catch (Exception e) {

			throw new RuntimeException(e);

		}
	}

	public static Connection getConnection() throws SQLException, IOException {

		return ds.getConnection();
	}
}
