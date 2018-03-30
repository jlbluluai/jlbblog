package com.xyz.service;

import com.xyz.base.BaseService;
import com.xyz.domain.Artical;
import com.xyz.domain.ArticalCategory;
import com.xyz.domain.File;

public interface ArticalService extends BaseService<Artical, Long> {

	int getCount();

	boolean writeOneBlog(Artical artical, ArticalCategory articalCategory, File file);

	boolean modifyOneBlog(Artical artical, ArticalCategory articalCategory, File file);

}
