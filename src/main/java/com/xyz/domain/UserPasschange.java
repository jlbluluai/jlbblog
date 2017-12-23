package com.xyz.domain;

import java.io.Serializable;
import java.util.Date;

public class UserPasschange implements Serializable {
    private Integer id;

    private Long uid;

    private String opass;

    private String npass;

    private Date createTime;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    public String getOpass() {
        return opass;
    }

    public void setOpass(String opass) {
        this.opass = opass == null ? null : opass.trim();
    }

    public String getNpass() {
        return npass;
    }

    public void setNpass(String npass) {
        this.npass = npass == null ? null : npass.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}