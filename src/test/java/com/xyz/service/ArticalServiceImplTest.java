package com.xyz.service;

import static org.junit.Assert.*;

import java.util.List;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xyz.domain.Artical;
import com.xyz.domain.ArticalCategory;
import com.xyz.util.TestUtils;

public class ArticalServiceImplTest {
	
	
	private ApplicationContext ctx;
	private ArticalService articalService;

	@Before
	public void setCtx() throws Exception {
		ctx = TestUtils.getCtx();
		articalService = (ArticalService)ctx.getBean("articalService");
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
		Artical artical = new Artical();
		artical.setUid(7L);
		artical.setIsPublish((byte)1);
		artical.setIsPublic((byte)1);
		//artical.setIsNice((byte)1);
		PageInfo<Artical> pageInfo = articalService.getAppointedPageItems(1, 10, artical);
		List<Artical> list = pageInfo.getList();
		System.out.println(list.size());
		for (Artical artical2 : list) {
			Set<ArticalCategory> set = artical2.getArticalCategorys();
			for (ArticalCategory articalCategory : set) {
				System.out.println(articalCategory.getName());
			}
			System.out.println("------------");
		}
	}

}
