package com.xyz.service;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;

import com.xyz.domain.Pubboard;
import com.xyz.util.TestUtils;

public class PubboardServiceImplTest {

	private ApplicationContext ctx;
	private PubboardService pubboardService;

	@Before
	public void setCtx() throws Exception {
		ctx = TestUtils.getCtx();
		pubboardService = (PubboardService)ctx.getBean("pubboardService");
	}

	@Test
	public void testGetCurrentPubboard(){
		Pubboard pubboard = pubboardService.getCurrentPubboard();
		System.out.println(pubboard);
	}

}
