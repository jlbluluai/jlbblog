package com.xyz.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

public class BlogBehindInterceptor implements HandlerInterceptor {

	// 定义需要拦截的路径
	private static final String[] INTERCPTOR_URI = { "/blogDraft", "/blogFiles", "/blogHandle", "/blogManage",
			"/blogPhotos", "/blogSettings" };

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		String path = request.getServletPath();
		for (String s : INTERCPTOR_URI) {
			if (path.contains(s)) {
				request.getRequestDispatcher("/blogBehindInterceptor/" + path).forward(request, response);
				return false;
			}
		}
		return true;
	}

	/**
	 * 这个方法在preHandle方法返回值为true的时候才会执行。 执行时间是在处理器进行处理之
	 * 后，也就是在Controller的方法调用之后执行。
	 */
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {

	}

	/**
	 * 该方法需要preHandle方法的返回值为true时才会执行。 该方法将在整个请求完成之后执行，主要作用是用于清理资源。
	 */
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {

	}

}
