package com.xyz.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class RecommendCategory implements Serializable {
    private Integer id;

    private String name;

    private Date createTime;

    private static final long serialVersionUID = 1L;
    
    /*视图属性*/
    private List<Recommend> recommends;

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

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

	public List<Recommend> getRecommends() {
		return recommends;
	}

	public void setRecommends(List<Recommend> recommends) {
		this.recommends = recommends;
	}
    
    
}