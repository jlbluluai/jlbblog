package com.xyz.domain;

import java.io.Serializable;

public class ArticalAssCategoryKey implements Serializable {
    private Long aid;

    private Integer cid;

    private static final long serialVersionUID = 1L;

    public Long getAid() {
        return aid;
    }

    public void setAid(Long aid) {
        this.aid = aid;
    }

    public Integer getCid() {
        return cid;
    }

    public void setCid(Integer cid) {
        this.cid = cid;
    }
}