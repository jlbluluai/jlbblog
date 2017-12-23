package com.xyz.base;


public interface BaseMax<T, K> {

	int deleteByPrimaryKey(K id);

	int insert(T record);

	int insertSelective(T record);

	T selectByPrimaryKey(K id);

	int updateByPrimaryKeySelective(T record);

	int updateByPrimaryKeyWithBLOBs(T record);

	int updateByPrimaryKey(T record);
}
