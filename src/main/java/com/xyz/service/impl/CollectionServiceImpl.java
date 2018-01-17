package com.xyz.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xyz.domain.Collection;
import com.xyz.mapper.CollectionMapper;
import com.xyz.mapper.CollectionMapperP;
import com.xyz.service.CollectionService;

@Service("collectionService")
@Transactional
public class CollectionServiceImpl implements CollectionService {

	@Autowired
	private CollectionMapper collectionMapper;

	@Autowired
	private CollectionMapperP collectionMapperP;

	@Override
	public Collection getAppointedItem(Long uid) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean changeAppointedItem(Collection item) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean saveAppointedItem(Collection item) {
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 * 取消收藏一篇文章
	 */
	@Override
	public boolean cutAppointedItem(Long id) {
		return collectionMapper.deleteByPrimaryKey(id) > 0;
	}

	/**
	 * 根据条件查询对应页的收藏记录
	 */
	@Override
	public PageInfo<Collection> getAppointedPageItems(Integer current, Integer limit, Collection item) {
		PageHelper.startPage(current, limit);
		return new PageInfo<Collection>(collectionMapperP.selectPages(item));
	}
}
