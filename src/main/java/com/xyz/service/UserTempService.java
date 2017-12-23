package com.xyz.service;

import com.xyz.domain.UserTemp;

public interface UserTempService {

	boolean addTheUser(String phone, String email);

	boolean getTheUser(String phone, String email);

	boolean cutTheUser(String phone, String email);

}
