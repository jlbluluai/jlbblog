package com.xyz.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xyz.domain.ArticalCategory;
import com.xyz.domain.User;
import com.xyz.dto.PortalStatistic;
import com.xyz.service.ArticalCategoryService;
import com.xyz.service.ArticalService;
import com.xyz.service.CommentService;
import com.xyz.service.UserService;

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

}
