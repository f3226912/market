package org.gudeng.commerce.info.service.impl;

import org.junit.Test;

import redis.clients.jedis.Jedis;

public class JedisTest {


	@Test
	public void test(){
		Jedis jedis = new Jedis("10.17.1.166");
		System.out.println(jedis.ping());
		System.out.println(jedis.exists("key1"));
		System.out.println(jedis.get("key1"));
		jedis.hset("key2", "a", "1");
		jedis.hset("user:1000:password", "b", "2");
	}
}
