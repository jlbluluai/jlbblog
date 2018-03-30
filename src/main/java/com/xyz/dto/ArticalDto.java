package com.xyz.dto;

import java.io.File;

import org.springframework.web.multipart.MultipartFile;

public class ArticalDto {

	/**
	 * 标题
	 */
	private String title;

	/**
	 * 转载
	 */
	private String reprint;

	/**
	 * 内容
	 */
	private String content;

	/**
	 * 富文本
	 */
	private String contentRich;
	
	/**
	 * 是否公开
	 */
	private Byte isPublic;
	
	/**
	 * 是否发布
	 */
	private Byte isPublish;

	/**
	 * 文件名
	 */
	private String filename;
	
	
	
	

}
