package com.xyz.domain;

import java.io.Serializable;
import java.util.Date;

public class Dynamic implements Serializable {
    private Long id;

    private Long uid;

    private Byte isBlog;

    private Long aid;

    private Date createTime;

    private String content;

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

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    public Byte getIsBlog() {
        return isBlog;
    }

    public void setIsBlog(Byte isBlog) {
        this.isBlog = isBlog;
    }

    public Long getAid() {
        return aid;
    }

    public void setAid(Long aid) {
        this.aid = aid;
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