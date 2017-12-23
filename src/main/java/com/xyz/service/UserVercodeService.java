package com.xyz.service;

public interface UserVercodeService {

	boolean getTheCode(String email,String code);

	boolean addTheCode(String email, String code);
	
	boolean cutTheCode(String email);

}
