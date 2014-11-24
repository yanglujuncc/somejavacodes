package util.redis;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;



import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPipeline;
import redis.clients.jedis.ShardedJedisPool;

public class LongMapShardedModel {

	ShardedJedisPool shardedJedisPool;

	public LongMapShardedModel(String redisInstanceFilePath) throws Exception {
		
		shardedJedisPool=RedisUtil.getShardedPool(redisInstanceFilePath);
	}
	public void set(String key, String field, long value) {

		ShardedJedis redis = null;
		try {
			redis = shardedJedisPool.getResource();
		//	redis.hincrBy(key, field, value);
			redis.hset(key, field, value+"");
		} finally {

			if (redis != null)
				shardedJedisPool.returnResource(redis);
		}

	}
	public void incr(String key, String field, long value) {

		ShardedJedis redis = null;
		try {
			redis = shardedJedisPool.getResource();
			redis.hincrBy(key, field, value);

		} finally {

			if (redis != null)
				shardedJedisPool.returnResource(redis);
		}

	}
	public void incrBatch(String[] keys, String[] fields, long[] values) {

		ShardedJedis sredis = null;
		ShardedJedisPipeline pipeline = null;
		try {
			sredis = shardedJedisPool.getResource();

			pipeline =sredis.pipelined();
			
			for(int i=0;i<keys.length;i++){
				pipeline.hincrBy(keys[i], fields[i], values[i]);
			}
			
			pipeline.sync();

		} finally {

			if (sredis != null)
				shardedJedisPool.returnResource(sredis);
		}

	}

	public class IncrTask {
		String key;
		String field;
		long value;
	}

	public void incrBatch(IncrTask[] tasks) {

		ShardedJedis sredis = null;
		ShardedJedisPipeline pipeline = null;
		try {
			sredis = shardedJedisPool.getResource();

			pipeline =sredis.pipelined();
			for (IncrTask incrTask : tasks) {
				pipeline.hincrBy(incrTask.key, incrTask.field, incrTask.value);
			}
			pipeline.sync();

		} finally {

			if (sredis != null)
				shardedJedisPool.returnResource(sredis);
		}

	}

	public Map<String, Long> getAllField(String key) {
		ShardedJedis sredis = null;
		try {
			sredis = shardedJedisPool.getResource();
			Map<String, String> allFields = sredis.hgetAll(key);
			if (allFields == null)
				return null;
			Map<String, Long> allFieldsLong = new HashMap<String, Long>();

			for (Entry<String, String> entry : allFields.entrySet()) {

				long value = Long.parseLong(entry.getValue());
				allFieldsLong.put(entry.getKey(), value);
			}
			
			return allFieldsLong;
			
		} finally {

			if (sredis != null)
				shardedJedisPool.returnResource(sredis);
		}

		
	}

	public long getField(String key, String field) {

		ShardedJedis sredis = shardedJedisPool.getResource();
		String strValue = sredis.hget(key, field);

		long value = Long.parseLong(strValue);

		return value;
	}

	public static void main(String[] args) throws Exception{
		
		LongMapShardedModel longMapShardedModel=new LongMapShardedModel("conf/redis-instance-account-device.xml");
		
		longMapShardedModel.incr("a", "_b", 1);
		//longMapShardedModel.incr("a", "_b", 1);
		//longMapShardedModel.set("a", "_b", 1);
		System.out.println(longMapShardedModel.getField("a", "_b"));
		
		//account=STyWRRCjmsq4Ymd4QruLoQ== device=aN6u2Gcv2g1y/dJ6sEy0Qvslo95NI9InKEr1hU5sraPRgVjCWT7Gl0Z/rRXEt3bY

		
	}
}
