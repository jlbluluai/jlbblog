package com.xyz.controller;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.xyz.domain.Artical;
import com.xyz.domain.ArticalCategory;
import com.xyz.domain.File;
import com.xyz.domain.User;
import com.xyz.service.ArticalService;
import com.xyz.util.FtpUtils;
import com.xyz.util.Utils;

@Controller
public class BlogManage {

	private Logger logger = Logger.getLogger(BlogManage.class);

	@Autowired
	@Qualifier("articalService")
	private ArticalService articalService;

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
	@RequestMapping(value = "/handleOneBlog", method = RequestMethod.POST)
	public boolean handleOneBlog(HttpServletRequest request, @RequestParam("title") String title,
			@RequestParam("reprint") String reprint, @RequestParam("content") String content,
			@RequestParam("contentRich") String contentRich, @RequestParam("myFile") MultipartFile myfile,
			@RequestParam("isPublic") String isPublic, @RequestParam("isPublish") String isPublish,
			@RequestParam("way") String way, @RequestParam("category") String category, @RequestParam("id") long id) {
		boolean flag = false;
		logger.info(f1 + "开始处理一篇博客" + f2);

		if ("".equals(id)) {
			id = Utils.createComplexId();
		}

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

		Date now = new Date();
		User user = (User) request.getSession().getAttribute("user");

		Artical artical = new Artical();
		artical.setId(id);
		artical.setTitle(title);
		artical.setContent(content);
		artical.setContentRich(contentRich);
		artical.setUid(user.getId());
		artical.setIsPublic((byte) Integer.parseInt(isPublic));
		artical.setIsPublish((byte) Integer.parseInt(isPublish));
		artical.setIsExamine((byte) 0);
		artical.setIsNice((byte) 0);
		artical.setReprint(reprint);
		artical.setViewNum(0);
		artical.setCommentNum(0);
		artical.setLeaveTime(now);

		File file = new File();
		if (!myfile.isEmpty()) {
			String[] str = filename.split("\\.");
			file.setFilename(str[0]);
			file.setFilestyle(str[1]);
			file.setFolder("file");
			file.setUid(user.getId());
			file.setCategory(1);// 普通上传
			file.setUploadtime(now);
		}

		ArticalCategory articalCategory = new ArticalCategory();
		articalCategory.setId(Integer.parseInt(category));

		if ("00".equals(way)) {// 新增
			flag = articalService.writeOneBlog(artical, articalCategory, file);
		} else if ("01".equals(way)) {// 修改
			flag = articalService.modifyOneBlog(artical, articalCategory, file);
		}

		logger.info(f1 + "结束处理一篇博客" + f2);
		return flag;
	}

}
