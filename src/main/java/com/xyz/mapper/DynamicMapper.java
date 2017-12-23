package com.xyz.mapper;

import java.util.List;

import com.xyz.base.BaseMax;
import com.xyz.domain.Dynamic;

public interface DynamicMapper extends BaseMax<Dynamic, Long> {
	
	List<Dynamic> selectPages(Dynamic dynamic);
}