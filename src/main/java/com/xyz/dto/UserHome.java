package com.xyz.dto;

import java.io.Serializable;

public class UserHome implements Serializable {

	private String master;
	private String access;

	public String getMaster() {
		return master;
	}

	public void setMaster(String master) {
		this.master = master;
	}

	public String getAccess() {
		return access;
	}

	public void setAccess(String access) {
		this.access = access;
	}

}
