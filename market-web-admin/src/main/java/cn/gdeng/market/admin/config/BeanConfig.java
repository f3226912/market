package cn.gdeng.market.admin.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.session.web.http.CookieSerializer;
import org.springframework.session.web.http.DefaultCookieSerializer;

import cn.gdeng.market.admin.enums.MyConst;
import cn.gdeng.market.util.PropertyUtil;
import redis.clients.jedis.JedisPoolConfig;

//@EnableRedisHttpSession
@Configuration
public class BeanConfig {

	@Autowired
	private JedisPoolConfig jedisPoolConfig;
	@Autowired
	PropertyUtil propertyUtil;

	// @Bean
	// public JedisConnectionFactory connectionFactory() {
	// String[] jedisClusterNodes = propertyUtil.getValue("redisClusterNodes").split(",");
	// RedisClusterConfiguration clusterConfig = new RedisClusterConfiguration(Arrays.asList(jedisClusterNodes));
	// return new JedisConnectionFactory(clusterConfig,jedisPoolConfig);
	// }
	@Bean
	public JedisConnectionFactory connectionFactory() {
		String[] jedisClusterNodes = propertyUtil.getValue("redisClusterNodes").split(",");
		String[] host = jedisClusterNodes[0].split(":");
		String ip = host[0];
		String port = host[1];
		JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory();
		jedisConnectionFactory.setPoolConfig(jedisPoolConfig);
		jedisConnectionFactory.setPort(Integer.valueOf(port));
		jedisConnectionFactory.setHostName(ip);
		return jedisConnectionFactory;
	}

	@Bean
	public CookieSerializer cookieSerializer() {
		DefaultCookieSerializer serializer = new DefaultCookieSerializer();
		serializer.setCookieName(MyConst.SEESION_COOKIE_NAME);
		serializer.setCookiePath("/");
//		serializer.setDomainName("www.a.com");
		serializer.setDomainNamePattern("^.+?\\.(\\w+\\.[a-z]+)$");
		return serializer;
	}
}