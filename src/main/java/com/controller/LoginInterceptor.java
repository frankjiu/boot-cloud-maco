package com.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.constant.Constants;

public class LoginInterceptor implements HandlerInterceptor{

	@Override
	public void afterCompletion(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, Exception arg3) throws Exception {
	}

	@Override
	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, ModelAndView arg3) throws Exception {
	}
	
	//方法执行前调用
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		// 注意:需要做此判断
		if(handler instanceof HandlerMethod){
			HandlerMethod methodHandler = (HandlerMethod) handler;
			System.out.println("self-defined-Interceptor......request.class:" + methodHandler.getBeanType() + "......execute method:" + methodHandler.getMethod().getName());
			// 进入 Handler方法之前执行以进行身份认证(无需拦截URL除外),如果认证不通过表示当前用户没有登陆,需要此方法进行拦截而不再向下执行
			// 判断session,如果存在则返回当前session, 否则创建一个
			HttpSession session = request.getSession(true);
			// 获取请求的url
			String url = request.getRequestURI();
			System.out.println("请求的URL========================================" + url);
			// 判断url是否是公开 地址(实际使用时将公开 地址配置配置在文件中, 如果已经在配置文件进行配置,则注释此处)
			if (url.indexOf("/login") >= 0 || 
				url.indexOf("/checkLogin") >= 0 || 
				url.indexOf("/logout") >= 0 || 
				url.indexOf("/code/getCode") >= 0) {
				// 将进行登陆相关操作的URL给予放行!!!
				return true;
			}
			
			// 从session中取出用户身份信息. return true: 表示放行; return false: 表示拦截,不向下执行
			if ((session.getAttribute(Constants.SESSION_LOGIN_ID)) == null) {
				// 返回的协议名称,默认是http
				String scheme = request.getScheme();
				// 返回的是你浏览器中显示的主机名
				String serverName = request.getServerName();
				// 获取端口号
				String serverPort = request.getServerPort() + "";
				// 拼接访问前缀
				String beforeUrl = scheme + "://" + serverName + ":" + serverPort; // http://localhost:8080
				// System.out.println("beforeUrl====================" + beforeUrl);
				// System.out.println("path====================" + request.getContextPath());
				System.out.println("请求的url====================" + beforeUrl + "/login");
				// response.sendRedirect("http://localhost:8080/sys/index/loginPage.do"); 注意这里路径应该指向登录页
				response.sendRedirect(beforeUrl + "/login");
				return false;
			}
		}
		return true;
	}
	

}
