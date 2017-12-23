package com.xyz.base;


public interface BaseMin<T> {

	int deleteByPrimaryKey(T key);

	int insert(T record);

	int insertSelective(T record);
}
