package com.xyz.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageInfo;
import com.xyz.domain.Apply;
import com.xyz.mapper.ApplyMapper;
import com.xyz.service.ApplyService;
import com.xyz.service.MessageService;

@Service("applyService")
@Transactional
public class ApplyServiceImpl implements ApplyService {

	@Autowired
	private ApplyMapper applyMapper;

	@Autowired
	@Qualifier("messageService")
	private MessageService messageService;

	@Override
	public Apply getAppointedItem(Long uid) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean changeAppointedItem(Apply item) {
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 * 根据相关信息存一条消息
	 * 
	 * @param item
	 * @return
	 */
	@Override
	public boolean saveAppointedItem(Apply item) {

		boolean flag1 = messageService.saveAppointedItem(item.getMessage());

		boolean flag2 = applyMapper.insertSelective(item) > 0;

		return flag1 && flag2;
	}

	@Override
	public boolean cutAppointedItem(Long id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public PageInfo<Apply> getAppointedPageItems(Integer current, Integer limit, Apply item) {
		// TODO Auto-generated method stub
		return null;
	}

}
