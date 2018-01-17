package com.xyz.service;

import com.xyz.base.BaseService;
import com.xyz.domain.Message;

public interface MessageService extends BaseService<Message, Long> {

	boolean cutBatchItems(Long[] ids);
}
