package REDIS;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Parser;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ShardedJedisPool;

public class RedisUtil {

	static String defaultHost = "localhost";
	static int defaultPort = 6379;
	static int defaultTimeout = 20000;

	static JedisPool commonPool;

	public static void init(String host, int port, int timeout) {

		defaultHost = host;
		defaultPort = port;
		defaultTimeout = timeout;

		commonPool = getPool(defaultHost, defaultPort, defaultTimeout);
	}

	public static void close() {
		if (commonPool != null)
			commonPool.destroy();
	}

	public static JedisPool getPool() {
		return getPool(defaultHost, defaultPort, defaultTimeout);
	}

	public static Jedis getJedis() {

		if (commonPool == null) {
			commonPool = getPool(defaultHost, defaultPort, defaultTimeout);
		}
		return commonPool.getResource();
	}

	public static void returnJedis(Jedis jedis) {

		commonPool.returnResource(jedis);

	}

	public static JedisPool getPool(String host, int port, int timeout) {

		JedisPoolConfig config = new JedisPoolConfig();
		// 控制一个pool可分配多少个jedis实例，通过pool.getResource()来获取；
		// 如果赋值为-1，则表示不限制；如果pool已经分配了maxActive个jedis实例，则此时pool的状态为exhausted(耗尽)。
		config.setMaxActive(500);
		// 控制一个pool最多有多少个状态为idle(空闲的)的jedis实例。
		config.setMaxIdle(5);
		// 表示当borrow(引入)一个jedis实例时，最大的等待时间，如果超过等待时间，则直接抛出JedisConnectionException；
		config.setMaxWait(1000 * 100);
		// 在borrow一个jedis实例时，是否提前进行validate操作；如果为true，则得到的jedis实例均是可用的；
		config.setTestOnBorrow(true);
		// JedisPool pool = new JedisPool(config, "bje2b9.space.163.org", 6379);
		JedisPool pool = new JedisPool(config, host, port, timeout);

		return pool;
	}

	public static ShardedJedisPool getShardedPool(List<JedisShardInfo> shards) {

		JedisPoolConfig config = new JedisPoolConfig();
		// 控制一个pool可分配多少个jedis实例，通过pool.getResource()来获取；
		// 如果赋值为-1，则表示不限制；如果pool已经分配了maxActive个jedis实例，则此时pool的状态为exhausted(耗尽)。
		config.setMaxActive(500);
		// 控制一个pool最多有多少个状态为idle(空闲的)的jedis实例。
		config.setMaxIdle(5);
		// 表示当borrow(引入)一个jedis实例时，最大的等待时间，如果超过等待时间，则直接抛出JedisConnectionException；
		config.setMaxWait(1000 * 100);
		// 在borrow一个jedis实例时，是否提前进行validate操作；如果为true，则得到的jedis实例均是可用的；
		config.setTestOnBorrow(true);
		// JedisPool pool = new JedisPool(config, "bje2b9.space.163.org", 6379);

		ShardedJedisPool shardedJedisPool = new ShardedJedisPool(config, shards);

		return shardedJedisPool;
	}

	private static String readFileText(String redisInstanceFilePath, String encoding) throws Exception {

		FileInputStream fileInputStream = new FileInputStream(redisInstanceFilePath);
		InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream, encoding);
		BufferedReader bReader = new BufferedReader(inputStreamReader);

		String line = null;
		StringBuilder aStringBuilder = new StringBuilder();
		while ((line = bReader.readLine()) != null) {
			aStringBuilder.append(line + "\n");
		}
		bReader.close();
		
		
		return aStringBuilder.toString();
	}

	public static ShardedJedisPool getShardedPool(String redisInstanceFilePath) throws Exception {

		String xmlText=readFileText( redisInstanceFilePath,  "utf-8");		
	
		Document doc = Jsoup.parse(xmlText, "", Parser.xmlParser());	

		List<JedisShardInfo> shards = new ArrayList<JedisShardInfo>();
	
		
		
		for (Element e : doc.select("instance")) {
			
			//System.out.println(e);
			
			String host=e.attr("host");
			String port=e.attr("port");
			String name=e.attr("name");
			String timeout=e.attr("timeout");
			
			System.out.println(" name:"+name+" host:"+host+" port:"+port+" name:"+name+" timeout:"+timeout);
		
			JedisShardInfo jedisShardInfo=new JedisShardInfo(host, Integer.parseInt(port),name);
			jedisShardInfo.setTimeout(Integer.parseInt(timeout));
			
		
			shards.add(jedisShardInfo);

			//jedisShardInfo.
		}
		
		return  getShardedPool(shards);
	
	}

	public static void main(String[] args) throws Exception {

		String host = "bje2b9.space.163.org";
		int port = 7001;
		int timeout = 20000;
		JedisPool jedisPool = getPool(host, port, timeout);

	////	List<JedisShardInfo> shards = new ArrayList<JedisShardInfo>();
	//	shards.add(new JedisShardInfo(host, port, "share_1"));

	//	ShardedJedisPool shardedJedisPool = getShardedPool(shards);

		String redisInstanceFilePath="conf/redis-instance.xml";
		ShardedJedisPool shardedJedisPool=getShardedPool( redisInstanceFilePath);
		
		
	}

}
