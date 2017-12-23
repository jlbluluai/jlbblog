package com.xyz.controller;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageInfo;
import com.xyz.domain.Artical;
import com.xyz.domain.Dynamic;
import com.xyz.domain.User;
import com.xyz.domain.UserInfo;
import com.xyz.dto.UserHome;
import com.xyz.service.ArticalService;
import com.xyz.service.DynamicService;
import com.xyz.service.UserInfoService;
import com.xyz.service.UserService;
import com.xyz.util.Utils;

import net.sf.json.JSONObject;

@Controller
public class UserHomeController {

	@Autowired
	@Qualifier("userInfoService")
	private UserInfoService userInfoService;

	@Autowired
	@Qualifier("userService")
	private UserService userService;

	@Autowired
	@Qualifier("dynamicService")
	private DynamicService dynamicService;

	@Autowired
	@Qualifier("articalService")
	private ArticalService articalService;

	/* 用户主页逻辑 */

	/**
	 * 获取用户主页共用部分相关信息
	 * 
	 * @param user
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/getUserHomeInfo", method = RequestMethod.GET)
	@ResponseBody
	public UserHome getUserHomeInfo(User user, HttpSession session) {
		User u = (User) session.getAttribute("user");
		UserHome userHome = new UserHome();
		if (user.getId() == u.getId()) {
			userHome.setMaster(u.getNickname());
			userHome.setAccess(u.getNickname());
		} else {
			userHome.setMaster(userService.getAppointedItem(user.getId()).getNickname());
			userHome.setAccess(u.getNickname());
		}
		return userHome;
	}

	/**
	 * 获取用户主页的动态
	 * 
	 * @param uid
	 * @param current
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/getDynamics", method = RequestMethod.GET)
	public void getDynamics(@RequestParam("id") Long uid, @RequestParam("current") Integer current,
			HttpServletResponse response) throws Exception {
		Dynamic dynamic = new Dynamic();
		dynamic.setUid(uid);
		PageInfo<Dynamic> pageInfo = dynamicService.getAppointedPageItems(current, 8, dynamic);

		JSONObject json = new JSONObject();
		json.put("dynamics", pageInfo.getList());
		json.put("total", pageInfo.getPages());
		response.setContentType("text/html;charset=UTF-8");
		response.getWriter().write(json.toString());
	}

	/**
	 * 删除指定的一条动态
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/cutOneDynamic")
	@ResponseBody
	public boolean cutOneDynamic(Long id) {
		return dynamicService.cutAppointedItem(id);
	}

	/**
	 * 写入一条动态
	 * 
	 * @param dynamic
	 * @return
	 */
	@RequestMapping(value = "/writeOneDynamic")
	@ResponseBody
	public Boolean writeOneDynamic(Dynamic dynamic) {
		dynamic.setId(Utils.createComplexId());
		dynamic.setCreateTime(new Date());
		return dynamicService.saveAppointedItem(dynamic);
	}

	/* 我的文章逻辑 */

	/**
	 * 用户信息模块获取对应文章
	 * 
	 * @param uid
	 * @param current
	 * @param response
	 * @param session
	 * @throws Exception
	 */
	@RequestMapping(value = "/getUserInfoArticals", method = RequestMethod.GET)
	public void getUserInfoArticals(@RequestParam("id") Long uid, @RequestParam("current") Integer current,
			HttpServletResponse response, HttpSession session) throws Exception {
		Artical artical = new Artical();
		artical.setUid(uid);
		artical.setIsPublish((byte) 1);
		if (uid != ((User) session.getAttribute("user")).getId()) {
			artical.setIsPublic((byte) 1);
		}

		PageInfo<Artical> pageInfo = articalService.getAppointedPageItems(current, 10, artical);

		JSONObject json = new JSONObject();
		json.put("articals", pageInfo.getList());
		json.put("total", pageInfo.getPages());
		response.setContentType("text/html;charset=utf-8");
		response.getWriter().write(json.toString());
	}

	/* 修改信息逻辑 */

	/**
	 * 获取指定id的用户信息
	 * 
	 * @param userInfo
	 * @return
	 */
	@RequestMapping(value = "/getAppointedUserInfo", method = RequestMethod.GET)
	public void getAppointedUserInfo(UserInfo userInfo, HttpSession session, HttpServletResponse response)
			throws Exception {
		JSONObject json = new JSONObject();
		User u = (User) session.getAttribute("user");
		if (!u.getId().equals(userInfo.getId())) {
			json.put("isMaster", "no");
		}
		json.put("userinfo", userInfoService.getAppointedItem(userInfo.getId()));
		json.put("nickname", userService.getAppointedItem(userInfo.getId()).getNickname());

		response.setContentType("text/html;charset=UTF-8");
		response.getWriter().append(json.toString());
	}

	/**
	 * 获取自己的用户信息
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/getOwnUserInfo", method = RequestMethod.GET)
	public void getOwnUserInfo(HttpSession session, HttpServletResponse response) throws Exception {
		User user = (User) session.getAttribute("user");
		UserInfo userInfo = userInfoService.getAppointedItem(user.getId());

		JSONObject json = new JSONObject();
		json.put("userinfo", userInfo);
		json.put("nickname", user.getNickname());
		response.setContentType("text/html;charset=UTF-8");
		response.getWriter().write(json.toString());
	}

	/**
	 * 修改指定id的用户信息
	 * 
	 * @param userInfo
	 * @return
	 */
	@RequestMapping(value = "/changeAppointedUserInfo")
	@ResponseBody
	public boolean changeAppointedUserInfo(@RequestBody String harvest, HttpSession session) throws Exception {
		JSONObject js = JSONObject.fromObject(harvest);
		String nickname = js.getString("nickname");
		String truename = js.getString("truename");
		int sex = js.getInt("sex");
		String temp_birthday = js.getString("birthday");
		Date birthday;
		if ("".equals(temp_birthday)) {
			birthday = null;
		} else {
			birthday = new SimpleDateFormat("yyyy-MM-dd").parse(temp_birthday);
		}
		String address = js.getString("address");
		Integer qq = js.getInt("qq");
		String description = js.getString("description");

		UserInfo userInfo = new UserInfo();
		userInfo.setId(((User) session.getAttribute("user")).getId());
		userInfo.setTruename(truename);
		userInfo.setSex((byte) sex);
		userInfo.setBirthday(birthday);
		userInfo.setAddress(address);
		userInfo.setQq(qq);
		userInfo.setDescription(description);
		User user = new User();
		user.setId(((User) session.getAttribute("user")).getId());
		user.setNickname(nickname);
		userInfo.setUser(user);

		if (userInfoService.changeAppointedItem(userInfo)) {
			((User) session.getAttribute("user")).setNickname(nickname);
			return true;
		}
		return false;
	}

}
