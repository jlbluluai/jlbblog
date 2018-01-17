package com.xyz.domain;

import java.io.Serializable;
import java.util.Date;

public class Message implements Serializable {
	private Long id;

	private String title;

	private Long pid;

	private Long rid;

	private Byte status;

	private Date createTime;

	private String content;

	private static final long serialVersionUID = 1L;

	/* 视图属性 */
	private User puser;
	private User ruser;

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

	public Long getPid() {
		return pid;
	}

	public void setPid(Long pid) {
		this.pid = pid;
	}

	public Long getRid() {
		return rid;
	}

	public void setRid(Long rid) {
		this.rid = rid;
	}

	public Byte getStatus() {
		return status;
	}

	public void setStatus(Byte status) {
		this.status = status;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content == null ? null : content.trim();
	}

	public User getPuser() {
		return puser;
	}

	public void setPuser(User puser) {
		this.puser = puser;
	}

	public User getRuser() {
		return ruser;
	}

	public void setRuser(User ruser) {
		this.ruser = ruser;
	}

}