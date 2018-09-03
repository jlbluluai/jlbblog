package com.xyz.controller;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
import com.xyz.common.DateUtils;
import com.xyz.domain.Apply;
import com.xyz.domain.Artical;
import com.xyz.domain.ArticalCategory;
import com.xyz.domain.Collection;
import com.xyz.domain.Comment;
import com.xyz.domain.Dynamic;
import com.xyz.domain.File;
import com.xyz.domain.FollowKey;
import com.xyz.domain.Message;
import com.xyz.domain.User;
import com.xyz.domain.UserInfo;
import com.xyz.dto.BloggerDto;
import com.xyz.dto.PagesFeedback;
import com.xyz.dto.StringTemp;
import com.xyz.service.ApplyService;
import com.xyz.service.ArticalService;
import com.xyz.service.CollectionService;
import com.xyz.service.CommentService;
import com.xyz.service.DynamicService;
import com.xyz.service.FileService;
import com.xyz.service.FollowService;
import com.xyz.service.UserService;
import com.xyz.util.CompressUtils;
import com.xyz.util.FtpConnect;
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
	@Qualifier("dynamicService")
	private DynamicService dynamicService;

	@Autowired
	@Qualifier("commentService")
	private CommentService commentService;

	@Autowired
	@Qualifier("userService")
	private UserService userService;

	@Autowired
	@Qualifier("followService")
	private FollowService followService;

	@Autowired
	@Qualifier("collectionService")
	private CollectionService collectionService;

	@Autowired
	@Qualifier("applyService")
	private ApplyService applyService;

	@Autowired
	@Qualifier("fileService")
	private FileService fileService;

	// 统一设定数据
	private String contentType = "text/html;charset=UTF-8";
	private String f1 = "--------------";
	private String f2 = "--------------";

	/**
	 * 处理一篇博客前可能需要上传博客附件
	 * 
	 * @param file
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/uploadOneBlogFile", method = RequestMethod.POST)
	public Integer uploadOneBlogFile(@RequestParam("myFile") MultipartFile file, HttpServletRequest request)
			throws Exception {
		Integer reid = 0;
		logger.info(f1 + "开始处理上传一篇博客附件" + f2);

		User user = (User) request.getSession().getAttribute("user");
		String tempname = user.getId() + "" + Utils.createComplexId() + "";
		String filename = tempname + "." + Utils.getTheFileStyle(file.getOriginalFilename());
		if (!file.isEmpty()) {
			// 创建一个临时文件
			java.io.File tempFile = new java.io.File(filename);
			// 将文件写入临时文件
			file.transferTo(tempFile);
			// 压缩文件并将文件上传到ftp服务器
			FtpConnect.uploadOneFile(tempFile, "file");
			// 修改用户信息
			File record = new File();
			record.setUid(user.getId());
			record.setFilename(tempname);
			record.setFilestyle(Utils.getTheFileStyle(file.getOriginalFilename()));
			record.setCategory(1);
			record.setUploadtime(new Date());
			record.setFolder("file");

			File temp = fileService.saveOneFileAndGetId(record);
			if (temp.getId() != null) {
				reid = temp.getId();
			} else {
				reid = -1;
			}

			tempFile.delete();
		}

		logger.info(f1 + "结束处理上传一篇博客附件" + f2);
		return reid;
	}

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
		Integer fileId = js.getInt("file");
		Long id = 0L;
		if (!"null".equals(js.getString("id"))) {
			id = Long.parseLong(js.getString("id"));
		}
		String way = js.getString("way");

		if (id == 0L) {
			id = Utils.createComplexId();
		}

		Date now = new Date();
		User user = (User) request.getSession().getAttribute("user");

		Artical artical = new Artical();
		artical.setId(id);
		artical.setTitle(title);
		artical.setContent(content);
		artical.setContentRich(contentRich);
		artical.setUid(user.getId());
		artical.setIsPublic(isPublic);
		artical.setIsPublish(isPublish);
		artical.setReprint(reprint);
		if (fileId != 0) {
			artical.setFid(fileId);
		}

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
			artical.setIsExamine((byte) 0);
			artical.setIsNice((byte) 0);
			artical.setViewNum(0);
			artical.setCommentNum(0);
			flag = articalService.writeOneBlog(artical, articalCategory, file);

			if (flag && isPublic == 1 && isPublish == 1) {
				Dynamic dynamic = new Dynamic();
				dynamic.setAid(id);
				dynamic.setId(Utils.createComplexId());
				dynamic.setCreateTime(new Date());
				dynamic.setUid(user.getId());
				dynamic.setIsBlog((byte) 1);
				dynamicService.saveAppointedItem(dynamic);
			}

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
		Artical artical = articalService.getAppointedItem(id);
		int viewNum = artical.getViewNum();
		viewNum++;
		Artical artical2 = new Artical();
		artical2.setId(artical.getId());
		artical2.setViewNum(viewNum);
		articalService.changeAppointedItem(artical2);
		return artical;
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
			art.setContent(art.getContent()+"...");
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

	/**
	 * 获取评论人的信息
	 * 
	 * @return
	 */
	@RequestMapping(value = "/getCommenter", method = RequestMethod.GET)
	@ResponseBody
	public StringTemp getCommenter(HttpServletRequest request) {
		StringTemp stringTemp = new StringTemp();

		User user = (User) request.getSession().getAttribute("user");
		if (user == null) {
			return stringTemp;
		}
		String nickname = user.getNickname();
		stringTemp.setStr(nickname);

		return stringTemp;
	}

	/**
	 * 写一篇评论
	 * 
	 * @param aid
	 * @param content
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/writeOneComment", method = RequestMethod.POST)
	@ResponseBody
	public boolean writeOneComment(@RequestParam("aid") Long aid, @RequestParam("content") String content,
			HttpServletRequest request) {
		boolean flag = false;

		User user = (User) request.getSession().getAttribute("user");
		if (user == null) {
			return false;
		}

		Comment comment = new Comment();
		comment.setAid(aid);
		comment.setContent(content);
		comment.setUid(user.getId());
		comment.setCreateTime(new Date());

		Artical artical = articalService.getAppointedItem(aid);
		int commentNum = artical.getCommentNum();
		commentNum++;
		Artical artical2 = new Artical();
		artical2.setId(artical.getId());
		artical2.setCommentNum(commentNum);
		articalService.changeAppointedItem(artical2);

		flag = commentService.saveAppointedItem(comment);

		return flag;
	}

	/**
	 * 收藏一篇博客
	 * 
	 * @param request
	 * @param aid
	 * @return
	 */
	@RequestMapping(value = "/collectOneBlog", method = RequestMethod.POST)
	@ResponseBody
	public Integer collectOneBlog(HttpServletRequest request, @RequestParam("aid") Long aid,
			@RequestParam("uid") Long uid) {
		Integer flag = 0;

		User user = (User) request.getSession().getAttribute("user");

		if (user == null) {
			return 1;
		}

		if (user.getId().equals(uid)) {
			return 2;
		}

		Collection collection = new Collection();
		collection.setAid(aid);
		collection.setUid(user.getId());
		collection.setCreateTime(new Date());

		if (collectionService.saveAppointedItem(collection)) {
			flag = 3;
		}

		return flag;
	}

	/**
	 * 跳转编辑博客验明身份
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/verEditBlog", method = RequestMethod.POST)
	@ResponseBody
	public Integer verEditBlog(HttpServletRequest request, @RequestParam("uid") Long uid) {

		User user = (User) request.getSession().getAttribute("user");

		if (user == null) {
			return 1;
		}

		if (!user.getId().equals(uid)) {
			return 2;
		}

		return 3;
	}
	
	
	/**
	 * 右侧公告栏信息跳转验明登录
	 */
	@RequestMapping(value = "/verGoYuan", method = RequestMethod.GET)
	@ResponseBody
	public Integer verGoYuan(HttpServletRequest request) {

		User user = (User) request.getSession().getAttribute("user");

		if (user == null) {
			return 1;
		}

		return 0;
	}

	/**
	 * 博客申精提交
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/blogQuality", method = RequestMethod.POST)
	@ResponseBody
	public boolean blogQuality(HttpServletRequest request, @RequestParam("aid") Long aid,
			@RequestParam("reason") String reason) {

		User user = (User) request.getSession().getAttribute("user");
		
		Message message = new Message();
		message.setId(Utils.createComplexId());
		message.setCreateTime(new Date());
		message.setPid((long) 1);
		message.setRid(user.getId());
		message.setStatus((byte) 0);
		message.setTitle("博客申精提交成功致信");
		message.setContent("您好，您的博客申精已经提交成功，我们将尽快受理审批并给您答复。");

		Apply apply = new Apply();
		apply.setIsBlogger((byte) 1);
		apply.setCreateTime(new Date());
		apply.setReason(reason);
		apply.setStatus((byte) 0);
		apply.setUid(user.getId());
		apply.setAid(aid);
		apply.setMessage(message);

		if (applyService.saveAppointedItem(apply)) {
			return true;
		}

		return false;
	}

	/* 公共部分获取信息 */
	/**
	 * 根据文章id获取博主的相关信息
	 * 
	 * @param aid
	 * @return
	 */
	@RequestMapping(value = "/getTheBlogger", method = RequestMethod.GET)
	@ResponseBody
	public BloggerDto getTheBlogger(@RequestParam("aid") Long aid, @RequestParam("uid") Long uid) {
		BloggerDto bloggerDto = new BloggerDto();

		if (aid != null) {
			uid = articalService.getAppointedItem(aid).getUid();
		}
		User user = userService.getAppointedItem(uid);
		bloggerDto.setUid(uid);
		bloggerDto.setNickname(user.getNickname());

		try {
			bloggerDto.setMakeDay(DateUtils.dateFormatCST(user.getMakeDay().toString()));
		} catch (ParseException e) {
			e.printStackTrace();
		}

		bloggerDto.setBlogAge(DateUtils.getTheDiscrepantMonths(user.getMakeDay(), new Date()));

		FollowKey followKey = new FollowKey();
		followKey.setFid(uid);
		int fans = followService.getFanFollowCount(followKey);
		bloggerDto.setFans(fans);

		followKey = new FollowKey();
		followKey.setMid(uid);
		int follows = followService.getFanFollowCount(followKey);
		bloggerDto.setFollows(follows);

		return bloggerDto;
	}

	/**
	 * 获取博客后管的统一信息
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/getBehindBlogger", method = RequestMethod.GET)
	@ResponseBody
	public BloggerDto getBehindBlogger(HttpServletRequest request) {
		BloggerDto bloggerDto = new BloggerDto();

		User user = (User) request.getSession().getAttribute("user");

		bloggerDto.setUid(user.getId());
		bloggerDto.setNickname(user.getNickname());

		return bloggerDto;
	}

	/**
	 * 添加关注
	 * 
	 * @param uid
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/addFollow", method = RequestMethod.POST)
	@ResponseBody
	public Integer addFollow(@RequestParam("uid") Long uid, HttpServletRequest request) {
		int i = 0;

		User user = (User) request.getSession().getAttribute("user");
		if (user == null) {
			return 1;
		}

		if (uid.equals(user.getId())) {
			return 2;
		}

		FollowKey followKey = new FollowKey();
		followKey.setMid(user.getId());
		followKey.setFid(uid);

		if (followService.saveOneFollow(followKey)) {
			return 3;
		}

		return i;
	}

	/* 博客后管逻辑 */
	/**
	 * 获取博客后管分类相关的博客列表
	 * 
	 * @param cid
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/getTheCategoryBloggerBlogs", method = RequestMethod.GET)
	@ResponseBody
	public List<Artical> getTheCategoryBloggerBlogs(@RequestParam("category") Integer cid, HttpServletRequest request) {
		List<Artical> articals = new ArrayList<Artical>();

		User user = (User) request.getSession().getAttribute("user");
		Long uid = user.getId();

		Artical artical = new Artical();
		artical.setUid(uid);
		artical.setIsPublish((byte) 1);
		if (cid != null) {
			artical.setCategory(cid);
		}
		artical.setSort(1);
		PageInfo<Artical> pageInfo = articalService.getAppointedPageItems(1, Integer.MAX_VALUE, artical);

		articals = pageInfo.getList();

		return articals;
	}

	/**
	 * 获取草稿箱博客列表
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/getDraftBlogs", method = RequestMethod.GET)
	@ResponseBody
	public List<Artical> getDraftBlogs(HttpServletRequest request) {
		List<Artical> articals = new ArrayList<Artical>();

		User user = (User) request.getSession().getAttribute("user");
		Long uid = user.getId();

		Artical artical = new Artical();
		artical.setUid(uid);
		artical.setIsPublish((byte) 0);
		artical.setSort(1);
		PageInfo<Artical> pageInfo = articalService.getAppointedPageItems(1, Integer.MAX_VALUE, artical);

		articals = pageInfo.getList();

		return articals;
	}

	/**
	 * 删除一篇博客
	 * 
	 * @param aid
	 * @param uid
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/cutOneBlog", method = RequestMethod.POST)
	@ResponseBody
	public Integer cutOneBlog(@RequestParam("aid") Long aid, @RequestParam("uid") Long uid,
			HttpServletRequest request) {

		User user = (User) request.getSession().getAttribute("user");

		if (user == null || !user.getId().equals(uid)) {
			return 1;
		}

		if (articalService.cutAppointedItem(aid)) {
			return 2;
		}

		return 3;
	}

	/* 相册 */
	/**
	 * 上传相册图片
	 * 
	 * @param request
	 * @param file
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/uploadPhoto")
	public void uploadPhoto(HttpServletRequest request, @RequestParam("myfile") MultipartFile file,
			HttpServletResponse response) throws Exception {
		logger.info("上传相册图片开始");
		User user = (User) request.getSession().getAttribute("user");
		String name = Utils.createComplexId() + "photo";
		String fileName = name + "." + Utils.getTheFileStyle(file.getOriginalFilename());
		if (!file.isEmpty()) {
			// 创建一个临时文件
			java.io.File tempFile = new java.io.File(fileName);
			// 将文件写入临时文件
			file.transferTo(tempFile);
			// 压缩文件并将文件上传到ftp服务器
			FtpConnect.uploadOneFile(CompressUtils.compressPic(tempFile, fileName, 500, 340, 1), "headpic");
			// 添加相册信息
			File f = new File();
			f.setFilename(name);
			f.setFilestyle(Utils.getTheFileStyle(file.getOriginalFilename()));
			f.setCategory(2);
			f.setFolder("photos");
			f.setUid(user.getId());
			f.setUploadtime(new Date());

			if (fileService.saveAppointedItem(f)) {
				logger.info("上传相册图片完成");
				JSONObject json = new JSONObject();
				json.put("msg", "上传完成");
				response.setContentType(contentType);
				response.getWriter().write(json.toString());
			} else {
				logger.info("上传相册图片失败");
				JSONObject json = new JSONObject();
				json.put("msg", "上传失败");
				response.setContentType(contentType);
				response.getWriter().write(json.toString());
			}

			tempFile.delete();
		} else {
			logger.error("图片接收失败");
			JSONObject json = new JSONObject();
			json.put("msg", "图片接收失败");
			response.setContentType(contentType);
			response.getWriter().write(json.toString());
		}
	}

	/**
	 * 获取相册图片
	 * 
	 * @return
	 */
	@RequestMapping(value = "/getAllPhotos", method = RequestMethod.GET)
	@ResponseBody
	public List<File> getAllPhotos(HttpSession session) {
		List<File> list = new ArrayList<>();
		logger.info("获取相册图片开始");

		User user = (User) session.getAttribute("user");

		File file = new File();
		file.setUid(user.getId());
		file.setCategory(2);

		list = fileService.getAll1(file);

		logger.info("获取相册图片结束");
		return list;
	}

	/* 文件 */
	/**
	 * 获取用户所有文件
	 * 
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/getAllFiles", method = RequestMethod.GET)
	@ResponseBody
	public List<File> getAllFiles(HttpSession session) {
		List<File> list = new ArrayList<>();
		logger.info("获取文件开始");

		User user = (User) session.getAttribute("user");

		File file = new File();
		file.setUid(user.getId());
		file.setCategory(1);

		list = fileService.getAll1(file);

		logger.info("获取文件结束");
		return list;
	}
}
