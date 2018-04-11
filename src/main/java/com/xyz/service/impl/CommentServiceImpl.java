package com.xyz.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xyz.domain.Comment;
import com.xyz.mapper.CommentMapper;
import com.xyz.mapper.CommentMapperP;
import com.xyz.service.CommentService;

@Service("commentService")
@Transactional
public class CommentServiceImpl implements CommentService {

	@Autowired
	private CommentMapper commentMapper;

	@Autowired
	private CommentMapperP commentMapperP;

	@Override
	public Comment getAppointedItem(Long uid) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean changeAppointedItem(Comment item) {
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 * 写一篇评论
	 */
	@Override
	public boolean saveAppointedItem(Comment item) {
		// TODO Auto-generated method stub
		return commentMapper.insertSelective(item) > 0;
	}

	@Override
	public boolean cutAppointedItem(Long id) {
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 * 根据相关条件获取指定页的评论
	 */
	@Override
	public PageInfo<Comment> getAppointedPageItems(Integer current, Integer limit, Comment item) {
		PageHelper.startPage(current, limit);
		return new PageInfo<Comment>(commentMapperP.selectPages(item));
	}

	/**
	 * 获取评论数量
	 */
	@Override
	public int getCount() {
		return commentMapperP.selectCount();
	}

}
