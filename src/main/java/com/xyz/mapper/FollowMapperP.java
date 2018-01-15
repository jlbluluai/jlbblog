package com.xyz.mapper;

import java.util.List;

import com.xyz.domain.FollowKey;

public interface FollowMapperP{
	
	List<FollowKey> selectPages(FollowKey follow);

}