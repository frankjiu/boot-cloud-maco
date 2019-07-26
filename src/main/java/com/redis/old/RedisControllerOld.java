package com.redis.old;
/*package com.redis;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/redis")
public class OprateRedis {
	
	@RequestMapping("")
	@ResponseBody
	public String testCache() throws Exception {
		try {
			RedisUtils.hashSet("redisKey", "url", "==================www.baidu.com==================");
			System.out.println(RedisUtils.hashGet("redisKey", "url"));
			return "ok";
		} catch (Exception e) {
			e.printStackTrace();
			return "no";
		}
	}
	
}*/
