package com.xyz.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

public class Artical implements Serializable {
	private Long id;

	private String title;

	private Long uid;

	private Byte isPublic;

	private Byte isPublish;

	private Byte isNice;

	private Byte isExamine;

	private Integer viewNum;

	private Integer commentNum;

	private Date leaveTime;

	private String content;

	private static final long serialVersionUID = 1L;

	/* 视图属性 */
	private User user;
	private Set<ArticalCategory> articalCategorys;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title == null ? null : title.trim();
	}

	public Long getUid() {
		return uid;
	}

	public void setUid(Long uid) {
		this.uid = uid;
	}

	public Byte getIsPublic() {
		return isPublic;
	}

	public void setIsPublic(Byte isPublic) {
		this.isPublic = isPublic;
	}

	public Byte getIsPublish() {
		return isPublish;
	}

	public void setIsPublish(Byte isPublish) {
		this.isPublish = isPublish;
	}

	public Byte getIsNice() {
		return isNice;
	}

	public void setIsNice(Byte isNice) {
		this.isNice = isNice;
	}

	public Byte getIsExamine() {
		return isExamine;
	}

	public void setIsExamine(Byte isExamine) {
		this.isExamine = isExamine;
	}

	public Integer getViewNum() {
		return viewNum;
	}

	public void setViewNum(Integer viewNum) {
		this.viewNum = viewNum;
	}

	public Integer getCommentNum() {
		return commentNum;
	}

	public void setCommentNum(Integer commentNum) {
		this.commentNum = commentNum;
	}

	public Date getLeaveTime() {
		return leaveTime;
	}

	public void setLeaveTime(Date leaveTime) {
		this.leaveTime = leaveTime;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content == null ? null : content.trim();
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}


	public Set<ArticalCategory> getArticalCategorys() {
		return articalCategorys;
	}

	public void setArticalCategorys(Set<ArticalCategory> articalCategorys) {
		this.articalCategorys = articalCategorys;
	}


}