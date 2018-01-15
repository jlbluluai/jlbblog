package com.xyz.service;

import static org.junit.Assert.fail;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;

import com.xyz.domain.FollowKey;
import com.xyz.util.TestUtils;

public class FollowServiceImplTest {

	private ApplicationContext ctx;
	private FollowService followService;

	@Before
	public void setCtx() throws Exception {
		ctx = TestUtils.getCtx();
		followService = (FollowService) ctx.getBean("followService");
	}

	@Test
	public void testGetFollows() {
		FollowKey followKey = new FollowKey();
		//followKey.setMid(7L);
		followKey.setFid(7L);
		
		List<FollowKey> list = followService.getFollows(followKey);
		for(FollowKey f : list){
			System.out.println(f.getUser().getNickname());
		}
	}

}
