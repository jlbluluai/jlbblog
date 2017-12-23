package com.xyz.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xyz.domain.Artical;
import com.xyz.mapper.ArticalMapper;
import com.xyz.service.ArticalService;

@Transactional
@Service("articalService")
public class ArticalServiceImpl implements ArticalService {

	@Autowired
	private ArticalMapper articalMapper;

	@Override
	public Artical getAppointedItem(Long uid) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean changeAppointedItem(Artical item) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean saveAppointedItem(Artical item) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean cutAppointedItem(Long id) {
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 * 根据条件获取指定页的文章
	 */
	public PageInfo<Artical> getAppointedPageItems(Integer current, Integer limit, Artical item) {
		PageHelper.startPage(current, limit);
		return new PageInfo<Artical>(articalMapper.selectPages(item));
	}

}
