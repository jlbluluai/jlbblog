package com.xyz.dto;

import java.io.Serializable;

public class BloggerDto implements Serializable {

	private Long uid;

	private String nickname;

	private String blogAge;
	
	private String makeDay;

	private Integer fans;

	private Integer follows;

	public Long getUid() {
		return uid;
	}

	public void setUid(Long uid) {
		this.uid = uid;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getBlogAge() {
		return blogAge;
	}

	public void setBlogAge(String blogAge) {
		this.blogAge = blogAge;
	}

	public Integer getFans() {
		return fans;
	}

	public void setFans(Integer fans) {
		this.fans = fans;
	}

	public Integer getFollows() {
		return follows;
	}

	public void setFollows(Integer follows) {
		this.follows = follows;
	}

	public String getMakeDay() {
		return makeDay;
	}

	public void setMakeDay(String makeDay) {
		this.makeDay = makeDay;
	}
	
	

}
