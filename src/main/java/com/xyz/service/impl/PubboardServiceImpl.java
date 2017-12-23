package com.xyz.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xyz.domain.Pubboard;
import com.xyz.mapper.PubboardMapper;
import com.xyz.service.PubboardService;

@Service("pubboardService")
public class PubboardServiceImpl implements PubboardService {

	@Autowired
	private PubboardMapper pubboardMapper;

	@Override
	public Pubboard getCurrentPubboard() {
		return pubboardMapper.selectByStatus();
	}

}
