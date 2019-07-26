package com.controller;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSONObject;
import com.constant.ApiResponse;

@Controller
@RequestMapping("/test")
public class ResultResponse {
	/**
	 * 返回信息给客户端
	 *
	 * @param response
	 * @param out
	 * @param apiResponse
	 */
	@SuppressWarnings("unused")
	private static void responseMessage(HttpServletResponse response, PrintWriter out, ApiResponse apiResponse) {
		
		response.setContentType("text/html; charset=utf-8");
		out.println("<script language='javascript'>");
		out.println("alert('Hello!你好!')");
		out.println("</script>");
		
		/*response.setContentType("application/json; charset=utf-8");
		out.print(JSONObject.toJSONString(apiResponse));
		out.flush();
		out.close();*/
		
	}

	@RequestMapping("/it")
	public void it(HttpServletRequest request, HttpServletResponse response) {
		try {
			ApiResponse apiResponse = new ApiResponse();
			apiResponse.setErrCode(1);
			apiResponse.setErrMsg("返回正常!!!");
			Thread.sleep(2000);
			PrintWriter writer = response.getWriter();
			
			//responseMessage(response, response.getWriter(), apiResponse);
			response.setContentType("application/json; charset=utf-8");
			writer.print(JSONObject.toJSONString(apiResponse));
			writer.flush();
			writer.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
