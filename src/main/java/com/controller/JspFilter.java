package com.controller;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 禁止直接访问jsp, 直接访问将跳转到登录页
 */
public class JspFilter implements Filter {

	@Override
	public void init(FilterConfig arg0) throws ServletException {

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest httpServletRequest = (HttpServletRequest) request;
		HttpServletResponse httpServletResponse = (HttpServletResponse) response;
		
		// 返回的协议名称,默认是http
		String scheme = request.getScheme();
		// 返回的是你浏览器中显示的主机名
		String serverName = request.getServerName();
		// 获取端口号
		String serverPort = request.getServerPort() + "";
		// 拼接访问前缀
		String beforeUrl = scheme + "://" + serverName + ":" + serverPort; // http://localhost:8080
		
		String url = httpServletRequest.getRequestURI();
		if (url != null && url.endsWith(".jsp")) {
			httpServletResponse.sendRedirect(beforeUrl + "/login");
			return;
		}
		chain.doFilter(request, response);
	}

	@Override
	public void destroy() {

	}

}