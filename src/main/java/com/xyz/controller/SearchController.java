package com.xyz.controller;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xyz.common.CommonElement;
import com.xyz.dto.PagesFeedback;
import com.xyz.util.SolrUtils;

/**
 * 搜索引擎模块，虽然可能这边只处理一个方法，但搜索引擎本身是一个很重要的模块，所以独立开写
 * 
 * @author pc
 *
 */
@Controller
public class SearchController {

	private Logger logger = Logger.getLogger(SearchController.class);

	// 统一设定数据
	private String contentType = "text/html;charset=UTF-8";
	private String f1 = "--------------";
	private String f2 = "--------------";

	/**
	 * 搜索引擎模块
	 * 
	 * @param condition
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getSearchResult", method = RequestMethod.GET)
	public PagesFeedback getSearchResult(@RequestParam("condition") String condition,
			@RequestParam("current") Integer current) {
		PagesFeedback feedback = new PagesFeedback();
		logger.info(CommonElement.separator + "搜索引擎启动" + CommonElement.separator);

		System.out.println(condition+" "+current);
		
		int nums = 10;// 默认一页数量
		int start = (current - 1) * 10;// 根据当前页计算数据开始位置

		try {
			feedback = SolrUtils.solrQueryArticals(condition, start, nums);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info(CommonElement.separator + "搜索引擎关闭" + CommonElement.separator);
		return feedback;
	}

}
