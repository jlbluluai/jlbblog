package com.xyz.mapper;

import com.xyz.base.BaseMax;
import com.xyz.domain.Pubboard;

public interface PubboardMapper extends BaseMax<Pubboard, Integer> {

	Pubboard selectByStatus();
}