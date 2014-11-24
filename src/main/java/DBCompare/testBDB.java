package DBCompare;

import java.io.File;

import com.sleepycat.bind.tuple.IntegerBinding;
import com.sleepycat.bind.tuple.StringBinding;
import com.sleepycat.je.Database;
import com.sleepycat.je.DatabaseConfig;
import com.sleepycat.je.DatabaseEntry;
import com.sleepycat.je.Environment;
import com.sleepycat.je.EnvironmentConfig;
import com.sleepycat.je.OperationStatus;
import com.sleepycat.je.Transaction;

public class testBDB {
	Environment ev;
	Database db;
	public void testInsert(int maxKey){
		long timeBegin=System.currentTimeMillis();
		String data="aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa";
		for(int i=0;i<maxKey;i++){
			
			DatabaseEntry keyEntry = new DatabaseEntry();
			DatabaseEntry dataEntry = new DatabaseEntry();
			
			StringBinding.stringToEntry(i+"", keyEntry);
			StringBinding.stringToEntry(data, dataEntry);
			
			OperationStatus status = db.put(null, keyEntry, dataEntry);
			
			if (status != OperationStatus.SUCCESS) {
				System.err.println("Insert status:" + status);
				System.exit(1);
			}
			
		}
		long timeEnd=System.currentTimeMillis();
		long cost=timeEnd-timeBegin;
		System.out.println("Insert Cost:" + cost+"'ms,total:"+maxKey);
		double perOper=((double)maxKey)/cost;
		System.out.println("Insert perOper:" + perOper+"/ms");
		double operPer = ((double)cost)/ maxKey;
		System.out.println("Delete operPer:" + operPer + "'ms/");
	}
	public void testRemove(int maxKey){
		long timeBegin=System.currentTimeMillis();
		for(int i=0;i<maxKey;i++){
			
			DatabaseEntry keyEntry = new DatabaseEntry();
			StringBinding.stringToEntry(i+"", keyEntry);
		
			OperationStatus status = db.delete(null,keyEntry);
			if(status!=OperationStatus.SUCCESS)
			{
				System.err.println("Del status:" + status);
				System.exit(1);
			}
			
		}
		long timeEnd=System.currentTimeMillis();
		long cost=timeEnd-timeBegin;
		System.out.println("Remove Cost:" + cost+"'ms,total:"+maxKey);
		double perOper=((double)maxKey)/cost;
		System.out.println("Remove perOper:" + perOper+"/ms");
		double operPer = ((double)cost)/ maxKey;
		System.out.println("Delete operPer:" + operPer + "'ms/");
	}
	public void initDB(String envDirPath,String dbName){
		
	    EnvironmentConfig envConfig = new EnvironmentConfig();
        envConfig.setTransactional(true);
        envConfig.setAllowCreate(true);
  
        File envDir=new File(envDirPath);
        ev = new Environment(envDir, envConfig);

        /*
         * Make a database within that environment
         *
         * Notice that we use an explicit transaction to
         * perform this database open, and that we
         * immediately commit the transaction once the
         * database is opened. This is required if we
         * want transactional support for the database.
         * However, we could have used autocommit to
         * perform the same thing by simply passing a
         * null txn handle to openDatabase().
         */
       
        Transaction txn = ev.beginTransaction(null, null);
        DatabaseConfig dbConfig = new DatabaseConfig();
        dbConfig.setTransactional(true);
        dbConfig.setAllowCreate(true);
        dbConfig.setSortedDuplicates(true);
        db = ev.openDatabase(txn,dbName,dbConfig);
        txn.commit();
	}
	public void close(){
		db.close();
		ev.close();
	}
	public static void main(String argvs[]) {
		testBDB atestBDB=new testBDB();
		atestBDB.initDB("TestBDBEnv", "testBDB");
		atestBDB.testInsert(1000);
		atestBDB.testRemove(1000);
		atestBDB.close();
	}
	
}
