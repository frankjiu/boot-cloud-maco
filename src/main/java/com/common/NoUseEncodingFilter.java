package com.common;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 使用过滤器处理 Request 和 Response 乱码
 */
public class NoUseEncodingFilter implements Filter {

	private String encoding = "UTF-8";

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		//String catchEncoding = filterConfig.getServletContext().getInitParameter("encoding");
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		System.out.println("编码格式为:" + encoding);
		// 统一处理post请求乱码
		req.setCharacterEncoding(encoding);
		// 统一处理响应乱码
		res.setContentType("text/html;charset=" + encoding);
		// 放行
		chain.doFilter(req, res);
	}

	@Override
	public void destroy() {

	}

}
