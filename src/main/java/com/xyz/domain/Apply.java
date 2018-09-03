package com.xyz.domain;

import java.io.Serializable;
import java.util.Date;

public class Apply implements Serializable {
	private Long id;

	private Long uid;

	private Byte isBlogger;

	private Long aid;

	private Byte status;

	private Date createTime;

	private String reason;

	private static final long serialVersionUID = 1L;

	/* 视图属性 */
	private Message message;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getUid() {
		return uid;
	}

	public void setUid(Long uid) {
		this.uid = uid;
	}

	public Byte getIsBlogger() {
		return isBlogger;
	}

	public void setIsBlogger(Byte isBlogger) {
		this.isBlogger = isBlogger;
	}

	public Long getAid() {
		return aid;
	}

	public void setAid(Long aid) {
		this.aid = aid;
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

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason == null ? null : reason.trim();
	}

	public Message getMessage() {
		return message;
	}

	public void setMessage(Message message) {
		this.message = message;
	}

}