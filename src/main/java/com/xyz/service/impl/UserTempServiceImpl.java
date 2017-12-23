package com.xyz.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xyz.domain.UserTemp;
import com.xyz.mapper.UserTempMapper;
import com.xyz.service.UserTempService;

@Service("userTempService")
public class UserTempServiceImpl implements UserTempService {

	@Autowired
	private UserTempMapper userTempMapper;

	@Override
	public boolean addTheUser(String phone, String email) {
		UserTemp userTemp = new UserTemp();
		userTemp.setPhone(phone);
		userTemp.setEmail(email);
		userTempMapper.deleteByPhoneAndEmail(userTemp);
		int i = userTempMapper.insertSelective(userTemp);
		return i > 0;
	}

	@Override
	public boolean getTheUser(String phone, String email) {
		UserTemp userTemp = new UserTemp();
		userTemp.setPhone(phone);
		userTemp.setEmail(email);
		int i = userTempMapper.selectByPhoneAndEmail(userTemp);
		return i > 0;
	}

	@Override
	public boolean cutTheUser(String phone, String email) {
		UserTemp userTemp = new UserTemp();
		userTemp.setPhone(phone);
		userTemp.setEmail(email);
		int i = userTempMapper.deleteByPhoneAndEmail(userTemp);
		return i > 0;
	}

}
