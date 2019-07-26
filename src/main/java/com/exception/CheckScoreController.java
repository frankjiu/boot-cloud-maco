package com.exception;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 测试分数不合法: http://localhost:8080/inputScore/in?score=1110
 * @author xinbe
 *
 */
@Controller
@RequestMapping("/inputScore")
public class CheckScoreController {
	@Autowired
	private CheckScoreService checkScoreService;

	@RequestMapping("/in")
	@ResponseBody
	public String inputScore(Integer score) {
		try {
			// 校验分数
			checkScoreService.check(score);
			return "ok:" + score;
		} catch (MyException e) {
			e.printStackTrace();
			return "no:" + score;
		}
	}
}
