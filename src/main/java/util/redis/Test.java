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
 * redis��Java�ͻ���Jedis������֤
 * 
 * @author
 */
public class Test {
	/**
	 * ����Ƭ�ͻ�������
	 */
	private Jedis jedis;
	/**
	 * ����Ƭ���ӳ�
	 */
	private JedisPool jedisPool;
	/**
	 * ��Ƭ�ͻ�������
	 */
	private ShardedJedis shardedJedis;
	/**
	 * ��Ƭ���ӳ�
	 */
	private ShardedJedisPool shardedJedisPool;
	private String ip = "172.16.205.186";

	/**
	 * ���캯��
	 */
	public Test() {
		initialPool();
		initialShardedPool();
		shardedJedis = shardedJedisPool.getResource();
		jedis = jedisPool.getResource();
	}

	private void initialPool() {

		JedisPoolConfig config = new JedisPoolConfig();
		config.setMaxActive(20);
		config.setMaxIdle(5);
		config.setMaxWait(1000l);
		config.setTestOnBorrow(false);
		jedisPool = new JedisPool(config, ip, 6379);
	}

	/**
	 * ��ʼ����Ƭ��
	 */
	private void initialShardedPool() {
		// �ػ�����
		JedisPoolConfig config = new JedisPoolConfig();
		config.setMaxActive(20);
		config.setMaxIdle(5);
		config.setMaxWait(1000l);
		config.setTestOnBorrow(false);
		// slave����
		List<JedisShardInfo> shards = new ArrayList<JedisShardInfo>();
		shards.add(new JedisShardInfo(ip, 6379, "master"));
		// �����
		shardedJedisPool = new ShardedJedisPool(config, shards);
	}

	public void show() {
		// key���
		testKey();
		// string���
		testString();
		// list���
		testList();
		// set���
		testSet();
		// sortedSet���
		testSortedSet();
		// hash���
		testHash();
		shardedJedisPool.returnResource(shardedJedis);
	}

	private void testKey() {
		System.out.println("=============key==========================");
		// ������
		System.out.println(jedis.flushDB());
		System.out.println(jedis.echo("foo"));
		// �ж�key�����
		System.out.println(shardedJedis.exists("foo"));
		shardedJedis.set("key", "values");
		System.out.println(shardedJedis.exists("key"));
	}

	private void testString() {
		System.out.println("=============String==========================");
		// ������
		System.out.println(jedis.flushDB());
		// �洢���
		shardedJedis.set("foo", "bar");
		System.out.println(shardedJedis.get("foo"));
		// ��key�����ڣ���洢
		shardedJedis.setnx("foo", "foo not exits");
		System.out.println(shardedJedis.get("foo"));
		// �������
		shardedJedis.set("foo", "foo update");
		System.out.println(shardedJedis.get("foo"));
		// ׷�����
		shardedJedis.append("foo", " hello, world");
		System.out.println(shardedJedis.get("foo"));
		// ����key����Ч�ڣ����洢���
		shardedJedis.setex("foo", 2, "foo not exits");
		System.out.println(shardedJedis.get("foo"));
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
		}
		System.out.println(shardedJedis.get("foo"));
		// ��ȡ��������
		shardedJedis.set("foo", "foo update");
		System.out.println(shardedJedis.getSet("foo", "foo modify"));
		// ��ȡvalue��ֵ
		System.out.println(shardedJedis.getrange("foo", 1, 3));
		System.out.println(jedis.mset("mset1", "mvalue1", "mset2", "mvalue2", "mset3", "mvalue3", "mset4", "mvalue4"));
		System.out.println(jedis.mget("mset1", "mset2", "mset3", "mset4"));
		System.out.println(jedis.del(new String[] { "foo", "foo1", "foo3" }));
	}

	private void testList() {
		System.out.println("=============list==========================");
		// ������
		System.out.println(jedis.flushDB());
		// ������
		shardedJedis.lpush("lists", "vector");
		shardedJedis.lpush("lists", "ArrayList");
		shardedJedis.lpush("lists", "LinkedList");
		// ���鳤��
		System.out.println(shardedJedis.llen("lists"));
		// ����
		// System.out.println(shardedJedis.sort("lists"));
		// �ִ�
		System.out.println(shardedJedis.lrange("lists", 0, 3));
		// �޸��б��е���ֵ
		shardedJedis.lset("lists", 0, "hello list!");
		// ��ȡ�б�ָ���±��ֵ
		System.out.println(shardedJedis.lindex("lists", 1));
		// ɾ���б�ָ���±��ֵ
		System.out.println(shardedJedis.lrem("lists", 1, "vector"));
		// ɾ�������������
		System.out.println(shardedJedis.ltrim("lists", 0, 1));
		// �б��ջ
		System.out.println(shardedJedis.lpop("lists"));
		// ����б�ֵ
		System.out.println(shardedJedis.lrange("lists", 0, -1));
	}

	private void testSet() {
		System.out.println("=============set==========================");
		// ������
		System.out.println(jedis.flushDB());
		// ������
		shardedJedis.sadd("sets", "HashSet");
		shardedJedis.sadd("sets", "SortedSet");
		shardedJedis.sadd("sets", "TreeSet");
		// �ж�value�Ƿ����б���
		System.out.println(shardedJedis.sismember("sets", "TreeSet"));
		;
		// ����б�ֵ
		System.out.println(shardedJedis.smembers("sets"));
		// ɾ��ָ��Ԫ��
		System.out.println(shardedJedis.srem("sets", "SortedSet"));
		// ��ջ
		System.out.println(shardedJedis.spop("sets"));
		System.out.println(shardedJedis.smembers("sets"));
		//
		shardedJedis.sadd("sets1", "HashSet1");
		shardedJedis.sadd("sets1", "SortedSet1");
		shardedJedis.sadd("sets1", "TreeSet");
		shardedJedis.sadd("sets2", "HashSet2");
		shardedJedis.sadd("sets2", "SortedSet1");
		shardedJedis.sadd("sets2", "TreeSet1");
		// ����
		System.out.println(jedis.sinter("sets1", "sets2"));
		// ����
		System.out.println(jedis.sunion("sets1", "sets2"));
		// �
		System.out.println(jedis.sdiff("sets1", "sets2"));
	}

	private void testSortedSet() {
		System.out.println("=============zset==========================");
		// ������
		System.out.println(jedis.flushDB());
		// ������
		shardedJedis.zadd("zset", 10.1, "hello");
		shardedJedis.zadd("zset", 10.0, ":");
		shardedJedis.zadd("zset", 9.0, "zset");
		shardedJedis.zadd("zset", 11.0, "zset!");
		// Ԫ�ظ���
		System.out.println(shardedJedis.zcard("zset"));
		// Ԫ���±�
		System.out.println(shardedJedis.zscore("zset", "zset"));
		// �����Ӽ�
		System.out.println(shardedJedis.zrange("zset", 0, -1));
		// ɾ��Ԫ��
		System.out.println(shardedJedis.zrem("zset", "zset!"));
		System.out.println(shardedJedis.zcount("zset", 9.5, 10.5));
		// �������ֵ
		System.out.println(shardedJedis.zrange("zset", 0, -1));
	}

	private void testHash() {
		System.out.println("=============hash==========================");
		// ������
		System.out.println(jedis.flushDB());
		// ������
		shardedJedis.hset("hashs", "entryKey", "entryValue");
		shardedJedis.hset("hashs", "entryKey1", "entryValue1");
		shardedJedis.hset("hashs", "entryKey2", "entryValue2");
		// �ж�ĳ��ֵ�Ƿ����
		System.out.println(shardedJedis.hexists("hashs", "entryKey"));
		// ��ȡָ����ֵ
		System.out.println(shardedJedis.hget("hashs", "entryKey"));
		// ������ȡָ����ֵ
		System.out.println(shardedJedis.hmget("hashs", "entryKey", "entryKey1"));
		// ɾ��ָ����ֵ
		System.out.println(shardedJedis.hdel("hashs", "entryKey"));
		// Ϊkey�е��� field ��ֵ�������� increment
		System.out.println(shardedJedis.hincrBy("hashs", "entryKey", 123l));
		// ��ȡ���е�keys
		System.out.println(shardedJedis.hkeys("hashs"));
		// ��ȡ���е�values
		System.out.println(shardedJedis.hvals("hashs"));
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		new Test().show();
	}
}