package com.atcrowdfunding.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.atcrowdfunding.bean.Activity;
import com.atcrowdfunding.bean.User;
import com.atcrowdfunding.dao.ActivityDao;
import com.atcrowdfunding.dao.UserDao;
import com.atcrowdfunding.service.ActivityService;

@Service
public class ActivityServiceImpl implements ActivityService {

	@Autowired
	private ActivityDao activityDao;
	
	@Override
	public Activity queryById(Integer id) {
		// TODO Auto-generated method stub
		return activityDao.queryById(id);
	}

	@Override
	public void insertActivity(Activity activity) {
		// TODO Auto-generated method stub
		activityDao.insertActivity(activity);
	}

	@Override
	public void updateActivity(Activity activity) {
		// TODO Auto-generated method stub
		activityDao.updateActivity(activity);
	}

	@Override
	public void deleteActivityById(Integer id) {
		// TODO Auto-generated method stub
		activityDao.deleteActivityById(id);
	}

	@Override
	public void deleteActivities(Map<String, Object> map) {
		// TODO Auto-generated method stub
		activityDao.deleteActivities(map);
	}

	@Override
	public List<Activity> pageQueryData(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return activityDao.pageQueryData(map);
	}

	@Override
	public int pageQueryCount(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return activityDao.pageQueryCount(map);
	}

	@Override
	public List<Activity> queryAll() {
		// TODO Auto-generated method stub
		return activityDao.queryAll();
	}

	

}
