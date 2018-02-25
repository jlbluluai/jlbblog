package com.xyz.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class ArticalCategory implements Serializable {
	private Integer id;

	private String name;

	private Integer pid;

	private Date createTime;

	private static final long serialVersionUID = 1L;

	/* 视图属性 */
	private List<ArticalCategory> categorys;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name == null ? null : name.trim();
	}

	public Integer getPid() {
		return pid;
	}

	public void setPid(Integer pid) {
		this.pid = pid;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public List<ArticalCategory> getCategorys() {
		return categorys;
	}

	public void setCategorys(List<ArticalCategory> categorys) {
		this.categorys = categorys;
	}

}