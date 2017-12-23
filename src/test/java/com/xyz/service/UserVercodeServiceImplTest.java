package com.xyz.service;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;

import com.xyz.util.TestUtils;

public class UserVercodeServiceImplTest {

	private ApplicationContext ctx;
	private UserVercodeService userVercodeService;

	@Before
	public void setCtx() throws Exception {
		ctx = TestUtils.getCtx();
		userVercodeService = (UserVercodeService) ctx.getBean("userVercodeService");
	}

	@Test
	public void testGetTheCode() {
		boolean flag = userVercodeService.getTheCode("290344808@qq.com","123456");
		System.out.println(flag);
	}

	@Test
	public void testAddTheCode() {
		boolean flag = userVercodeService.addTheCode("290344808@qq.com", "123456");
		System.out.println(flag);
	}

	@Test
	public void testCutTheCode() {
		boolean flag = userVercodeService.cutTheCode("290344808@qq.com");
		System.out.println(flag);
	}

}
