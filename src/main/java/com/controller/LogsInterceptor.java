package com.controller;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.domain.MacoLogs;
import com.service.MacoLogsService;
import com.constant.Constants;
import com.utils.LogAssist;;

public class LogsInterceptor implements HandlerInterceptor {

	@Autowired
	private MacoLogsService macoLogsService;

	/** 通过spring自动注入 */
	public void setMacoLogsServiceImpl(MacoLogsService service) {
		this.macoLogsService = service;
	}	
	
	/**
	 * preHandle方法是进行处理器拦截用的，顾名思义，该方法将在Controller处理之前进行调用，
	 * SpringMVC中的Interceptor拦截器是链式的，可以同时存在
	 * 多个Interceptor，然后SpringMVC会根据声明的前后顺序一个接一个的执行，
	 * 而且所有的Interceptor中的preHandle方法都会在
	 * Controller方法调用之前调用。SpringMVC的这种Interceptor链式结构也是可以进行中断的，
	 * 这种中断方式是令preHandle的返 回值为false，当preHandle的返回值为false的时候整个请求就结束了。
	 * 
	 * 可以考虑作权限，日志，事务等等 该方法在目标方法调用之前被调用； 若返回TURE,则继续调用后续的拦截器和目标方法
	 * 若返回FALSE,则不会调用后续的拦截器和目标方法
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		// System.out.println("CommContextInterceptor............." +
		// handler.getClass().getName());
		return true;
	}

	/**
	 * 这个方法只会在当前这个Interceptor的preHandle方法返回值为true的时候才会执行。postHandle是进行处理器拦截用的，
	 * 它的执行时间是在处理器进行处理之
	 * 后，也就是在Controller的方法调用之后执行，但是它会在DispatcherServlet进行视图的渲染之前执行，
	 * 也就是说在这个方法中你可以对ModelAndView进行操
	 * 作。这个方法的链式结构跟正常访问的方向是相反的，也就是说先声明的Interceptor拦截器该方法反而会后调用，
	 * 这跟Struts2里面的拦截器的执行过程有点像，
	 * 只是Struts2里面的intercept方法中要手动的调用ActionInvocation的invoke方法，
	 * Struts2中调用ActionInvocation的invoke方法就是调用下一个Interceptor
	 * 或者是调用action，然后要在Interceptor之前调用的内容都写在调用invoke之前，
	 * 要在Interceptor之后调用的内容都写在调用invoke方法之后。
	 * 
	 * 该方法在目标方法调用之后，渲染视图之前被调用； 可以对请求域中的属性或视图做出修改
	 * 
	 */
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
		response.setContentType("text/html;charset=UTF-8");
	}

	/**
	 * 该方法也是需要当前对应的Interceptor的preHandle方法的返回值为true时才会执行。该方法将在整个请求完成之后，
	 * 也就是DispatcherServlet渲染了视图执行，
	 * 这个方法的主要作用是用于清理资源的，当然这个方法也只能在当前这个Interceptor的preHandle方法的返回值为true时才会执行。
	 * 
	 * 在渲染视图之后被调用； 可以用来释放资源
	 */
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
		try {
			if (handler instanceof HandlerMethod) {
				HandlerMethod methodHandler = (HandlerMethod) handler;
				HttpSession session = request.getSession(false);
				String login_id = null;
				if (session != null) {
					login_id = (String) session.getAttribute(Constants.SESSION_LOGIN_ID);
				}
				// 记录日志
				if (login_id != null) {
					// 操作者
					MacoLogs macoLogs = new MacoLogs();
					macoLogs.setUserId(login_id);

					// 时间转化
					DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					String timeZoneId = TimeZone.getDefault().getID();
					TimeZone timeZone = TimeZone.getTimeZone(timeZoneId);
					formatter.setTimeZone(timeZone);
					String strDate = formatter.format(new Date());
					Date date = formatter.parse(strDate);

					// 操作IP和时间记录
					macoLogs.setIpAddress(getIp(request));
					macoLogs.setCreateTime(date);
					// 获取访问方法的注解
					LogAssist logAssist = methodHandler.getMethod().getAnnotation(LogAssist.class);
					if (logAssist != null) {
						macoLogs.setOpModule(logAssist.operationModule());
						macoLogs.setIntroduce(logAssist.describe());
						macoLogsService.save(macoLogs);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 获取真实IP
	 * 
	 * @param request
	 * @return
	 */
	public static String getIp(HttpServletRequest request) {
		String ipAddress = request.getHeader("x-forwarded-for");
		if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
			ipAddress = request.getHeader("Proxy-Client-IP");
		}
		if (ipAddress == null || ipAddress.length() == 0 || "unknow".equalsIgnoreCase(ipAddress)) {
			ipAddress = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
			ipAddress = request.getRemoteAddr();
			if (ipAddress.equals("0:0:0:0:0:0:0:1") || ipAddress.equals("127.0.0.1")) {
				// 通过网卡获取本机ip配置
				InetAddress inetAddress = null;
				try {
					inetAddress = InetAddress.getLocalHost();
				} catch (UnknownHostException e) {
					e.printStackTrace();
				}
				ipAddress = inetAddress.getHostAddress();
			}
		}
		// 使用多个代理时,第一个IP为客户端真实IP,通过','分割处理获取第一个
		if (null != ipAddress && ipAddress.length() > 15) {
			if (ipAddress.indexOf(",") > 0)
				ipAddress = ipAddress.substring(0, ipAddress.indexOf(","));
		}
		return ipAddress;
	}
}
