package com.xyz.mapper;

import java.util.List;

import com.xyz.base.BaseMax;
import com.xyz.domain.Artical;

public interface ArticalMapper extends BaseMax<Artical, Long> {
	
	List<Artical> selectPages(Artical artical);
}