package com.xyz.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xyz.domain.UserVercode;
import com.xyz.mapper.UserVercodeMapper;
import com.xyz.service.UserVercodeService;

@Service("userVercodeService")
public class UserVercodeServiceImpl implements UserVercodeService {

	@Autowired
	private UserVercodeMapper userVercodeMapper;

	@Override
	public boolean getTheCode(String email, String code) {
		UserVercode userVercode = new UserVercode();
		userVercode.setEmail(email);
		userVercode.setCode(code);
		int i = userVercodeMapper.selectByEmailAndCode(userVercode);
		return i > 0;
	}

	@Override
	public boolean addTheCode(String email, String code) {
		UserVercode userVercode = new UserVercode();
		userVercode.setEmail(email);
		userVercode.setCode(code);
		userVercodeMapper.deleteByEmail(userVercode);
		int i = userVercodeMapper.insertSelective(userVercode);
		return i > 0;
	}

	@Override
	public boolean cutTheCode(String email) {
		UserVercode userVercode = new UserVercode();
		userVercode.setEmail(email);
		int i = userVercodeMapper.deleteByEmail(userVercode);
		return i > 0;
	}

}
