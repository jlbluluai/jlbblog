package com.xyz.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
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
import com.xyz.domain.ArticalCategory;
import com.xyz.domain.Comment;
import com.xyz.domain.File;
import com.xyz.domain.User;
import com.xyz.dto.PagesFeedback;
import com.xyz.service.ArticalService;
import com.xyz.service.CommentService;
import com.xyz.util.FtpUtils;
import com.xyz.util.Utils;

import net.sf.json.JSONObject;

@Controller
public class BlogManage {

	private Logger logger = Logger.getLogger(BlogManage.class);

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

	/**
	 * 处理一篇博客，新增，修改
	 * 
	 * @param request
	 * @param title
	 * @param reprint
	 * @param content
	 * @param contentRich
	 * @param myfile
	 * @param isPublic
	 * @param isPublish
	 * @param way
	 * @param category
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/handleOneBlog", method = RequestMethod.POST)
	public boolean handleOneBlog(HttpServletRequest request, @RequestBody String harvest) {
		boolean flag = false;
		logger.info(f1 + "开始处理一篇博客" + f2);

		JSONObject js = JSONObject.fromObject(harvest);

		String title = js.getString("title");
		String reprint = js.getString("reprint");
		String content = js.getString("content");
		String contentRich = js.getString("contentRich");
		Byte isPublic = (byte) js.getInt("isPublic");
		Byte isPublish = (byte) js.getInt("isPublish");
		Integer category = Integer.parseInt(js.getString("category"));
		Long id = 0L;
		if (js.get("id") == null) {
			id = Long.parseLong(js.getString("id"));
		}
		String way = js.getString("way");

		if (id == 0L) {
			id = Utils.createComplexId();
		}

		Date now = new Date();
		User user = (User) request.getSession().getAttribute("user");
		user = new User();
		user.setId(7L);

		Artical artical = new Artical();
		artical.setId(id);
		artical.setTitle(title);
		artical.setContent(content);
		artical.setContentRich(contentRich);
		artical.setUid(user.getId());
		artical.setIsPublic(isPublic);
		artical.setIsPublish(isPublish);
		artical.setIsExamine((byte) 0);
		artical.setIsNice((byte) 0);
		artical.setReprint(reprint);
		artical.setViewNum(0);
		artical.setCommentNum(0);
		artical.setLeaveTime(now);

		File file = new File();
		// if (!"".equals(myFile)) {
		// String[] str = myFile.split("\\.");
		// file.setFilename(str[0]);
		// file.setFilestyle(str[1]);
		// file.setFolder("file");
		// file.setUid(user.getId());
		// file.setCategory(1);// 普通上传
		// file.setUploadtime(now);
		// }

		ArticalCategory articalCategory = new ArticalCategory();
		articalCategory.setId(category);

		if ("00".equals(way)) {// 新增
			flag = articalService.writeOneBlog(artical, articalCategory, file);
		} else if ("01".equals(way)) {// 修改
			flag = articalService.modifyOneBlog(artical, articalCategory, file);
		}

		logger.info(f1 + "结束处理一篇博客" + f2);
		return flag;
	}

	@RequestMapping(value = "/uploadFile")
	@ResponseBody
	public boolean uploadFile(HttpServletRequest request, @RequestParam("myFile") MultipartFile myfile) {
		boolean flag = false;

		Long id = ((User) request.getAttribute("user")).getId();

		String filename = id + myfile.getOriginalFilename();
		if (!myfile.isEmpty()) {// 上传文件不为空，先上传文件
			// 创建一个临时文件
			java.io.File tempFile = new java.io.File(filename);
			try {
				// 将文件写入临时文件
				myfile.transferTo(tempFile);
				long size = tempFile.length() / 1024;
				if (size <= 1024 * 2) {
					// 将文件上传到ftp服务器
					FtpUtils.uploadOneFile(tempFile, "file");
				} else {
					logger.info("文件超过限制");
				}
			} catch (Exception e) {
				logger.error("文件上传失败");
				e.printStackTrace();
			}
		}

		return flag;
	}

	@RequestMapping(value = "/writeOneBlog", method = RequestMethod.POST)
	@ResponseBody
	public boolean writeOneBlog(HttpServletRequest request, @RequestBody String harvest) {
		boolean flag = false;

		return flag;
	}

	/**
	 * 根据博客id获取博客内容
	 * 
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getOneArtical", method = RequestMethod.GET)
	public Artical getOneArtical(@RequestParam("aid") Long id) {
		return articalService.getAppointedItem(id);
	}

	/**
	 * 博客主页的文章列表获取
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getBlogPortalArticals", method = RequestMethod.GET)
	public PagesFeedback getBlogPortalArticals(HttpServletRequest request, @RequestParam("uid") Long uid,
			@RequestParam("current") Integer current) {
		PagesFeedback feedback = new PagesFeedback();
		User user = (User) request.getSession().getAttribute("user");

		Artical artical = new Artical();
		artical.setIsPublish((byte) 1);
		if (!(user != null && (user.getId()).equals(uid))) {// 不是本人只能访问公开博客
			artical.setIsPublic((byte) 1);
		}
		artical.setSort(1);
		artical.setUid(uid);

		PageInfo<Artical> pageInfo = new PageInfo<>();
		pageInfo = articalService.getAppointedPageItems(current, 16, artical);

		List<Object> list = new ArrayList<Object>();
		for (Artical art : pageInfo.getList()) {
			list.add(art);
		}
		feedback.setoList(list);
		feedback.setTotalPages(pageInfo.getPages());

		return feedback;
	}

	/**
	 * 获取指定博客的评论
	 * 
	 * @param harvest
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/getBlogComments", method = RequestMethod.GET)
	@ResponseBody
	public PagesFeedback getBlogComments(@RequestParam("current") Integer current, @RequestParam("aid") Long aid) {
		logger.info(f1 + "获取博客评论开始" + f2);
		PagesFeedback feedback = new PagesFeedback();
		Comment comment = new Comment();
		comment.setAid(aid);

		PageInfo<Comment> pageInfo = commentService.getAppointedPageItems(current, 8, comment);

		List<Object> list = new ArrayList<Object>();
		int count = 1;
		int floor = 1;
		for (Comment comm : pageInfo.getList()) {
			floor = (current - 1) * 8 + count;
			comm.setFloor(floor);
			list.add(comm);
			count++;
		}
		feedback.setoList(list);
		feedback.setTotalPages(pageInfo.getPages());
		logger.info(f1 + "获取博客评论结束" + f2);
		return feedback;
	}

}
