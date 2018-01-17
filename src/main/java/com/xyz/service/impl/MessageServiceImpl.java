package com.xyz.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xyz.domain.Message;
import com.xyz.mapper.MessageMapper;
import com.xyz.mapper.MessageMapperP;
import com.xyz.service.MessageService;

@Service("messageService")
@Transactional
public class MessageServiceImpl implements MessageService {

	@Autowired
	private MessageMapper messageMapper;

	@Autowired
	private MessageMapperP messageMapperP;

	@Override
	public Message getAppointedItem(Long uid) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * 根据相关信息修改一条消息
	 */
	@Override
	public boolean changeAppointedItem(Message item) {
		return messageMapper.updateByPrimaryKeySelective(item) > 0;
	}

	@Override
	public boolean saveAppointedItem(Message item) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean cutAppointedItem(Long id) {
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 * 根据指定状态获取指定页的消息
	 */
	@Override
	public PageInfo<Message> getAppointedPageItems(Integer current, Integer limit, Message item) {
		PageHelper.startPage(current, limit);
		return new PageInfo<Message>(messageMapperP.selectPages(item));
	}

	/**
	 * 批量删除消息
	 */
	@Override
	public boolean cutBatchItems(Long[] ids) {
		return messageMapperP.deleteBatch(ids) > 0;
	}
}
