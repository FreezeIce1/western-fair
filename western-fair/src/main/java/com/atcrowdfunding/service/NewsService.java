package com.atcrowdfunding.service;

import java.util.List;
import java.util.Map;

import com.atcrowdfunding.bean.News;
import com.atcrowdfunding.bean.NewsType;

public interface NewsService {
	List<News> pageQueryData(Map<String, Object> map);

	int pageQueryCount(Map<String, Object> map);

	List<NewsType> findNewsType();

	int insertNews(News news);

	News findNews(int newsid);

	int updateNews(News news);

	int deleteNews(int newsid);

	int deleteNewsList(Map<String, Object> map);

	// 查找头条新闻
	News findTopNews(int top);

	// 查找最新新闻
	List<News> findLastedNews();

	// 查找首页新闻的id
	int findNewsWhereTop();

	// 设置新首页新闻后将原来的首页新闻top属性置零
	int cancelTop(int newsid);
}
