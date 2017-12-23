package com.xyz.mapper;

import com.xyz.base.BaseMid;
import com.xyz.domain.UserTemp;

public interface UserTempMapper extends BaseMid<UserTemp, Integer> {

	int selectByPhoneAndEmail(UserTemp userTemp);

	int deleteByPhoneAndEmail(UserTemp userTemp);
}