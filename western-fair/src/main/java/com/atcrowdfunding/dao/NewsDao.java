package com.atcrowdfunding.dao;

import java.util.List;
import java.util.Map;

import com.atcrowdfunding.bean.News;
import com.atcrowdfunding.bean.NewsType;

public interface NewsDao {
	//分页显示新闻列表
	List<News> pageQueryData(Map<String, Object> map);
	//查询页数
	int pageQueryCount(Map<String, Object> map);
	
	//查询新闻活动类型
	List<NewsType> findNewsType();
	
	//添加新闻活动
	int insertNews(News news);
	
	//根据id获取新闻内容
	News findNews(int newsid);
	
	//根据id更新新闻活动内容
	int updateNews(News news);
	
	//根据id删除一条新闻活动
	int deleteNews(int newsid);
	
	//批量删除
	int deleteNewsList(Map<String, Object> map);
	
	//查找头条新闻
	News findTopNews(int top);
	
	//查找最新新闻
	List<News> findLastedNews();
	
	//查找首页新闻的id
	int findNewsWhereTop();
	
	//设置新首页新闻后将原来的首页新闻top属性置零
	int cancelTop(int newsid);
}
