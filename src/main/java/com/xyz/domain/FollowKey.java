package com.xyz.domain;

import java.io.Serializable;

public class FollowKey implements Serializable {
	private Long mid;

	private Long fid;

	private static final long serialVersionUID = 1L;

	/* 视图属性 */
	private User user;
	private String headpic;
	
	private Long nowId;

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

	public String getHeadpic() {
		return headpic;
	}

	public void setHeadpic(String headpic) {
		this.headpic = headpic;
	}

	public Long getNowId() {
		return nowId;
	}

	public void setNowId(Long nowId) {
		this.nowId = nowId;
	}
	
	

}