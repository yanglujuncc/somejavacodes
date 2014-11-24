package util.redis;


import java.util.Collections;
import java.util.List;







import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.Pipeline;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPipeline;
import redis.clients.jedis.ShardedJedisPool;
import util.redis.RedisUtil;



public class ListModel {

	
	JedisPool jedisPool;
		
	//final  static int ExpireSeconds=3600*3;
	
	
	public ListModel(String host, int port, int timeout) throws Exception {
		
		jedisPool = RedisUtil.getPool(host, port, timeout);
	}
	
	

	public void addElement(String key,String element ){
		
		
		Jedis redis = null;
		
		try {
			redis = jedisPool.getResource();
			redis.lpush(key, element);
			
		} finally {

			if (redis != null)
				jedisPool.returnResource(redis);
		}

	}
	public void addElementBatch(String[] keys,String[] elements ){
		
		
		Jedis redis = null;
		
		try {
			redis = jedisPool.getResource();
			
			Pipeline pipeline=redis.pipelined();
			for(int i=0;i<keys.length;i++){
				pipeline.lpush(keys[i], elements[i]);
			}
			pipeline.sync();
			
			
		} finally {

			if (redis != null)
				jedisPool.returnResource(redis);
		}

	}
	
	public List<String> getValueList(String key){
		
		
		Jedis redis = null;

		try {
			redis = jedisPool.getResource();

		
			List<String>  allList=redis.lrange(key, 0, -1);
			
			
			return allList;
		} finally {

			if (redis != null)
				jedisPool.returnResource(redis);
		}
		
		
	}

	public String getLastClick(String key){
		
		
		Jedis redis = null;

		try {
			redis = jedisPool.getResource();	
			String lastClick=redis.lindex(key, 0);						
			return lastClick;
			
		} finally {

			if (redis != null)
				jedisPool.returnResource(redis);
		}
		
		
	}


	public static void main(String[] args) throws Exception{
		
	

	}
}
