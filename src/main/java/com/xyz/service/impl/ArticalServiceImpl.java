package com.xyz.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xyz.domain.Artical;
import com.xyz.domain.ArticalAssCategoryKey;
import com.xyz.domain.ArticalCategory;
import com.xyz.domain.File;
import com.xyz.domain.User;
import com.xyz.mapper.ArticalAssCategoryMapper;
import com.xyz.mapper.ArticalMapper;
import com.xyz.mapper.ArticalMapperP;
import com.xyz.service.ArticalCategoryService;
import com.xyz.service.ArticalService;
import com.xyz.service.FileService;
import com.xyz.service.UserService;

@Transactional
@Service("articalService")
public class ArticalServiceImpl implements ArticalService {

	@Autowired
	private ArticalMapper articalMapper;

	@Autowired
	private ArticalMapperP articalMapperP;

	@Autowired
	private ArticalAssCategoryMapper articalAssCategoryMapper;

	@Autowired
	@Qualifier("articalCategoryService")
	private ArticalCategoryService articalCategoryService;

	@Autowired
	@Qualifier("fileService")
	private FileService fileService;
	
	@Autowired
	@Qualifier("userService")
	private UserService userService;

	/**
	 * 获取一篇博客，根据id
	 */
	@Override
	public Artical getAppointedItem(Long uid) {
		Artical artical = articalMapper.selectByPrimaryKey(uid);
		ArticalAssCategoryKey articalAssCategoryKey = articalAssCategoryMapper.selectOne(uid);
		artical.setCategory(articalAssCategoryKey.getCid());
		User user = userService.getAppointedItem(artical.getUid());
		artical.setUser(user);
		return artical;
	}

	@Override
	public boolean changeAppointedItem(Artical item) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean saveAppointedItem(Artical item) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean cutAppointedItem(Long id) {
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 * 根据条件获取指定页的文章
	 */
	public PageInfo<Artical> getAppointedPageItems(Integer current, Integer limit, Artical item) {
		PageHelper.startPage(current, limit);
		return new PageInfo<Artical>(articalMapperP.selectPages(item));
	}

	/**
	 * 获取文章统计信息
	 */
	@Override
	public int getCount() {
		return articalMapperP.selectCount();
	}

	/**
	 * 写一篇博客
	 */
	@Override
	public boolean writeOneBlog(Artical artical, ArticalCategory articalCategory, File file) {
		int count = 0;

		count += articalMapper.insertSelective(artical);

		Integer cid = articalCategory.getId();
		Integer pid = articalCategoryService.getAppointedItem(cid).getPid();

		ArticalAssCategoryKey ac1 = new ArticalAssCategoryKey();
		ac1.setAid(artical.getId());
		ac1.setCid(pid);
		ArticalAssCategoryKey ac2 = new ArticalAssCategoryKey();
		ac2.setAid(artical.getId());
		ac2.setCid(cid);

		count += articalAssCategoryMapper.insertSelective(ac1);
		count += articalAssCategoryMapper.insertSelective(ac2);

		// if (file.getFilename() != null &&
		// fileService.saveAppointedItem(file)) {
		// count++;
		// return count == 4;
		// }

		return count == 3;
	}

	/**
	 * 修改一篇博客
	 */
	@Override
	public boolean modifyOneBlog(Artical artical, ArticalCategory articalCategory, File file) {
		int count = 0;

		count += articalMapper.updateByPrimaryKey(artical);

		Integer cid = articalCategory.getId();
		Integer pid = articalCategoryService.getAppointedItem(cid).getPid();

		ArticalAssCategoryKey ac1 = new ArticalAssCategoryKey();
		ac1.setAid(artical.getId());
		ac1.setCid(pid);
		ArticalAssCategoryKey ac2 = new ArticalAssCategoryKey();
		ac2.setAid(artical.getId());
		ac2.setCid(cid);

		count += articalAssCategoryMapper.deleteByPrimaryKey(ac1);
		count += articalAssCategoryMapper.deleteByPrimaryKey(ac2);
		count += articalAssCategoryMapper.insertSelective(ac1);
		count += articalAssCategoryMapper.insertSelective(ac2);

		// if (file.getFilename() != null &&
		// fileService.saveAppointedItem(file)) {
		// count++;
		// return count == 6;
		// }

		return count == 5;
	}

}
