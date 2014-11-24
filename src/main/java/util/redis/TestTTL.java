package util.redis;

import java.util.ArrayList;
import java.util.List;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;

/**
 * redis的Java客户端Jedis测试验证
 * 
 * @author
 */
public class TestTTL {

	public static void main(String[] args) {
		String host = "app-127.photo.163.org";
		int port = 9904;
		int timeout = 20000;
		JedisPool jedisPool = RedisUtil.getPool(host, port, timeout);
		Jedis jedis=jedisPool.getResource();
		
		//jedis.expire("a", 10);
		
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("ttl[a]:"+jedis.ttl("a"));
		//jedis.incrBy("a",2);
		
		System.out.println("a:"+jedis.get("a"));
		
		jedisPool.returnResource(jedis);
		jedisPool.destroy();
		
		
	}
}