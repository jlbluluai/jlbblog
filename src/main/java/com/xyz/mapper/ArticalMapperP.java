package com.xyz.mapper;

import java.util.List;

import com.xyz.base.BaseMax;
import com.xyz.domain.Artical;

public interface ArticalMapperP{
	
	List<Artical> selectPages(Artical artical);
	
	int selectCount();
}