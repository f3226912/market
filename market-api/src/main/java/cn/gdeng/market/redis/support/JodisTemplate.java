package cn.gdeng.market.redis.support;

import java.util.List;
import java.util.Map;
import java.util.Set;

import cn.gdeng.market.redis.RedisCallBack;
import cn.gdeng.market.redis.RedisPool;
import cn.gdeng.market.redis.RedisTemplateInterface;
import cn.gdeng.market.util.SerializeUtil;
import redis.clients.jedis.Jedis;

/**
 * jodis模板类。
 * 
 * @author wjguo datetime 2016年9月14日 上午11:02:17
 *
 */
public class JodisTemplate implements RedisTemplateInterface {

	/**
	 * 连接池。
	 * 
	 */
	private RedisPool proxy;

	public void setProxy(RedisPool proxy) {
		this.proxy = proxy;
	}

	@Override
	public String set(String key, String value) {
		Jedis jedis = null;
		try {
			jedis = proxy.getJedis();
			return jedis.set(key, value);
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}
	}

	@Override
	public String set(String key, String value, String nxxx, String expx, long time) {
		Jedis jedis = null;
		try {
			jedis = proxy.getJedis();
			return jedis.set(key, value, nxxx, expx, time);
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}
	}

	@Override
	public String get(String key) {
		Jedis jedis = null;
		try {
			jedis = proxy.getJedis();
			return jedis.get(key);
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}
	}

	public Object getObject(byte[] key) {
		Jedis jedis = null;
		try {
			jedis = proxy.getJedis();
			byte[] bs = jedis.get(key);
			if (bs != null) {
				Object obj = SerializeUtil.unserialize(jedis.get(key));
				return obj;
			} else {
				return null;
			}
		} catch (Exception e) {
			return null;
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}
	}

	@Override
	public Long exists(String... keys) {
		Jedis jedis = null;
		try {
			jedis = proxy.getJedis();
			return jedis.exists(keys);
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}
	}

	@Override
	public Boolean exists(String key) {
		Jedis jedis = null;
		try {
			jedis = proxy.getJedis();
			return jedis.exists(key);
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}
	}

	@Override
	public Long del(String... keys) {
		Jedis jedis = null;
		try {
			jedis = proxy.getJedis();
			return jedis.del(keys);
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}
	}

	@Override
	public Long del(String key) {
		Jedis jedis = null;
		try {
			jedis = proxy.getJedis();
			return jedis.del(key);
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}
	}

	@Override
	public Long del(byte[] key) {
		Jedis jedis = null;
		try {
			jedis = proxy.getJedis();
			return jedis.del(key);
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}
	}

	@Override
	public String type(String key) {
		Jedis jedis = null;
		try {
			jedis = proxy.getJedis();
			return jedis.type(key);
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}
	}

	@Override
	public Set<String> keys(String pattern) {
		Jedis jedis = null;
		try {
			jedis = proxy.getJedis();
			return jedis.keys(pattern);
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}
	}

	@Override
	public Long expire(String key, int seconds) {
		Jedis jedis = null;
		try {
			jedis = proxy.getJedis();
			return jedis.expire(key, seconds);
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}
	}

	@Override
	public String setex(String key, int seconds, String value) {
		Jedis jedis = null;
		try {
			jedis = proxy.getJedis();
			return jedis.setex(key, seconds, value);
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}
	}

	@Override
	public String setex(final byte[] key, final int seconds, final byte[] value) {
		Jedis jedis = null;
		try {
			jedis = proxy.getJedis();
			return jedis.setex(key, seconds, value);
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}
	}

	@Override
	public Long hset(String key, String field, String value) {
		Jedis jedis = null;
		try {
			jedis = proxy.getJedis();
			return jedis.hset(key, field, value);
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}
	}

	@Override
	public String hget(String key, String field) {
		Jedis jedis = null;
		try {
			jedis = proxy.getJedis();
			return jedis.hget(key, field);
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}
	}

	@Override
	public String hmset(String key, Map<String, String> hash) {
		Jedis jedis = null;
		try {
			jedis = proxy.getJedis();
			return jedis.hmset(key, hash);
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}
	}

	@Override
	public List<String> hmget(String key, String... fields) {
		Jedis jedis = null;
		try {
			jedis = proxy.getJedis();
			return jedis.hmget(key, fields);
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}
	}

	@Override
	public Long hincrBy(String key, String field, long value) {
		Jedis jedis = null;
		try {
			jedis = proxy.getJedis();
			return jedis.hincrBy(key, field, value);
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}
	}

	@Override
	public String set(byte[] key, byte[] value) {
		Jedis jedis = null;
		try {
			jedis = proxy.getJedis();
			return jedis.set(key, value);
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}
	}

	@Override
	public byte[] get(byte[] key) {
		Jedis jedis = null;
		try {
			jedis = proxy.getJedis();
			return jedis.get(key);
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T exec(RedisCallBack callback) {
		Jedis jedis = null;
		try {
			jedis = proxy.getJedis();
			return (T) callback.invoke(jedis);
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}
	}

}
