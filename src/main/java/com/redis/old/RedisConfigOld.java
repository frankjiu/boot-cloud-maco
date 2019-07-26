package com.redis.old;
/*package com.redis;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import redis.clients.jedis.JedisPoolConfig;

@Configuration
public class RedisConfig {
	private Logger LOG = LoggerFactory.getLogger(RedisConfig.class);

	@Value("${spring.redis.database}")
	private Integer database;
	
	@Value("${spring.redis.host}")
	private String host;
	
	@Value("${spring.redis.port}")
	private Integer port;
	
	@Value("${redis.usepool}")
	private boolean usepool;
	
	@Value("${spring.redis.jedis.pool.max-idle}")
	private Integer maxIdle;
	
	@Value("${spring.redis.jedis.pool.min-idle}")
	private Integer minIdle;
	
	@Value("${spring.redis.jedis.pool.max-wait}")
	private Integer maxWait;
	
	@SuppressWarnings({ "deprecation"})
	@Bean
	JedisConnectionFactory jedisConnectionFactory() {
		JedisConnectionFactory factory = new JedisConnectionFactory();
		GenericObjectPoolConfig poolConfig = factory.getPoolConfig();
		
		poolConfig.setMaxIdle(maxIdle);
		poolConfig.setMinIdle(minIdle);
		poolConfig.setMaxWaitMillis(maxWait);
		
		factory.setPoolConfig((JedisPoolConfig) poolConfig);
		factory.setDatabase(database);
		factory.setHostName(host);
		factory.setPort(port);
		factory.setUsePool(usepool);
		return factory;
	}

	@Bean
	public RedisTemplate<String, String> redisTemplate() {
		RedisTemplate<String, String> template = new RedisTemplate<String, String>();
		template.setConnectionFactory(jedisConnectionFactory());
		template.setKeySerializer(new StringRedisSerializer());
		LOG.info("create RedisTemplate success");
		return template;
	}
}*/


