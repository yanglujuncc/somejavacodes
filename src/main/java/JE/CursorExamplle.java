package JE;

import java.io.File;

import com.sleepycat.je.Cursor;
import com.sleepycat.je.Database;
import com.sleepycat.je.DatabaseEntry;
import com.sleepycat.je.DatabaseException;
import com.sleepycat.je.Environment;
import com.sleepycat.je.EnvironmentConfig;
import com.sleepycat.je.LockMode;
import com.sleepycat.je.OperationStatus;

public class CursorExamplle {

	public static void main(String[] args) {

		String env = "bdb/dbEnv";

		Environment myDbEnvironment = null;
		Database myDatabase = null;

		try {
			try {
				EnvironmentConfig envConfig = new EnvironmentConfig();
				envConfig.setAllowCreate(true);// 如果不存在则创建一个
				myDbEnvironment = new Environment(new File(env), envConfig);

				myDatabase = myDbEnvironment.openDatabase(null, "myDB", null);

			} catch (DatabaseException dbe) {
				// Exception handling goes here ...
			}

			String searchKey = "Alaska";
			String searchData = "Fa";

			Cursor cursor = null;

			try {

				cursor = myDatabase.openCursor(null, null);
				DatabaseEntry theKey = new DatabaseEntry(searchKey.getBytes("UTF-8"));
				DatabaseEntry theData = new DatabaseEntry(searchData.getBytes("UTF-8"));
				cursor = myDatabase.openCursor(null, null);
				OperationStatus retVal = cursor.getSearchBothRange(theKey, theData, LockMode.DEFAULT);
				if (retVal == OperationStatus.NOTFOUND) {
					System.out.println(searchKey + "/" + searchData + " not matched in database " + myDatabase.getDatabaseName());
				} else {
					String foundKey = new String(theKey.getData(), "UTF-8");
					String foundData = new String(theData.getData(), "UTF-8");
					System.out.println("Found record " + foundKey + "/" + foundData + "for search key/data: " + searchKey + "/" + searchData);
				}

			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if (cursor != null)
					cursor.close();
			}
		} finally {
			if (myDatabase != null)
				myDatabase.close();
			if (myDbEnvironment != null)
			{
				myDbEnvironment.cleanLog();
				myDbEnvironment.close();
			}
		}
	}
}
