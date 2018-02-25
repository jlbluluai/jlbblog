package com.xyz.service;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;

import com.xyz.domain.User;
import com.xyz.util.TestUtils;
import com.xyz.util.Utils;

public class UserServiceImplTest {

	private ApplicationContext ctx;
	private UserService userService;

	@Before
	public void setCtx() throws Exception {
		ctx = TestUtils.getCtx();
		userService = (UserService) ctx.getBean("userService");
	}

	@Test
	public void testLoginByPass() {
		User user = userService.loginByPass("290344808@qq.com", "jie080903?");
		System.out.println(user);
	}

	@Test
	public void testVerEmail() {
		boolean flag = userService.verEmail("290344808@qq.com");
		System.out.println(flag);
	}

	@Test
	public void testVerPhone() {
		boolean flag = userService.verPhone("18752835321");
		System.out.println(flag);
	}

	@Test
	public void testRegister() {
		Long id = Utils.createComplexId();
		boolean flag = userService.register(id, "15850682759", "15850682759@qq.com", "yangjie", "123456");
		System.out.println(flag);
	}

	@Test
	public void testFirstChangeNick() {
		boolean flag = userService.firstChangeNick(1511262442788455678L, "养狗比");
		System.out.println(flag);
	}

	@Test
	public void testVerUser() {
		boolean flag = userService.verUser(1511262442788455678L);
		System.out.println(flag);
	}

	@Test
	public void testGetBack() {
		boolean flag = userService.getBack("15850682759@qq.com", "23456");
		System.out.println(flag);
	}
	
	@Test
	public void testGetUserCount(){
		User user = new User();
		user.setIid(3);
		int count = userService.getUserCount(user);
		System.out.println("数量"+count);
	}

}
