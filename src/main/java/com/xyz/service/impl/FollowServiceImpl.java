package com.xyz.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xyz.domain.FollowKey;
import com.xyz.mapper.FollowMapper;
import com.xyz.mapper.FollowMapperP;
import com.xyz.service.FollowService;

@Service("followService")
@Transactional
public class FollowServiceImpl implements FollowService {

	@Autowired
	private FollowMapper followMapper;

	@Autowired
	private FollowMapperP followMapperP;

	/**
	 * 获取关注与粉丝
	 */
	@Override
	public List<FollowKey> getFollows(FollowKey follow) {
		return followMapperP.selectPages(follow);
	}

}
