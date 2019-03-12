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

	// ����ͷ������
	News findTopNews(int top);

	// ������������
	List<News> findLastedNews();

	// ������ҳ���ŵ�id
	int findNewsWhereTop();

	// ��������ҳ���ź�ԭ������ҳ����top��������
	int cancelTop(int newsid);
}
