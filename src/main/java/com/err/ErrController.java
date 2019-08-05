package com.err;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 * 异常捕捉并跳转错误页面
 */
@ControllerAdvice
public class ErrController {
	/**
	 * 全局异常捕捉处理
	 */
	@ResponseBody
	@ExceptionHandler(value = Exception.class)
	public Map<String, Object> errorHandler(Exception ex) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("code", 100);
		map.put("msg", ex.getMessage());
		return map;
	}

	/**
	 * 拦截捕捉自定义异常 MyErrException.class
	 */
	@ExceptionHandler(value = MyErrException.class)
	public ModelAndView myErrorHandler(MyErrException ex) {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("error");
		modelAndView.addObject("code", ex.getCode());
		modelAndView.addObject("msg", ex.getMsg());
		return modelAndView;
	}
}