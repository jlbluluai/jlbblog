package com.xyz.dto;

import java.io.Serializable;

public class ArticalSolrDto implements Serializable {

	/**
	 * 博客id
	 */
	private Long id;

	/**
	 * 博客标题
	 */
	private String title;

	/**
	 * 博客内容
	 */
	private String content;

	/**
	 * 发布时间
	 */
	private String pubTime;

	/**
	 * 阅览量
	 */
	private String viewNum;

	/**
	 * 评论量
	 */
	private String commentNum;

	/**
	 * 作者id
	 */
	private Long uid;

	/**
	 * 作者昵称
	 */
	private String nickname;

	/**
	 * 博客id
	 * 
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * 博客id
	 * 
	 * @param id
	 *            the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * 博客标题
	 * 
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * 博客标题
	 * 
	 * @param title
	 *            the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * 博客内容
	 * 
	 * @return the content
	 */
	public String getContent() {
		return content;
	}

	/**
	 * 博客内容
	 * 
	 * @param content
	 *            the content to set
	 */
	public void setContent(String content) {
		this.content = content;
	}

	/**
	 * 发布时间
	 * 
	 * @return the pubTime
	 */
	public String getPubTime() {
		return pubTime;
	}

	/**
	 * 发布时间
	 * 
	 * @param pubTime
	 *            the pubTime to set
	 */
	public void setPubTime(String pubTime) {
		this.pubTime = pubTime;
	}

	/**
	 * 阅览量
	 * 
	 * @return the viewNum
	 */
	public String getViewNum() {
		return viewNum;
	}

	/**
	 * 阅览量
	 * 
	 * @param viewNum
	 *            the viewNum to set
	 */
	public void setViewNum(String viewNum) {
		this.viewNum = viewNum;
	}

	/**
	 * 评论量
	 * 
	 * @return the commentNum
	 */
	public String getCommentNum() {
		return commentNum;
	}

	/**
	 * 评论量
	 * 
	 * @param commentNum
	 *            the commentNum to set
	 */
	public void setCommentNum(String commentNum) {
		this.commentNum = commentNum;
	}

	/**
	 * 作者id
	 * 
	 * @return the uid
	 */
	public Long getUid() {
		return uid;
	}

	/**
	 * 作者id
	 * 
	 * @param uid
	 *            the uid to set
	 */
	public void setUid(Long uid) {
		this.uid = uid;
	}

	/**
	 * 作者昵称
	 * 
	 * @return the nickname
	 */
	public String getNickname() {
		return nickname;
	}

	/**
	 * 作者昵称
	 * 
	 * @param nickname
	 *            the nickname to set
	 */
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "ArticalSolrDto [id=" + id + ", title=" + title + ", content=" + content + ", pubTime=" + pubTime
				+ ", viewNum=" + viewNum + ", commentNum=" + commentNum + ", uid=" + uid + ", nickname=" + nickname
				+ "]";
	}

}
