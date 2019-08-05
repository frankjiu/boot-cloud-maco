package com.err;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 抛异常测试
 */
@Controller
@RequestMapping("/err")
public class TestError {
	@RequestMapping("/test")
	public String home() throws Exception {
		throw new MyErrException("101", "运行错误");
	}
}