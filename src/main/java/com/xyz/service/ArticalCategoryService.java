package com.xyz.service;

import java.util.List;

import com.xyz.base.BaseService;
import com.xyz.domain.ArticalCategory;

public interface ArticalCategoryService extends BaseService<ArticalCategory, Integer>{

	List<ArticalCategory> getAllCategorys();
	
}
