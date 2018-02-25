package com.xyz.service;

import com.xyz.base.BaseService;
import com.xyz.domain.User;

public interface UserService extends BaseService<User,Long>{

	User loginByPass(String loginname, String password);

	boolean verEmail(String email);

	boolean verPhone(String phone);

	boolean register(Long id, String phone, String email, String username, String password);

	boolean firstChangeNick(Long id, String nickname);

	boolean verUser(Long id);

	boolean getBack(String email, String password);
	
	int getUserCount(User user);
}
