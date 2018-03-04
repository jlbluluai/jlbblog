package com.xyz.controller;

import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageInfo;
import com.xyz.domain.Artical;
import com.xyz.domain.ArticalCategory;
import com.xyz.domain.RecommendCategory;
import com.xyz.domain.Suggestion;
import com.xyz.domain.User;
import com.xyz.domain.UserInfo;
import com.xyz.domain.Version;
import com.xyz.dto.PagesFeedback;
import com.xyz.dto.PortalStatistic;
import com.xyz.service.ArticalCategoryService;
import com.xyz.service.ArticalService;
import com.xyz.service.CommentService;
import com.xyz.service.RecommendCategoryService;
import com.xyz.service.SuggestionService;
import com.xyz.service.UserService;
import com.xyz.service.VersionService;
import com.xyz.util.FtpConnect;
import com.xyz.util.Utils;

@Controller
public class PortalController {

	@Autowired
	@Qualifier("articalCategoryService")
	private ArticalCategoryService articalCategoryService;

	@Autowired
	@Qualifier("userService")
	private UserService userService;

	@Autowired
	@Qualifier("articalService")
	private ArticalService articalService;

	@Autowired
	@Qualifier("commentService")
	private CommentService commentService;

	@Autowired
	@Qualifier("recommendCategoryService")
	private RecommendCategoryService recommendCategoryService;

	@Autowired
	@Qualifier("suggestionService")
	private SuggestionService suggestionService;
	
	@Autowired
	@Qualifier("versionService")
	private VersionService versionService;

	// 统一设定数据
	private String contentType = "text/html;charset=UTF-8";
	private String f1 = "--------------";
	private String f2 = "--------------";

	// 日志
	private Logger log = Logger.getLogger(PortalController.class);

	/* 主页逻辑 */
	/**
	 * 获取文章分类
	 * 
	 * @return
	 */
	@RequestMapping(value = "/getCategorys", method = RequestMethod.GET)
	@ResponseBody
	public List<ArticalCategory> getCategorys() {
		List<ArticalCategory> list = new ArrayList<>();
		log.info(f1 + "获取文章分类开始" + f2);
		list = articalCategoryService.getAllCategorys();
		log.info(f1 + "获取文章分类结束" + f2);
		return list;
	}

	/**
	 * 获取门户统计信息
	 * 
	 * @return
	 */
	@RequestMapping(value = "/getPortalStatistics", method = RequestMethod.GET)
	@ResponseBody
	public PortalStatistic getPortalStatistics() {
		PortalStatistic portalStatistic = new PortalStatistic();
		log.info(f1 + "获取门户统计信息开始" + f2);
		User user = new User();
		user.setIid(2);
		int temp = userService.getUserCount(user);
		user.setIid(3);
		int bloggerCount = userService.getUserCount(user);
		int articalCount = articalService.getCount();
		int commentCount = commentService.getCount();
		portalStatistic.setUserCount(new Long((long) (bloggerCount + temp)));
		portalStatistic.setBloggerCount(new Long((long) bloggerCount));
		portalStatistic.setBlogCount(new Long((long) articalCount));
		portalStatistic.setCommentCount(new Long((long) commentCount));
		log.info(f1 + "获取门户统计信息结束" + f2);
		return portalStatistic;
	}

	/**
	 * 获取推荐信息
	 * 
	 * @return
	 */
	@RequestMapping(value = "/getRecommends", method = RequestMethod.GET)
	@ResponseBody
	public List<RecommendCategory> getRecommends() {
		List<RecommendCategory> list = new ArrayList<>();
		log.info(f1 + "获取推荐开始" + f2);
		list = recommendCategoryService.getAllRecommends();
		log.info(f1 + "获取推荐结束" + f2);
		return list;
	}

	/**
	 * 根据相关条件获取主页相关文章 style：0首页，1精选，2关注
	 * 
	 * @param current
	 * @param category
	 * @param style
	 * @return
	 */
	@RequestMapping(value = "/getMainArticals", method = RequestMethod.GET)
	@ResponseBody
	public PagesFeedback getMainArticals(@RequestParam("current") Integer current,
			@RequestParam("category") Integer category, @RequestParam("style") Integer style, HttpSession session) {
		PagesFeedback feedback = new PagesFeedback();
		log.info(f1 + "获取主页文章开始" + f2);
		System.out.println(current);
		System.out.println(category);
		System.out.println(style);
		Artical artical = new Artical();
		artical.setIsPublish((byte) 1);
		if (category != 0) {
			artical.setCategory(category);
		}
		if (style == 0) {

		} else if (style == 1) {
			artical.setIsNice((byte) 1);
		} else if (style == 2) {

		} else {

		}
		artical.setSort(1);
		User user = (User) session.getAttribute("user");
		if (user == null) {
			user = new User();
			user.setId(0L);
		}
		artical.setUser(user);

		String prop = FtpConnect.class.getClassLoader().getResource("/").getPath()
				+ "properties/ftp-connect.properties";
		prop = URLDecoder.decode(prop);
		Properties properties = Utils.getProperties(prop);
		PageInfo<Artical> pageInfo = new PageInfo<>();
		if (category != 0) {
			pageInfo = articalService.getAppointedPageItems(current, 8, artical);
		} else {
			pageInfo = articalService.getAppointedPageItems(current, 16, artical);
		}
		List<Object> list = new ArrayList<Object>();
		for (Artical art : pageInfo.getList()) {
			UserInfo info = art.getUserInfo();
			String headpic = "http://" + properties.getProperty("url") + ":" + properties.getProperty("nginxPort") + "/"
					+ info.getHeadpic();
			info.setHeadpic(headpic);
			art.setUserInfo(info);
			list.add(art);
		}
		feedback.setoList(list);
		feedback.setTotalPages(pageInfo.getPages());
		log.info(f1 + "获取主页文章结束" + f2);
		return feedback;
	}

	/**
	 * 获取主页的登录状态
	 * 
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/getTheLoginStatus", method = RequestMethod.GET)
	@ResponseBody
	public User getTheLoginStatus(HttpSession session) {
		User user = new User();
		log.info(f1 + "获取主页登录状态开始" + f2);
		user = (User) session.getAttribute("user");
		log.info(f1 + "获取主页登录状态结束" + f2);
		return user;
	}

	/**
	 * 登出
	 * 
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/logOut", method = RequestMethod.GET)
	@ResponseBody
	public boolean logOut(HttpSession session) {
		boolean flag = true;
		log.info(f1 + "登出开始" + f2);
		session.invalidate();
		log.info(f1 + "登出技结束" + f2);
		return flag;
	}

	/**
	 * 相关需要获取用户id的跳转
	 * 
	 * @param session
	 * @param address
	 * @return
	 */
	@RequestMapping(value = "/jumpOthers", method = RequestMethod.GET)
	@ResponseBody
	public Long jumpOthers(HttpSession session) {
		Long uid = 0L;
		log.info(f1 + "相关需要获取用户id的跳转开始" + f2);
		User user = (User) session.getAttribute("user");
		if (user != null) {
			uid = user.getId();
		}
		log.info(f1 + "相关需要获取用户id的跳转结束" + f2);
		return uid;
	}

	/* 意见箱逻辑 */

	/**
	 * 写入一条建议
	 * 
	 * @param content
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/writeOneSuggestion", method = RequestMethod.POST)
	@ResponseBody
	public Boolean writeOneSuggestion(@RequestParam("content") String content, HttpSession session) {
		Boolean flag = false;
		log.info(f1 + "提交建议开始" + f2);
		System.out.println(content);
		Suggestion suggestion = new Suggestion();
		User user = (User) session.getAttribute("user");
		if (user != null) {
			suggestion.setUid(user.getId());
		} else {
			return false;
		}
		suggestion.setContent(content);
		suggestion.setStatus((byte) 0);
		suggestion.setLeaveTime(new Date());

		flag = suggestionService.saveAppointedItem(suggestion);
		log.info(f1 + "提交建议结束" + f2);
		return flag;
	}

	/**
	 * 获取所有的版本信息
	 * 
	 * @return
	 */
	@RequestMapping(value = "/getAllVersions", method = RequestMethod.GET)
	@ResponseBody
	public List<Version> getAllVersions() {
		List<Version> list = new ArrayList<>();
		log.info(f1 + "获取版本信息开始" + f2);
		list = versionService.getAllVersions();
		log.info(f1 + "获取版本信息结束" + f2);
		return list;
	}
}
