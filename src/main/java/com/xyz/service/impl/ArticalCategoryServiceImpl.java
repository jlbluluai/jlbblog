package com.xyz.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageInfo;
import com.xyz.domain.ArticalCategory;
import com.xyz.mapper.ArticalCategoryMapper;
import com.xyz.mapper.ArticalCategoryMapperP;
import com.xyz.service.ArticalCategoryService;

@Transactional
@Service("articalCategoryService")
public class ArticalCategoryServiceImpl implements ArticalCategoryService {

	@Autowired
	private ArticalCategoryMapper articalCategoryMapper;

	@Autowired
	private ArticalCategoryMapperP articalCategoryMapperP;

	@Override
	public ArticalCategory getAppointedItem(Integer id) {
		return articalCategoryMapper.selectByPrimaryKey(id);
	}

	@Override
	public boolean changeAppointedItem(ArticalCategory item) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean saveAppointedItem(ArticalCategory item) {
		return articalCategoryMapper.insertSelective(item) > 0;
	}

	@Override
	public boolean cutAppointedItem(Integer id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public PageInfo<ArticalCategory> getAppointedPageItems(Integer current, Integer limit, ArticalCategory item) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * 获取所有的文章分类
	 */
	@Override
	public List<ArticalCategory> getAllCategorys() {
		return articalCategoryMapperP.selectAll();
	}

}
