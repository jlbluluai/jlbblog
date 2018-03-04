package com.xyz.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageInfo;
import com.xyz.domain.Version;
import com.xyz.mapper.VersionMapper;
import com.xyz.mapper.VersionMapperP;
import com.xyz.service.VersionService;

@Service("versionService")
public class VersionServiceImpl implements VersionService {

	@Autowired
	private VersionMapper versionMapper;

	@Autowired
	private VersionMapperP versionMapperP;

	@Override
	public Version getAppointedItem(Integer uid) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean changeAppointedItem(Version item) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean saveAppointedItem(Version item) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean cutAppointedItem(Integer id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public PageInfo<Version> getAppointedPageItems(Integer current, Integer limit, Version item) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * 获取所有的版本信息
	 */
	@Override
	public List<Version> getAllVersions() {
		return versionMapperP.selectAllVersions();
	}

}
