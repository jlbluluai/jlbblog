package com.xyz.mapper;

import com.xyz.base.BaseMid;
import com.xyz.domain.UserVercode;

public interface UserVercodeMapper extends BaseMid<UserVercode, Integer> {

	int selectByEmailAndCode(UserVercode userVercode);

	int deleteByEmail(UserVercode userVercode);
}