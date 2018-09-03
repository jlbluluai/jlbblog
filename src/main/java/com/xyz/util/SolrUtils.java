package com.xyz.util;

import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;

import com.xyz.common.CommonElement;
import com.xyz.common.CommonUtils;
import com.xyz.common.DateUtils;
import com.xyz.dto.ArticalSolrDto;
import com.xyz.dto.PagesFeedback;

public final class SolrUtils {

	private static Logger logger = Logger.getLogger(SolrUtils.class);

	private static String errinf = "";

	/**
	 * solr查询，实现分页，高亮，封装。暂不支持统一，所以该查询就具体需求得进行定制
	 * 
	 * @param condition
	 *            条件
	 * @param start
	 *            开始位置
	 * @param nums
	 *            条数
	 * @throws Exception
	 */
	public static PagesFeedback solrQueryArticals(String condition, int start, int nums) throws Exception {
		logger.info(CommonElement.separator + "solr查询，博客定制开始" + CommonElement.separator);
		logger.info("条件：" + condition + " 开始：" + start + " 条数：" + nums);

		PagesFeedback feedback = new PagesFeedback();

		List<ArticalSolrDto> articalSolrDtos = new ArrayList<ArticalSolrDto>();

		// 检查
		if ("".equals(condition.trim())) {
			errinf = "查询条件不能为空";
			logger.error(errinf);
			throw new Exception(errinf);
		}
		if (start < 0) {
			errinf = "开始条数不能小于0";
			logger.error(errinf);
			throw new Exception(errinf);
		}
		if (nums < 0) {
			errinf = "查询条数不能小于0";
			logger.error(errinf);
			throw new Exception(errinf);
		}

		/* [1]获取连接 */
		// 读取连接信息
		String prop = SolrUtils.class.getClassLoader().getResource("").getPath() + "properties/solr-connect.properties";
		prop = URLDecoder.decode(prop);
		Properties properties = CommonUtils.getProperties(prop);
		String solrUrl = properties.getProperty("url");
		// 创建solrClient同时指定超时时间，不指定走默认配置
		HttpSolrClient client = new HttpSolrClient.Builder(solrUrl).withConnectionTimeout(10000)
				.withSocketTimeout(60000).build();

		/* [2]封装查询参数 */
		SolrQuery query = new SolrQuery();
		// 添加要查询的内容
		query.addField("id");// id
		query.addField("title");// 标题
		query.addField("content");// 内容
		query.addField("leave_time");// 发布时间
		query.addField("view_num");// 阅览量
		query.addField("comment_num");// 评论量
		query.addField("uid");// 作者id
		query.addField("uname");// 作者昵称
		// 设置查询条件
		query.set("q", "content_rich:" + condition + " or title:" + condition);// 条件
		query.setStart(start);// 开始
		query.setRows(nums);// 多少条
		// 高亮设置
		query.setHighlight(true);// 开启高亮
		query.setHighlightSimplePre("<font color='red'>");// 高亮显示的格式
		query.setHighlightSimplePost("</font>");
		query.setParam("hl.fl", "title or content");// 需要高亮的字段

		/* [3]执行查询返回QueryResponse */
		QueryResponse response = client.query(query);

		/* [4]获取高亮内容 */
		Map<String, Map<String, List<String>>> map = response.getHighlighting();

		/* [5]获取doc文档 */
		SolrDocumentList documents = response.getResults();

		long records = documents.getNumFound();
		int totalPages;
		double temp = (double)records / (double)nums;
		if (temp == (int) temp) {
			totalPages = (int) temp;
		} else {
			totalPages = (int) temp + 1;
		}

		/* [6]内容遍历 */
		for (SolrDocument doc : documents) {
			// 取出数据并做好相应的转换
			String id = doc.get("id").toString();
			String title = "";
			if (map.get(id).get("title") != null) {
				title = map.get(id).get("title").get(0);
			} else {
				title = doc.get("title").toString();
			}
			String content = "";
			if (map.get(id).get("content") != null) {
				content = map.get(id).get("content").get(0);
			} else {
				content = doc.get("content").toString();
			}
			String leaveTime = DateUtils.dateFormatCST(CommonUtils.cutForeArt(doc.get("leave_time").toString()));
			String commentNum = CommonUtils.cutForeArt(doc.get("comment_num").toString());
			String viewNum = CommonUtils.cutForeArt(doc.get("view_num").toString());
			String uid = CommonUtils.cutForeArt(doc.get("uid").toString());
			String nickname = doc.get("uname").toString();

			// 封装数据
			ArticalSolrDto articalSolrDto = new ArticalSolrDto();
			articalSolrDto.setId(Long.parseLong(id));
			articalSolrDto.setTitle(title);
			articalSolrDto.setContent(content);
			articalSolrDto.setPubTime(leaveTime);
			articalSolrDto.setViewNum(viewNum);
			articalSolrDto.setCommentNum(commentNum);
			articalSolrDto.setUid(Long.parseLong(uid));
			articalSolrDto.setNickname(nickname);

			// 加入集合
			articalSolrDtos.add(articalSolrDto);
		}

		List<Object> list = new ArrayList<Object>();
		for (ArticalSolrDto art : articalSolrDtos) {
			list.add(art);
		}
		feedback.setoList(list);
		feedback.setTotalPages(totalPages);

		/* [7]关闭 */
		client.close();

		logger.info(articalSolrDtos);
		logger.info(CommonElement.separator + "solr查询，博客定制结束" + CommonElement.separator);
		return feedback;
	}

	public static void main(String[] args) {
		List<ArticalSolrDto> list = new ArrayList<ArticalSolrDto>();

		try {
			PagesFeedback feedback = SolrUtils.solrQueryArticals("solr", 0, 10);
			System.out.println(feedback.getoList());
			System.out.println(feedback.getTotalPages());
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
