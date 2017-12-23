package com.xyz.mapper;

import com.xyz.base.BaseMid;
import com.xyz.domain.User;

public interface UserMapper extends BaseMid<User, Long> {

	User selectByLoginname(User user);

	User selectByEmail(User user);

	int selectByPhone(User user);

	int updateNickname(User user);
	
	int updatePass(User user);

}