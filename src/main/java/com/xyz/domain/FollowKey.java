package com.xyz.domain;

import java.io.Serializable;

public class FollowKey implements Serializable {
    private Long mid;

    private Long fid;

    private static final long serialVersionUID = 1L;

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
}