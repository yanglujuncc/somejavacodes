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



public class ListShardedModel {

	
	
	ShardedJedisPool shardedJedisPool;
	//final  static int ExpireSeconds=3600*3;
	
	
	public ListShardedModel(String redisInstanceFilePath) throws Exception {
		
		shardedJedisPool=RedisUtil.getShardedPool(redisInstanceFilePath);
	}
	
	

	public void addElement(String key,String element ){
		
		
		ShardedJedis redis = null;
		
		try {
			redis = shardedJedisPool.getResource();
			redis.lpush(key, element);
			
		} finally {

			if (redis != null)
				shardedJedisPool.returnResource(redis);
		}

	}
	public void addElementBatch(String[] keys,String[] elements ){
		
		
		ShardedJedis redis = null;
		
		try {
			redis = shardedJedisPool.getResource();
			
			ShardedJedisPipeline pipeline=redis.pipelined();
			for(int i=0;i<keys.length;i++){
				pipeline.lpush(keys[i], elements[i]);
			}
			pipeline.sync();
			
			
		} finally {

			if (redis != null)
				shardedJedisPool.returnResource(redis);
		}

	}
	
	public List<String> getValueList(String key){
		
		
		ShardedJedis redis = null;

		try {
			redis = shardedJedisPool.getResource();		
			List<String>  allList=redis.lrange(key, 0, -1);				
			return allList;
						
		} finally {

			if (redis != null)
				shardedJedisPool.returnResource(redis);
		}
		
		
	}

	public String getLastClick(String key){
		
		
		ShardedJedis redis = null;

		try {
			redis = shardedJedisPool.getResource();	
			String lastClick=redis.lindex(key, 0);						
			return lastClick;
			
		} finally {

			if (redis != null)
				shardedJedisPool.returnResource(redis);
		}
		
		
	}


	public static void main(String[] args) throws Exception{
		
	

	}
}
