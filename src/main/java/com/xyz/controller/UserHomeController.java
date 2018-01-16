package com.xyz.controller;

import java.io.File;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.github.pagehelper.PageInfo;
import com.xyz.domain.Artical;
import com.xyz.domain.Comment;
import com.xyz.domain.Dynamic;
import com.xyz.domain.FollowKey;
import com.xyz.domain.User;
import com.xyz.domain.UserInfo;
import com.xyz.dto.PagesFeedback;
import com.xyz.dto.UserHome;
import com.xyz.service.ArticalService;
import com.xyz.service.CommentService;
import com.xyz.service.DynamicService;
import com.xyz.service.FollowService;
import com.xyz.service.UserInfoService;
import com.xyz.service.UserService;
import com.xyz.util.FtpConnect;
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

	@Autowired
	@Qualifier("commentService")
	private CommentService commentService;

	@Autowired
	@Qualifier("followService")
	private FollowService followService;

	// 统一设定数据
	private String contentType = "text/html;charset=UTF-8";
	private String f1 = "--------------";
	private String f2 = "--------------";

	// 日志
	private Logger log = Logger.getLogger(UserHomeController.class);

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
		UserInfo info = userInfoService.getAppointedItem(userInfo.getId());

		String prop = FtpConnect.class.getClassLoader().getResource("/").getPath()
				+ "properties/ftp-connect.properties";
		prop = URLDecoder.decode(prop);
		Properties properties = Utils.getProperties(prop);
		String headpic = "http://" + properties.getProperty("url") + ":" + properties.getProperty("nginxPort") + "/"
				+ info.getHeadpic();
		info.setHeadpic(headpic);
		json.put("userinfo", info);
		json.put("nickname", userService.getAppointedItem(userInfo.getId()).getNickname());

		response.setContentType("text/html;charset=UTF-8");
		response.getWriter().append(json.toString());
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

	/* 文章逻辑 */
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

	/* 关注与粉丝逻辑 */
	/**
	 * 获取关注与粉丝
	 * 
	 * @param uid
	 * @param module
	 * @return
	 */
	@RequestMapping(value = "/getUserFollows", method = RequestMethod.GET)
	@ResponseBody
	public List<FollowKey> getUserFollows(@RequestParam("uid") Long uid, @RequestParam("module") Integer module) {
		log.info(f1 + "获取用户关注与粉丝开始" + f2);
		FollowKey follow = new FollowKey();
		if (module == 1) {
			follow.setMid(uid);
		} else if (module == 2) {
			follow.setFid(uid);
		}
		List<FollowKey> list = followService.getFollows(follow);
		log.info(f1 + "获取用户关注与粉丝结束" + f2);
		return list;
	}

	/* 评论逻辑 */
	/**
	 * 获取用户自己指定页的评论
	 * 
	 * @param harvest
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/getUserComments", method = RequestMethod.GET)
	@ResponseBody
	public PagesFeedback getUserComments(@RequestParam("current") Integer current, HttpSession session)
			throws Exception {
		log.info(f1 + "获取用户评论开始" + f2);
		Long uid = ((User) session.getAttribute("user")).getId();
		Comment comment = new Comment();
		comment.setUid(uid);

		PageInfo<Comment> pageInfo = commentService.getAppointedPageItems(current, 8, comment);

		List<Object> list = new ArrayList<Object>();
		for (Comment comm : pageInfo.getList()) {
			list.add(comm);
		}
		PagesFeedback feedback = new PagesFeedback();
		feedback.setoList(list);
		feedback.setTotalPages(pageInfo.getPages());
		log.info(f1 + "获取用户评论结束" + f2);
		return feedback;
	}

	/* 修改信息逻辑 */
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

	/**
	 * 上传头像
	 * 
	 * @param request
	 * @param file
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/uploadHeadpic")
	public void uploadHeadpic(HttpServletRequest request, @RequestParam("myfile") MultipartFile file,
			HttpServletResponse response) throws Exception {
		log.info("头像上传开始");
		User user = (User) request.getSession().getAttribute("user");
		String headname = user.getId() + "head." + Utils.getTheFileStyle(file.getOriginalFilename());
		if (!file.isEmpty()) {
			// 创建一个临时文件
			File tempFile = new File(headname);
			// 将文件写入临时文件
			file.transferTo(tempFile);
			// 将文件上传到ftp服务器
			FtpConnect.uploadOneFile(tempFile, "headpic");
			// 修改用户信息
			UserInfo userInfo = new UserInfo();
			userInfo.setId(user.getId());
			userInfo.setHeadpic(headname);
			userInfoService.changeAppointedItem(userInfo);
			log.info("头像上传完成");
			JSONObject json = new JSONObject();
			json.put("msg", "上传完成");
			response.setContentType(contentType);
			response.getWriter().write(json.toString());
		} else {
			log.error("文件接收失败");
			JSONObject json = new JSONObject();
			json.put("msg", "文件接收失败");
			response.setContentType(contentType);
			response.getWriter().write(json.toString());
		}
	}

}
