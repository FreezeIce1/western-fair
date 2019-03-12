package com.atcrowdfunding.dao;

import java.util.List;
import java.util.Map;

import com.atcrowdfunding.bean.News;
import com.atcrowdfunding.bean.NewsType;

public interface NewsDao {
	//��ҳ��ʾ�����б�
	List<News> pageQueryData(Map<String, Object> map);
	//��ѯҳ��
	int pageQueryCount(Map<String, Object> map);
	
	//��ѯ���Ż����
	List<NewsType> findNewsType();
	
	//������Ż
	int insertNews(News news);
	
	//����id��ȡ��������
	News findNews(int newsid);
	
	//����id�������Ż����
	int updateNews(News news);
	
	//����idɾ��һ�����Ż
	int deleteNews(int newsid);
	
	//����ɾ��
	int deleteNewsList(Map<String, Object> map);
	
	//����ͷ������
	News findTopNews(int top);
	
	//������������
	List<News> findLastedNews();
	
	//������ҳ���ŵ�id
	int findNewsWhereTop();
	
	//��������ҳ���ź�ԭ������ҳ����top��������
	int cancelTop(int newsid);
}
