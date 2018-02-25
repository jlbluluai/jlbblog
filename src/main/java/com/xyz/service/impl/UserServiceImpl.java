package com.xyz.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;

import com.github.pagehelper.PageInfo;
import com.xyz.domain.User;
import com.xyz.domain.UserInfo;
import com.xyz.domain.UserPasschange;
import com.xyz.mapper.UserInfoMapper;
import com.xyz.mapper.UserMapper;
import com.xyz.mapper.UserMapperP;
import com.xyz.mapper.UserPasschangeMapper;
import com.xyz.service.UserService;
import com.xyz.util.Utils;

@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
@Service("userService")
public class UserServiceImpl implements UserService {

	@Autowired
	private UserMapper userMapper;

	@Autowired
	private UserMapperP userMapperP;

	@Autowired
	private UserInfoMapper userInfoMapper;

	@Autowired
	private UserPasschangeMapper userPasschangeMapper;

	@Override
	public User loginByPass(String loginname, String password) {
		User user = new User();
		user.setPassword(password);
		if (Utils.isEmail(loginname)) {
			user.setEmail(loginname);
		} else if (Utils.isPhone(loginname)) {
			user.setPhone(loginname);
		} else {
			user.setUsername(loginname);
		}

		return userMapper.selectByLoginname(user);
	}

	@Override
	public boolean verEmail(String email) {
		User user = new User();
		user.setEmail(email);
		if (userMapper.selectByEmail(user) != null) {
			return true;
		}
		return false;
	}

	@Override
	public boolean verPhone(String phone) {
		User user = new User();
		user.setPhone(phone);
		int i = userMapper.selectByPhone(user);
		return i > 0;
	}

	@Override
	public boolean register(Long id, String phone, String email, String username, String password) {
		User user = new User();
		user.setPhone(phone);
		user.setEmail(email);
		user.setUsername(username);
		user.setPassword(password);
		user.setIid(2);
		user.setMakeDay(new Date());
		user.setId(id);
		user.setNickname(username + "_temp");
		int i = userMapper.insert(user);

		UserInfo userInfo = new UserInfo();
		userInfo.setId(id);
		userInfo.setSex((byte) 0);
		userInfo.setBirthday(new Date());
		userInfoMapper.insert(userInfo);
		return i > 0;
	}

	@Override
	public boolean firstChangeNick(Long id, String nickname) {
		User user = new User();
		user.setId(id);
		user.setNickname(nickname);
		int i = userMapper.updateNickname(user);
		return i > 0;
	}

	@Override
	public boolean verUser(Long id) {
		User user = userMapper.selectByPrimaryKey(id);
		if (user != null) {
			return true;
		}
		return false;
	}

	@Override
	public boolean getBack(String email, String password) {
		User user = new User();
		user.setEmail(email);
		user.setPassword(password);
		User u = userMapper.selectByEmail(user);
		UserPasschange up = new UserPasschange();
		up.setUid(u.getId());
		up.setOpass(u.getPassword());
		up.setNpass(password);
		up.setCreateTime(new Date());
		userPasschangeMapper.insertSelective(up);
		int i = userMapper.updatePass(user);
		return i > 0;
	}

	@Override
	public User getAppointedItem(Long uid) {
		return userMapper.selectByPrimaryKey(uid);
	}

	/**
	 * 修改指定用户信息
	 */
	public boolean changeAppointedItem(User item) {
		return userMapper.updateByPrimaryKeySelective(item) > 0;
	}

	@Override
	public boolean saveAppointedItem(User item) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public PageInfo<User> getAppointedPageItems(Integer current, Integer limit, User item) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean cutAppointedItem(Long id) {
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 * 获取用户数量
	 */
	@Override
	public int getUserCount(User user) {
		return userMapperP.selectCountByCondition(user);
	}

}
