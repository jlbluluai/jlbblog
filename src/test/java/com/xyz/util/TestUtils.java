package com.xyz.util;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

public class TestUtils {

	public static ApplicationContext getCtx() {
		return new FileSystemXmlApplicationContext("classpath:applicationContext-core.xml",
				"classpath:applicationContext-redis.xml");
	}
}
