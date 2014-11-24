package util.redis;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.Pipeline;

public class LongMapModel {

	JedisPool redisPool;

	public LongMapModel(String host, int port, int timeout) {
		redisPool = RedisUtil.getPool(host, port, timeout);
	}

	public void incr(String key, String field, long value) {

		Jedis redis = null;
		try {
			redis = redisPool.getResource();
			redis.hincrBy(key, field, value);

		} finally {

			if (redis != null)
				redisPool.returnResource(redis);
		}

	}

	public class IncrTask {
		String key;
		String field;
		long value;
	}

	public void incrBatch(IncrTask[] tasks) {

		Jedis redis = null;
		Pipeline pipeline = null;
		try {
			redis = redisPool.getResource();

			pipeline = redis.pipelined();
			for (IncrTask incrTask : tasks) {
				redis.hincrBy(incrTask.key, incrTask.field, incrTask.value);
			}

			pipeline.sync();

		} finally {

			if (redis != null)
				redisPool.returnResource(redis);
		}

	}

	public Map<String, Long> getAllField(String key) {
		Jedis redis = null;
		try {
			redis = redisPool.getResource();
			Map<String, String> allFields = redis.hgetAll(key);
			if (allFields == null)
				return null;
			Map<String, Long> allFieldsLong = new HashMap<String, Long>();

			for (Entry<String, String> entry : allFields.entrySet()) {

				long value = Long.parseLong(entry.getValue());
				allFieldsLong.put(entry.getKey(), value);
			}
			
			return allFieldsLong;
			
		} finally {

			if (redis != null)
				redisPool.returnResource(redis);
		}

		
	}

	public long getField(String key, String field) {

		Jedis redis = redisPool.getResource();
		String strValue = redis.hget(key, field);

		long value = Long.parseLong(strValue);

		return value;
	}

}
