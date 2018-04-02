package com.xyz.mapper;

import com.xyz.base.BaseMin;
import com.xyz.domain.ArticalAssCategoryKey;

public interface ArticalAssCategoryMapper extends BaseMin<ArticalAssCategoryKey> {

	ArticalAssCategoryKey selectOne(Long aid);

}