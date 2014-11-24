package util.redis;

import java.util.Map;

public class TestLongMapModel {

	
	public static void main(String[] args){
		   
		   String host="bje2b11.space.163.org";
		   int port=8804;
		   int timeout=20000;
		   
		   LongMapModel longMapModel=new LongMapModel(host,port,timeout);
		   
		   String key="sssssssssssssssssssss";
		   String field="bbbbbbbbbbbbb";
		   long value=200;
		   longMapModel.incr(key, field, value);
		   
		   long gotvalue= longMapModel.getField(key, field);
		   System.out.println(gotvalue);
		   
		   Map<String, Long> allFields= longMapModel.getAllField(key);
		   System.out.println(allFields.entrySet());
		//   longMapModel.getAllField(key)
	}
}
