package com.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/redis")
public class RedisController {
	@Autowired
	RedisUtils redisUtil;
	
	//Redis数据库索引(默认为0)
	@Value("${spring.redis.database}")
	private Integer database;

	//@RequestMapping(value = "", method = RequestMethod.POST)
	@RequestMapping("")
	@ResponseBody
	public String getRedis() {
		try {
			// 设置key (使用命令行获取值时要使用 ./redis-cli --raw 启动,避免获取乱码)
			redisUtil.set("frank", "缓存数据", database);
			// 设置key过期时间
			Long expireTime = redisUtil.expire("frank", 10, database);
			
			//取值
			String value = redisUtil.get("frank", database);
			System.out.println("====================== value:" + value + ", 过期剩余时间:" + expireTime + " ======================");
			return "ok";
		} catch (Exception e) {
			return "no";
		}
	}
}