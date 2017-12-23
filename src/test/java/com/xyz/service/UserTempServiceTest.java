package com.xyz.service;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;

import com.xyz.util.TestUtils;

public class UserTempServiceTest {

	private ApplicationContext ctx;
	private UserTempService userTempService;

	@Before
	public void setCtx() throws Exception {
		ctx = TestUtils.getCtx();
		userTempService = (UserTempService) ctx.getBean("userTempService");
	}

	@Test
	public void testAddTheUser() {
		boolean flag = userTempService.addTheUser("18752835321", "981378964@qq.com");
		System.out.println(flag);
	}

	@Test
	public void testGetTheUser() {
		boolean flag = userTempService.getTheUser("18752835321", "981378964@qq.com");
		System.out.println(flag);
	}

	@Test
	public void testCutTheUser() {
		boolean flag = userTempService.cutTheUser("18752835321", "981378964@qq.com");
		System.out.println(flag);
	}

}
