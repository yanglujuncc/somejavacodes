package JE;

import java.io.File;

import com.sleepycat.je.Cursor;
import com.sleepycat.je.Database;
import com.sleepycat.je.DatabaseConfig;
import com.sleepycat.je.DatabaseEntry;
import com.sleepycat.je.DatabaseException;
import com.sleepycat.je.Environment;
import com.sleepycat.je.EnvironmentConfig;
import com.sleepycat.je.EnvironmentMutableConfig;
import com.sleepycat.je.LockMode;
import com.sleepycat.je.OperationStatus;

public class OpenClose {
	public static void main(String[] args) {

		String env = "bdb/dbEnv";

		Environment myEnvironment = null;
		EnvironmentConfig envConfig = new EnvironmentConfig();
		envConfig.setAllowCreate(true);// 如果不存在则创建一个
		
		try {

			myEnvironment = new Environment(new File(env), envConfig);
			   
			EnvironmentMutableConfig envMutableConfig = new EnvironmentMutableConfig();      
            envMutableConfig.setTxnNoSyncVoid(true);         
            myEnvironment .setMutableConfig(envMutableConfig);
		
            
            Database myDatabase = null;
            DatabaseConfig dbConfig = new DatabaseConfig();
            dbConfig.setAllowCreate(true);
            
            dbConfig.set
			try {
				myDatabase = myEnvironment.openDatabase(null, "myDB", dbConfig);


				

				//do something
				
				
				
			} finally {

				if (myDatabase != null)
					myDatabase.close();
			}

		} finally {

			if (myEnvironment != null) {
				myEnvironment.cleanLog();
				myEnvironment.close();
			}
		}
	}
}
