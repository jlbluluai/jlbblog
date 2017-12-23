package com.xyz.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageInfo;
import com.xyz.base.RedisDao;
import com.xyz.domain.UserInfo;
import com.xyz.mapper.UserInfoMapper;
import com.xyz.service.UserInfoService;
import com.xyz.service.UserService;

@Transactional
@Service("userInfoService")
public class UserInfoServiceImpl implements UserInfoService {

	@Autowired
	private UserInfoMapper userInfoMapper;

	@Autowired
	private RedisDao redisDao;

	@Autowired
	@Qualifier("userService")
	private UserService userService;

	/**
	 * 获取指定的用户信息
	 * 
	 * @param uid
	 * @return
	 */
	public UserInfo getAppointedItem(Long uid) {
		UserInfo userInfo = redisDao.get("userInfo_" + uid);
		if (userInfo == null) {
			userInfo = userInfoMapper.selectByPrimaryKey(uid);
			redisDao.set("userInfo_" + uid, userInfo);
			return userInfo;
		}
		return userInfo;
	}

	/**
	 * 修改指定的用户信息
	 * 
	 * @param item
	 * @return
	 */
	public boolean changeAppointedItem(UserInfo item) {
		redisDao.delete("userInfo_" + item.getId());
		userService.changeAppointedItem(item.getUser());
		return userInfoMapper.updateByPrimaryKeySelective(item) > 0;
	}

	/**
	 * 新增指定的用户信息
	 * 
	 * @param item
	 * @return
	 */
	@Override
	public boolean saveAppointedItem(UserInfo item) {
		return userInfoMapper.insertSelective(item) > 0;
	}

	@Override
	public PageInfo<UserInfo> getAppointedPageItems(Integer current, Integer limit, UserInfo item) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean cutAppointedItem(Long id) {
		// TODO Auto-generated method stub
		return false;
	}

}
