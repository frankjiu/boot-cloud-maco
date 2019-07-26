package com.redis.old;
/*package com.redis;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
public class RedisUtils {
	@Resource
	private RedisTemplate<String, String> redisTemplate;
	private static RedisUtils redisUtils;

	@PostConstruct
	public void init() {
		redisUtils = this;
		redisUtils.redisTemplate = this.redisTemplate;
	}

	*//**
	 * 保存到hash集合中
	 *//*
	public static void hashSet(String hName, String key, String value) {
		redisUtils.redisTemplate.opsForHash().put(hName, key, value);
	}

	*//**
	 * 从hash集合里取得
	 *//*
	public static Object hashGet(String hName, String key) {
		return redisUtils.redisTemplate.opsForHash().get(hName, key);
	}
	
	// 根据需要自行添加方法,此处予以省略......
	
}*/
