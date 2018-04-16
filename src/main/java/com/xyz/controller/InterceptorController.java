package com.xyz.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.xyz.domain.User;
import com.xyz.service.UserService;
import com.xyz.service.UserTempService;

@Controller
public class InterceptorController {

	@Autowired
	@Qualifier("userTempService")
	private UserTempService userTempService;

	@Autowired
	@Qualifier("userService")
	private UserService userService;

	/**
	 * 注册拦截器的请求，防止用户随意访问第二步和第三步的页面，通过拦截器结合临时表的数据判断进行拦截
	 * 
	 * @author 叶灬黎
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/registerInterceptor")
	public String registerInterceptor(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String phone = request.getParameter("phone");
		String email = request.getParameter("email");
		String sid = request.getParameter("id");
		Long id = null;
		if (sid != null) {
			id = Long.parseLong(sid);
		}
		if (phone != null && email != null) {
			if (userTempService.getTheUser(phone, email)) {
				return "login/userRegister";
			} else {
				return "error";
			}
		} else if (id != null) {
			if (userService.verUser(id)) {
				return "login/nickRegister";
			} else {
				return "error";
			}

		}
		return "error";
	}

	@RequestMapping(value = "/blogBehindInterceptor/{path}")
	public String blogBehindInterceptor(HttpServletRequest request, HttpServletResponse response,
			@PathVariable String path) throws Exception {

		System.out.println(path);

		User user = (User) request.getSession().getAttribute("user");
		if (user == null) {
			return "error";
		}

		if (user.getIid() != 3) {
			return "error";
		}

		return "blog/" + path;
	}

}
