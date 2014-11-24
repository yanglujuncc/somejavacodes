package REDIS;

import java.util.List;
import java.util.Set;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Pipeline;

public class testJedis {

	public static void main(String[] args){
		
	
		//creatTableExample();
		Jedis jedis = new Jedis("bje2b9.space.163.org",7777);
		
		boolean exists=jedis.exists("mylist");
		System.out.println(exists);
		
		jedis.lpush("mylist", "1");
		jedis.lpush("mylist", "4");
		jedis.lpush("mylist", "6");
		
		List<String> list3 = jedis.lrange("mylist", 0, -1);
		System.out.println(list3);
	
		/*
		 * 
		jedis.select(0);
		Pipeline pipeline=new Jedis("bje2b9.space.163.org").pipelined();
		pipeline.select(0);
		
		jedis.set("foo", "xxx");
		String value = jedis.get("foo");
		System.out.println(value);
		
		System.out.println(jedis.getDB());;
		
		jedis.del("mylist");
		
		
		jedis.lpush("mylist", "1");
		jedis.lpush("mylist", "4");
		jedis.lpush("mylist", "6");
		
		pipeline.lpush("mylist", "7");
		pipeline.lpush("mylist", "8");
	
		
		jedis.lpush("mylist", "3");
		jedis.lpush("mylist", "0");
		
		Object list = jedis.lrange("mylist", 0, -1);
		System.out.println(list);
		
		
		pipeline.sync();
		List<String> list2 = jedis.lrange("mylist", 0, -1);
		System.out.println(list2);
		
		System.out.println(jedis.del("mylist2"));
		
		Set<String> keySet=jedis.keys("my????");
		System.out.println(keySet);
		
		
		
		
		*/
		
		//pipeline.lpush(key, string)
	//	jedis.incr(key)
		//jedis.set
	}
}
