package com.xyz.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageInfo;
import com.xyz.base.RedisDao;
import com.xyz.domain.RecommendCategory;
import com.xyz.mapper.RecommendCategoryMapper;
import com.xyz.mapper.RecommendCategoryMapperP;
import com.xyz.service.RecommendCategoryService;

@Service("recommendCategoryService")
@Transactional
public class RecommendCategoryServiceImpl implements RecommendCategoryService {

	@Autowired
	private RecommendCategoryMapper recommendCategoryMapper;

	@Autowired
	private RecommendCategoryMapperP recommendCategoryMapperP;
	
	@Autowired
	private RedisDao redisDao;

	@Override
	public RecommendCategory getAppointedItem(Integer uid) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean changeAppointedItem(RecommendCategory item) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean saveAppointedItem(RecommendCategory item) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean cutAppointedItem(Integer id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public PageInfo<RecommendCategory> getAppointedPageItems(Integer current, Integer limit, RecommendCategory item) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * 获取所有的推荐
	 */
	@Override
	public List<RecommendCategory> getAllRecommends() {
		List<RecommendCategory> list = redisDao.get("recommendCategory");
		if(list == null){
			list = recommendCategoryMapperP.selectRecommends();
			redisDao.set("recommendCategory", list);
		}
		return list;
	}

}
