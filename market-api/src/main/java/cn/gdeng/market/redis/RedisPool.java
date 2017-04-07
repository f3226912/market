package cn.gdeng.market.redis;

import redis.clients.jedis.Jedis;

/**redis连接池。
 * @author wjguo
 * datetime 2016年9月20日 下午12:03:50  
 *
 */
public interface RedisPool {	
	/**
	 * 获取Redis实例.
	 * @return Redis工具类实例
	 */
	Jedis getJedis();

	/**
	 * 销毁jedis实例，并从连接池中去除，使得当前实例不可再使用。
	 * @param jedis jedis实例
	 */
	void destroyJedis(Jedis jedis);
	
	
	/** 释放jedis实例，返回到连接池中。
	 * @param jedis
	 */
	void returnResource(Jedis jedis);}
