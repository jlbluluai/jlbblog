package com.xyz.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageInfo;
import com.xyz.domain.Suggestion;
import com.xyz.mapper.SuggestionMapper;
import com.xyz.service.SuggestionService;

@Service("suggestionService")
@Transactional
public class SuggestionServiceImpl implements SuggestionService {

	@Autowired
	private SuggestionMapper suggestionMapper;

	@Override
	public Suggestion getAppointedItem(Integer uid) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean changeAppointedItem(Suggestion item) {
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 * 插入一条意见
	 */
	@Override
	public boolean saveAppointedItem(Suggestion item) {
		return suggestionMapper.insertSelective(item) > 0;
	}

	@Override
	public boolean cutAppointedItem(Integer id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public PageInfo<Suggestion> getAppointedPageItems(Integer current, Integer limit, Suggestion item) {
		// TODO Auto-generated method stub
		return null;
	}

}
