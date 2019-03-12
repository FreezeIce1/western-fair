package com.atcrowdfunding.service;

import java.util.List;
import java.util.Map;

import com.atcrowdfunding.bean.NewsType;

public interface NewsTypeService {
	// ������������
	List<NewsType> findNewsType();

	// ��ҳ��ʾ�����б�
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
