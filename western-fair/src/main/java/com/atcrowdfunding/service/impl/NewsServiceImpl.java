package com.atcrowdfunding.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.atcrowdfunding.bean.News;
import com.atcrowdfunding.bean.NewsType;
import com.atcrowdfunding.dao.NewsDao;
import com.atcrowdfunding.service.NewsService;

@Service
public class NewsServiceImpl implements NewsService{
	@Autowired
	private NewsDao newsDao;
	
	@Override
	public List<News> pageQueryData(Map<String, Object> map) {
		return newsDao.pageQueryData(map);
	}

	@Override
	public int pageQueryCount(Map<String, Object> map) {
		return newsDao.pageQueryCount(map);
	}

	@Override
	public List<NewsType> findNewsType() {
		return newsDao.findNewsType();
	}

	@Override
	public int insertNews(News news) {
		return newsDao.insertNews(news);
	}

	@Override
	public News findNews(int newsid) {
		return newsDao.findNews(newsid);
	}

	@Override
	public int updateNews(News news) {
		return newsDao.updateNews(news);
	}

	@Override
	public int deleteNews(int newsid) {
		return newsDao.deleteNews(newsid);
	}

	@Override
	public int deleteNewsList(Map<String, Object> map) {
		return newsDao.deleteNewsList(map);
	}

	@Override
	public News findTopNews(int top) {
		return newsDao.findTopNews(top);
	}

	@Override
	public List<News> findLastedNews() {
		return newsDao.findLastedNews();
	}

	@Override
	public int findNewsWhereTop() {
		return newsDao.findNewsWhereTop();
	}

	@Override
	public int cancelTop(int newsid) {
		return newsDao.cancelTop(newsid);
	}

	



}
