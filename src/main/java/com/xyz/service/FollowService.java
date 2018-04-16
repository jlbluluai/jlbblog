package com.xyz.service;

import java.util.List;

import com.xyz.domain.FollowKey;

public interface FollowService {

	List<FollowKey> getFollows(FollowKey follow);
	
	int getFanFollowCount(FollowKey follow);
	
	boolean saveOneFollow(FollowKey followKey);

}
