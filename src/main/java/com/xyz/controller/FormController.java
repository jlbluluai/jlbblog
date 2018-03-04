package com.xyz.controller;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.xyz.domain.User;
import com.xyz.util.Utils;

@Controller
public class FormController {

	private static final String[] login = { "loginForm", "phemRegister", "userRegister", "nickRegister", "getBack" };
	private static final String[] test = { "index" };
	private static final String[] main = { "main", "looking", "suggestion" };
	private static final String[] userInfo = { "userinfo" };
	private static final String[] blog = { "blogDraft", "blogFiles", "blogHandle", "blogManage", "blogPhotos",
			"blogPortal", "blogRead", "blogSettings" };

	/**
	 * 根据路径找到应该展示的视图位置并展示视图
	 * 
	 * @author 叶灬黎
	 * @param formname
	 * @return
	 */
	@RequestMapping(value = "/{formname}")
	public String loginForm(@PathVariable String formname) {
		String path = null;
		if (Utils.isThisStringGroup(login, formname)) {
			path = "login/" + formname;
		} else if (Utils.isThisStringGroup(test, formname)) {
			path = "test/" + formname;
		} else if (Utils.isThisStringGroup(main, formname)) {
			path = "main/" + formname;
		} else if (Utils.isThisStringGroup(userInfo, formname)) {
			path = "userinfo/" + formname;
		} else if (Utils.isThisStringGroup(blog, formname)) {
			path = "blog/" + formname;
		} else {
			path = "error";
		}
		return path;
	}

	@RequestMapping(value = "/userInfo/{formname}")
	public String goUserInfoForm(@PathVariable String formname) {
		return "userinfo/" + formname;
	}

	@RequestMapping(value = "/blog/{formname}")
	public String goBlogForm(@PathVariable String formname) {
		return "blog/" + formname;
	}

}