package com.xyz.service;

import java.util.List;

import com.xyz.base.BaseService;
import com.xyz.domain.Version;

public interface VersionService extends BaseService<Version, Integer> {

	List<Version> getAllVersions();
}
