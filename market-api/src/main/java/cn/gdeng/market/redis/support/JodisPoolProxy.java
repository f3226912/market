package cn.gdeng.market.redis.support;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.gdeng.market.redis.RedisPool;
import io.codis.jodis.RoundRobinJedisPool;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPoolConfig;

/**
 * jodis连接池配置
 * 
 * @author zhangnf
 *
 */
public class JodisPoolProxy implements RedisPool{
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	private static RoundRobinJedisPool roundRobinJedisPool;
	
	/**
	 *  默认的失败重试次数。
	 */
	private static final int DEFAULT_FAILURE_RETRIES = 4;
	
	/**失败重试次数。
	 * 
	 */
	private int failureRetrie = DEFAULT_FAILURE_RETRIES;

	public JodisPoolProxy(JedisPoolConfig jedisPoolConfig, String zkAddr, int zkSessionTimeoutMs, int timeoutMs,
			String zkProxyDir) {
		roundRobinJedisPool = RoundRobinJedisPool.create().poolConfig(jedisPoolConfig).timeoutMs(timeoutMs)
				.zkProxyDir(zkProxyDir).curatorClient(zkAddr, zkSessionTimeoutMs).build();
	}

	public Jedis getJedis() {
		int count = 1;
		do {
			try {
				Jedis jedis = roundRobinJedisPool.getResource();
				if (jedis.isConnected()) {
					return jedis;
				}
			} catch (Exception e) {
				count ++ ;
				logger.error(String.format("get jedis %d time failure*****************************************", count));
			}
		} while (count <= failureRetrie);
		
		return null;
	}

	@Override
	public void destroyJedis(Jedis jedis) {
		//jodis的连接池暂不支持。
		throw new UnsupportedOperationException();
	}

	@Override
	public void returnResource(Jedis jedis) {
		jedis.close();
	}

	public int getFailureRetrie() {
		return failureRetrie;
	}

	public void setFailureRetrie(int failureRetrie) {
		this.failureRetrie = failureRetrie;
	}
}
