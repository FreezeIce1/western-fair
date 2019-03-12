package com.atcrowdfunding.service;

import java.util.List;
import java.util.Map;

import com.atcrowdfunding.bean.Activity;

public interface ActivityService {

	List<Activity> queryAll();
	
	Activity queryById(Integer id);

	void insertActivity(Activity activity);

	void updateActivity(Activity activity);

	void deleteActivityById(Integer id);

	void deleteActivities(Map<String, Object> map);

	List<Activity> pageQueryData(Map<String, Object> map);

	int pageQueryCount(Map<String, Object> map);
}
