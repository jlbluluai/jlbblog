package com.xyz.domain;

import java.io.Serializable;
import java.util.Date;

public class Collection implements Serializable {
    private Long id;

    private Long aid;

    private Long uid;

    private Date createTime;

    private static final long serialVersionUID = 1L;
    
    /*视图属性*/
    private User user;
    private Artical artical;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getAid() {
        return aid;
    }

    public void setAid(Long aid) {
        this.aid = aid;
    }

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Artical getArtical() {
		return artical;
	}

	public void setArtical(Artical artical) {
		this.artical = artical;
	}
    
    
}