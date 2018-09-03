package com.xyz.dto;

import java.io.Serializable;
import java.util.List;

public class PagesFeedback implements Serializable {

	private List<Object> oList;

	private Integer totalPages;

	private String msg;

	public List<Object> getoList() {
		return oList;
	}

	public void setoList(List<Object> oList) {
		this.oList = oList;
	}

	public Integer getTotalPages() {
		return totalPages;
	}

	public void setTotalPages(Integer totalPages) {
		this.totalPages = totalPages;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

}
