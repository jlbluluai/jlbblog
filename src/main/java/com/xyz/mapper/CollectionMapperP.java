package com.xyz.mapper;

import java.util.List;

import com.xyz.domain.Collection;

public interface CollectionMapperP {
	
	List<Collection> selectPages(Collection collection);
}