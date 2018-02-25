package com.xyz.service;

import static org.junit.Assert.fail;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;

import com.xyz.domain.ArticalCategory;
import com.xyz.util.TestUtils;

public class ArticalCategoryServiceImplTest {

	private ApplicationContext ctx;
	private ArticalCategoryService articalCategoryService;

	@Before
	public void setCtx() throws Exception {
		ctx = TestUtils.getCtx();
		articalCategoryService = (ArticalCategoryService) ctx.getBean("articalCategoryService");
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
	public void testGetAllCategorys() {
		List<ArticalCategory> list = articalCategoryService.getAllCategorys();
		for (ArticalCategory articalCategory : list) {
			System.out.println(articalCategory.getName());
			for (ArticalCategory childCategory : articalCategory.getCategorys()) {
				System.out.println("--" + childCategory.getName());
			}
		}
	}

}
