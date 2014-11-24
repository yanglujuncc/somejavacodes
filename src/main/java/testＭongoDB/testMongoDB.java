package testMongoDB;
import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.Mongo;
import com.mongodb.WriteResult;

public class testMongoDB {

	public static void insert() throws Exception {

		Mongo mongo = new Mongo("localhost", 27017);
		// ������Ϊyourdb����ݿ⣬������ݿⲻ���ڵĻ���mongodb���Զ�����
		DB db = mongo.getDB("yourdb");
		// ��Mongodb�л����ΪyourColleection����ݼ��ϣ�������ݼ��ϲ����ڣ�Mongodb��Ϊ���½���
		DBCollection yourCollection = db.getCollection("yourCollection");

		byte[] datas = new byte[4];
		datas[0] = 1;
		datas[1] = 1;
		datas[2] = 1;
		datas[3] = 1;

		
		// ���½�����document���浽collection��ȥ
		for(int i=1;i<2;i++){
			BasicDBObject document = new BasicDBObject();
			document.put("msg", "hello");
			document.put("data", datas);
			document.put("id", 10000);
		
			System.out.println("insert document:" + document);
			WriteResult writeResult = yourCollection.insert(document);
			System.out.println("writeResult:" + writeResult.getN()+", Error:"+ writeResult.getError()+" ");
		}
		
		mongo.close();
	

	}

	public static void find() throws Exception {

		Mongo mongo = new Mongo("localhost", 27017);
		// ������Ϊyourdb����ݿ⣬������ݿⲻ���ڵĻ���mongodb���Զ�����
		DB db = mongo.getDB("yourdb");
		// ��Mongodb�л����ΪyourColleection����ݼ��ϣ�������ݼ��ϲ����ڣ�Mongodb��Ϊ���½���
		DBCollection yourCollection = db.getCollection("yourCollection");
		BasicDBObject searchQuery = new BasicDBObject();

		BasicDBObject inDBObject = new BasicDBObject();
		BasicDBList values = new BasicDBList();
		values.add(1001);
		values.add(1005);
		inDBObject.put("$in", values);
		searchQuery.put("id", inDBObject);
		// searchQuery.put("id", null);
		System.out.println(searchQuery);
		// ʹ��collection��find��������document
		DBCursor cursor = yourCollection.find(searchQuery);
		printDBCursor(cursor);

	}

	public static void printDBCursor(DBCursor cursor) {
		// ѭ��������
		while (cursor.hasNext()) {

			DBObject dbObject = cursor.next();

			System.out.println(dbObject);
		}
	}

	public static void findCondition() throws Exception {

		Mongo mongo = new Mongo("localhost", 27017);
		// ������Ϊyourdb����ݿ⣬������ݿⲻ���ڵĻ���mongodb���Զ�����
		DB db = mongo.getDB("yourdb");
		// ��Mongodb�л����ΪyourColleection����ݼ��ϣ�������ݼ��ϲ����ڣ�Mongodb��Ϊ���½���
		DBCollection yourCollection = db.getCollection("yourCollection");

		BasicDBObject searchQuery = new BasicDBObject();
		BasicDBObject condtionDBObject = null;

		// <1> > >= < <= != =
		// <1> "$gt", "$gte", "$lt", "$lte", "$ne", "û������ؼ���"
		System.out.println("---------------------------");

		{
			/**** "$gt" ****/
			condtionDBObject = new BasicDBObject();
			condtionDBObject.put("$gt", 1001);
			searchQuery.put("id", condtionDBObject);

			System.out.println("searchQuery:" + searchQuery);
			DBCursor cursor = yourCollection.find(searchQuery);
			printDBCursor(cursor);
		}

		System.out.println("---------------------------");
		searchQuery.clear();
		{
			/**** "$gte" ****/
			condtionDBObject = new BasicDBObject();
			condtionDBObject.put("$gte", 1001);
			searchQuery.put("id", condtionDBObject);

			System.out.println("searchQuery:" + searchQuery);
			DBCursor cursor = yourCollection.find(searchQuery);
			printDBCursor(cursor);
		}

		System.out.println("---------------------------");
		searchQuery.clear();
		{
			/**** "$lt" ****/
			condtionDBObject = new BasicDBObject();
			condtionDBObject.put("$lt", 1001);
			searchQuery.put("id", condtionDBObject);

			System.out.println("searchQuery:" + searchQuery);
			DBCursor cursor = yourCollection.find(searchQuery);
			printDBCursor(cursor);
		}

		System.out.println("---------------------------");
		searchQuery.clear();
		{
			/**** "$gte" ****/
			condtionDBObject = new BasicDBObject();
			condtionDBObject.put("$lte", 1001);
			searchQuery.put("id", condtionDBObject);

			System.out.println("searchQuery:" + searchQuery);
			DBCursor cursor = yourCollection.find(searchQuery);
			printDBCursor(cursor);
		}

		System.out.println("---------------------------");
		searchQuery.clear();
		{
			/**** "$ne" ****/
			condtionDBObject = new BasicDBObject();
			condtionDBObject.put("$ne", 1001);
			searchQuery.put("id", condtionDBObject);

			System.out.println("searchQuery:" + searchQuery);
			DBCursor cursor = yourCollection.find(searchQuery);
			printDBCursor(cursor);
		}

		System.out.println("---------------------------");
		searchQuery.clear();
		{
			/**** "" ****/

			searchQuery.put("id", 1001);

			System.out.println("searchQuery:" + searchQuery);
			DBCursor cursor = yourCollection.find(searchQuery);
			printDBCursor(cursor);
		}
		mongo.close();
	}

	public static void findCondition2() throws Exception {

		Mongo mongo = new Mongo("localhost", 27017);
		// ������Ϊyourdb����ݿ⣬������ݿⲻ���ڵĻ���mongodb���Զ�����
		DB db = mongo.getDB("yourdb");
		// ��Mongodb�л����ΪyourColleection����ݼ��ϣ�������ݼ��ϲ����ڣ�Mongodb��Ϊ���½���
		DBCollection yourCollection = db.getCollection("yourCollection");

		BasicDBObject searchQuery = new BasicDBObject();

		// <2> And OR In NotIn
		// <2> "�޹ؼ��֡�, "$or", "$in"��"$nin"

		System.out.println("---------------------------");

		{
			/**** "And" ****/
			BasicDBObject condtionDBObject = new BasicDBObject();

			condtionDBObject.put("id", 1001);
			condtionDBObject.put("msg", "hello world mongoDB in Java");

			searchQuery = condtionDBObject;
			System.out.println("searchQuery:" + searchQuery);
			DBCursor cursor = yourCollection.find(searchQuery);
			printDBCursor(cursor);
		}

		System.out.println("---------------------------");
		searchQuery.clear();
		{
			/**** $or ****/
			BasicDBList conditionList = new BasicDBList();

			BasicDBObject condtionDBObject = new BasicDBObject();
			condtionDBObject.put("id", 1001);
			BasicDBObject condtion2DBObject = new BasicDBObject();
			condtion2DBObject.put("id", 1005);

			conditionList.add(condtionDBObject);
			conditionList.add(condtion2DBObject);

			searchQuery.put("$or", conditionList);

			System.out.println("searchQuery:" + searchQuery);
			DBCursor cursor = yourCollection.find(searchQuery);
			printDBCursor(cursor);
		}

		System.out.println("---------------------------");
		searchQuery.clear();
		{
			/**** $in ****/

			BasicDBObject inDBObject = new BasicDBObject();
			BasicDBList values = new BasicDBList();
			values.add(1001);
			values.add(1005);
			inDBObject.put("$in", values);
			searchQuery.put("id", inDBObject);

			System.out.println("searchQuery:" + searchQuery);
			DBCursor cursor = yourCollection.find(searchQuery);

			printDBCursor(cursor);
		}

		System.out.println("---------------------------");
		searchQuery.clear();

		{
			/**** $nin ****/
			BasicDBObject inDBObject = new BasicDBObject();
			BasicDBList values = new BasicDBList();
			values.add(1001);
			// values.add(1005);
			inDBObject.put("$nin", values);
			searchQuery.put("id", inDBObject);

			System.out.println("searchQuery:" + searchQuery);
			DBCursor cursor = yourCollection.find(searchQuery);
			printDBCursor(cursor);
		}

		mongo.close();

	}

	public static void findAll() throws Exception {

		Mongo mongo = new Mongo("localhost", 27017);
		// ������Ϊyourdb����ݿ⣬������ݿⲻ���ڵĻ���mongodb���Զ�����
		DB db = mongo.getDB("yourdb");
		// ��Mongodb�л����ΪyourColleection����ݼ��ϣ�������ݼ��ϲ����ڣ�Mongodb��Ϊ���½���
		DBCollection yourCollection = db.getCollection("yourCollection");

		DBCursor cursor = yourCollection.find();
		// ѭ��������
		while (cursor.hasNext()) {
			DBObject dbObject = cursor.next();
			System.out.println(dbObject);
		}

		mongo.close();
	}

	public static void update() throws Exception {
		// update�����ĵ�һ������Ϊ�����ҵ����������ڶ�������Ϊ�����µ�ֵ
		Mongo mongo = new Mongo("localhost", 27017);
		// ������Ϊyourdb����ݿ⣬������ݿⲻ���ڵĻ���mongodb���Զ�����
		DB db = mongo.getDB("yourdb");
		// ��Mongodb�л����ΪyourColleection����ݼ��ϣ�������ݼ��ϲ����ڣ�Mongodb��Ϊ���½���
		DBCollection yourCollection = db.getCollection("yourCollection");
		
		/*
		{	// �������
			
			BasicDBObject condtionDBObject = new BasicDBObject();
			condtionDBObject.put("id", 1001);

			DBCursor cursor = yourCollection.find(condtionDBObject);
			// ѭ��������
			if (cursor.hasNext()) {
				DBObject dbObject = cursor.next();
				System.out.println(dbObject);
				// dbObject.removeField("msg");
				dbObject.put("msg", "hello");
				System.out.println("condtionDBObject:" + condtionDBObject+" dbObject:"+dbObject);
				yourCollection.update(condtionDBObject, dbObject);

			}
		}
		*/
		{	// �ֲ�����  $inc
			BasicDBObject condtionDBObject = new BasicDBObject();
			condtionDBObject.put("id", 1001);
			
			BasicDBObject incElementDBObject = new BasicDBObject();
			incElementDBObject.put("id", 10);

			BasicDBObject incDBObject = new BasicDBObject();
			incDBObject.put("$inc", incElementDBObject);
			
			System.out.println("condtionDBObject:" + condtionDBObject+" dbObject:"+incDBObject);
			yourCollection.update(condtionDBObject, incDBObject);
		

		}
		
		{	// �ֲ�����  $set  //Ĭ��
			BasicDBObject condtionDBObject = new BasicDBObject();
			condtionDBObject.put("id", 1005);
			
			BasicDBObject incElementDBObject = new BasicDBObject();
			incElementDBObject.put("firstname", "lj");
			incElementDBObject.put("lastname", "y");
			BasicDBObject incDBObject = new BasicDBObject();
			incDBObject.put("$set", incElementDBObject);
			
			System.out.println("condtionDBObject:" + condtionDBObject+" dbObject:"+incDBObject);
			yourCollection.update(condtionDBObject, incDBObject,false,true);
		

		}

		

		mongo.close();

	}

	public static void remove() throws Exception {

		Mongo mongo = new Mongo("localhost", 27017);
		// ������Ϊyourdb����ݿ⣬������ݿⲻ���ڵĻ���mongodb���Զ�����
		DB db = mongo.getDB("yourdb");
		// ��Mongodb�л����ΪyourColleection����ݼ��ϣ�������ݼ��ϲ����ڣ�Mongodb��Ϊ���½���
		DBCollection yourCollection = db.getCollection("yourCollection");

		BasicDBObject searchQuery = new BasicDBObject();
		searchQuery.put("id", 1005);

		WriteResult writeResult = yourCollection.remove(searchQuery);

		System.out.println(writeResult.getN());
		

		mongo.close();

	}

	public static void main(String[] args) {
		try {

			insert();
			// remove();
			//update();
			// findCondition2();
			//findAll();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
