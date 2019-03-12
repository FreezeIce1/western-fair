package com.atcrowdfunding.dao;

import java.util.List;
import java.util.Map;

import com.atcrowdfunding.bean.NewsType;

public interface NewsTypeDao {
	//������������
	List<NewsType> findNewsType();
	// ��ҳ��ʾ����
	List<NewsType> pageQueryData(Map<String, Object> map);

	// ��ѯҳ��
	int pageQueryCount(Map<String, Object> map);

	// ��������
	int insertNewsType(NewsType newsType);

	// ����id�������
	int updateNewsType(NewsType newsType);

	// ����id��ѯ���
	NewsType findNewsTypeById(int id);

	// ����idɾ��һ������
	int deleteNewsType(int id);

	// ����ɾ��
	int deleteNewsTypeList(Map<String, Object> map);

	// ��News�в����Ƿ������õ�type
	int findNewsTypeInNews(int id);

	// ����id������ѯ
	int findNewsTypeByIds(Map<String, Object> map);

}
