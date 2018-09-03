package com.xyz.service;

import java.util.List;

import com.xyz.base.BaseService;
import com.xyz.domain.File;

public interface FileService extends BaseService<File, Integer> {

	List<File> getAll1(File item);
	
	File saveOneFileAndGetId(File item);

}
