package com.xyz.service;

import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;

import com.github.pagehelper.PageInfo;
import com.xyz.domain.Comment;
import com.xyz.util.TestUtils;

public class CommentServiceImplTest {
	
	private ApplicationContext ctx;
	private CommentService commentService;

	@Before
	public void setCtx() throws Exception {
		ctx = TestUtils.getCtx();
		commentService = (CommentService)ctx.getBean("commentService");
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
		Comment comment = new Comment();
		comment.setUid(7L);
		PageInfo<Comment> pageInfo = commentService.getAppointedPageItems(1, 10, comment);
		for(Comment item : pageInfo.getList()){
			System.out.println(item.getId()+" "+item.getContent());
		}
	}

}
