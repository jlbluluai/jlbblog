package com.xyz.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xyz.domain.Dynamic;
import com.xyz.mapper.DynamicMapper;
import com.xyz.service.DynamicService;

@Transactional
@Service("dynamicService")
public class DynamicServiceImpl implements DynamicService {

	@Autowired
	private DynamicMapper dynamicMapper;

	public Dynamic getAppointedItem(Long uid) {
		return null;
	}

	public boolean changeAppointedItem(Dynamic item) {
		return false;
	}

	/**
	 * 写入一条动态
	 */
	public boolean saveAppointedItem(Dynamic item) {
		return dynamicMapper.insertSelective(item) > 0;
	}

	/**
	 * 获取指定页指定人的动态
	 */
	public PageInfo<Dynamic> getAppointedPageItems(Integer current, Integer limit, Dynamic dynamic) {
		PageHelper.startPage(current, limit);

		return new PageInfo<Dynamic>(dynamicMapper.selectPages(dynamic));
	}

	/**
	 * 删除指定的动态
	 */
	public boolean cutAppointedItem(Long id) {
		return dynamicMapper.deleteByPrimaryKey(id) > 0;
	}

}
