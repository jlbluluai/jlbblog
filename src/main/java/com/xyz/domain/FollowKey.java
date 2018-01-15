package com.xyz.domain;

import java.io.Serializable;

public class FollowKey implements Serializable {
	private Long mid;

	private Long fid;

	private static final long serialVersionUID = 1L;

	/* 视图属性 */
	private User user;

	public Long getMid() {
		return mid;
	}

	public void setMid(Long mid) {
		this.mid = mid;
	}

	public Long getFid() {
		return fid;
	}

	public void setFid(Long fid) {
		this.fid = fid;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}