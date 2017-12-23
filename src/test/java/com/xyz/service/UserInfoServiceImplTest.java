package com.xyz.service;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;

import com.xyz.domain.UserInfo;
import com.xyz.util.TestUtils;

public class UserInfoServiceImplTest {
	private ApplicationContext ctx;
	private UserInfoService userInfoService;
	
	@Before
	public void setCtx() throws Exception {
		ctx = TestUtils.getCtx();
		userInfoService = (UserInfoService)ctx.getBean("userInfoService");
	}
	
	@Test
	public void testGetAppointedItem() {
		UserInfo userInfo = userInfoService.getAppointedItem(7L);
		System.out.println(userInfo.getBirthday());
	}

	@Test
	public void testChangeAppointedItem() {
		fail("Not yet implemented");
	}

	@Test
	public void testSaveAppointedItem() {
		fail("Not yet implemented");
	}

}
