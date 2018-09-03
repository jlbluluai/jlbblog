package com.xyz.mapper;

import java.util.List;

import com.xyz.domain.Message;

public interface MessageMapperP {

	List<Message> selectPages(Message message);

	int deleteBatch(Long[] id);
	
	int selectCount1(Message message);
}