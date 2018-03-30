package com.xyz.domain;

import java.io.Serializable;
import java.util.Date;

public class File implements Serializable {
    private Integer id;

    private String filename;

    private String filestyle;

    private Long uid;

    private Integer category;

    private Date uploadtime;

    private String folder;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename == null ? null : filename.trim();
    }

    public String getFilestyle() {
        return filestyle;
    }

    public void setFilestyle(String filestyle) {
        this.filestyle = filestyle == null ? null : filestyle.trim();
    }

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    public Integer getCategory() {
        return category;
    }

    public void setCategory(Integer category) {
        this.category = category;
    }

    public Date getUploadtime() {
        return uploadtime;
    }

    public void setUploadtime(Date uploadtime) {
        this.uploadtime = uploadtime;
    }

    public String getFolder() {
        return folder;
    }

    public void setFolder(String folder) {
        this.folder = folder == null ? null : folder.trim();
    }
}