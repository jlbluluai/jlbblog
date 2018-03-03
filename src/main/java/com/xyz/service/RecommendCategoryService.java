package com.xyz.service;

import java.util.List;

import com.xyz.base.BaseService;
import com.xyz.domain.RecommendCategory;

public interface RecommendCategoryService extends BaseService<RecommendCategory, Integer>{

	List<RecommendCategory> getAllRecommends();
}
