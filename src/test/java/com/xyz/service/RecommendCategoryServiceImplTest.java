package com.xyz.service;

import static org.junit.Assert.fail;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;

import com.xyz.domain.Recommend;
import com.xyz.domain.RecommendCategory;
import com.xyz.util.TestUtils;

public class RecommendCategoryServiceImplTest {

	private ApplicationContext ctx;
	private RecommendCategoryService recommendCategoryService;

	@Before
	public void setCtx() throws Exception {
		ctx = TestUtils.getCtx();
		recommendCategoryService = (RecommendCategoryService) ctx.getBean("recommendCategoryService");
	}

	@Test
	public void testGetAppointedItem() {
		fail("Not yet implemented");
	}

	@Test
	public void testChangeAppointedItem() {
		fail("Not yet implemented");
	}

	@Test
	public void testSaveAppointedItem() {
		fail("Not yet implemented");
	}

	@Test
	public void testCutAppointedItem() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetAppointedPageItems() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetAllRecommends() {
		List<RecommendCategory> list = recommendCategoryService.getAllRecommends();
		System.out.println(list.size());
		for (RecommendCategory rc : list) {
			System.out.println(rc.getName());
			for (Recommend r : rc.getRecommends()) {
				System.out.println("  " + r.getName());
			}
		}
	}

}
