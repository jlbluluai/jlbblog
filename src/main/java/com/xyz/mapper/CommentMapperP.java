package com.xyz.mapper;

import java.util.List;

import com.xyz.base.BaseMax;
import com.xyz.domain.Comment;

public interface CommentMapperP{
	
	List<Comment> selectPages(Comment comment);
	
	int selectCount();
}