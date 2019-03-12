package com.atcrowdfunding.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.atcrowdfunding.bean.NewsType;
import com.atcrowdfunding.dao.NewsTypeDao;
import com.atcrowdfunding.service.NewsTypeService;

@Service
public class NewsTypeServiceImpl implements NewsTypeService {
	@Autowired
	private NewsTypeDao newsTypeDao;

	@Override
	public List<NewsType> findNewsType() {
		return newsTypeDao.findNewsType();
	}

	
	@Override
	public List<NewsType> pageQueryData(Map<String, Object> map) {
		return newsTypeDao.pageQueryData(map);
	}

	@Override
	public int pageQueryCount(Map<String, Object> map) {
		return newsTypeDao.pageQueryCount(map);
	}

	@Override
	public int insertNewsType(NewsType newsType) {
		return newsTypeDao.insertNewsType(newsType);
	}

	@Override
	public int updateNewsType(NewsType newsType) {
		return newsTypeDao.updateNewsType(newsType);
	}

	@Override
	public NewsType findNewsTypeById(int id) {
		return newsTypeDao.findNewsTypeById(id);
	}

	@Override
	public int deleteNewsType(int id) {
		return newsTypeDao.deleteNewsType(id);
	}

	@Override
	public int deleteNewsTypeList(Map<String, Object> map) {
		return newsTypeDao.deleteNewsTypeList(map);
	}

	@Override
	public int findNewsTypeInNews(int id) {
		return newsTypeDao.findNewsTypeInNews(id);
	}

	@Override
	public int findNewsTypeByIds(Map<String, Object> map) {
		return newsTypeDao.findNewsTypeByIds(map);
	}

	
}
