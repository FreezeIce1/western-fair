package com.atcrowdfunding.dao;

import java.util.List;
import java.util.Map;

import com.atcrowdfunding.bean.NewsType;

public interface NewsTypeDao {
	//查找所有类型
	List<NewsType> findNewsType();
	// 分页显示类型
	List<NewsType> pageQueryData(Map<String, Object> map);

	// 查询页数
	int pageQueryCount(Map<String, Object> map);

	// 增添类型
	int insertNewsType(NewsType newsType);

	// 根据id更新类别
	int updateNewsType(NewsType newsType);

	// 根据id查询类别
	NewsType findNewsTypeById(int id);

	// 根据id删除一条类型
	int deleteNewsType(int id);

	// 批量删除
	int deleteNewsTypeList(Map<String, Object> map);

	// 在News中查找是否有在用的type
	int findNewsTypeInNews(int id);

	// 根据id批量查询
	int findNewsTypeByIds(Map<String, Object> map);

}
