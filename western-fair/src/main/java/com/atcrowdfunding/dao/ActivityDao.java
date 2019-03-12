package com.atcrowdfunding.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Select;

import com.atcrowdfunding.bean.Activity;

public interface ActivityDao {
	
	@Select("select * from xbh_activity")
	List<Activity> queryAll();
	
	@Select("select * from xbh_activity where id = #{id}")
	Activity queryById(Integer id);

	void insertActivity(Activity activity);

	void updateActivity(Activity activity);

	void deleteActivityById(Integer id);

	void deleteActivities(Map<String, Object> map);

	List<Activity> pageQueryData(Map<String, Object> map);

	int pageQueryCount(Map<String, Object> map);
	
	
}
