package com.xyz.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageInfo;
import com.xyz.domain.File;
import com.xyz.mapper.FileMapper;
import com.xyz.mapper.FileMapperP;
import com.xyz.service.FileService;

@Transactional
@Service("fileService")
public class FileServiceImpl implements FileService {

	@Autowired
	private FileMapper fileMapper;

	@Autowired
	private FileMapperP fileMapperP;

	@Override
	public File getAppointedItem(Integer uid) {
		return fileMapper.selectByPrimaryKey(uid);
	}

	@Override
	public boolean changeAppointedItem(File item) {
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 * 插入一条文件记录
	 */
	@Override
	public boolean saveAppointedItem(File item) {
		return fileMapper.insertSelective(item) > 0;
	}

	/**
	 * 插入一条文件记录并返回id
	 */
	@Override
	public File saveOneFileAndGetId(File item) {
		fileMapper.insertSelective(item);
		return item;
	}

	@Override
	public boolean cutAppointedItem(Integer id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public PageInfo<File> getAppointedPageItems(Integer current, Integer limit, File item) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<File> getAll1(File item) {
		return fileMapperP.selectAll1(item);
	}

}
