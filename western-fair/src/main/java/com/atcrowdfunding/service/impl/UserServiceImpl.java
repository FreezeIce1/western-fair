package com.atcrowdfunding.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.atcrowdfunding.bean.User;
import com.atcrowdfunding.dao.UserDao;
import com.atcrowdfunding.service.UserService;

@Service
public class UserServiceImpl implements UserService {
	@Autowired
	private UserDao userDao;

	@Override
	public List<User> queryAll() {
		return userDao.queryAll();
	}

	@Override
	public User query4Login(User user) {
		
		return userDao.query4Login(user);
	}

	@Override
	public List<User> pageQueryData(Map<String, Object> map) {
		
		return userDao.pageQueryData(map);
	}

	@Override
	public int pageQueryCount(Map<String, Object> map) {
		return userDao.pageQueryCount(map);
	}

	@Override
	public void insertUser(User user) {
		
		userDao.insertUser(user);
	}

	@Override
	public User queryById(Integer id) {
		return userDao.queryById(id);
	}

	@Override
	public void updateUser(User user) {
		userDao.updateUser(user);
	}

	@Override
	public void deleteUserById(Integer id) {
		userDao.deleteUserById(id);
	}

	@Override
	public void deleteUsers(Map<String, Object> map) {
		userDao.deleteUsers(map);
		
	}

	@Override
	public void insertUserRoles(Map<String, Object> map) {
		userDao.insertUserRoles(map);
	}

	@Override
	public void deleteUserRoles(Map<String, Object> map) {
		userDao.deleteUsersRoles(map);
	}

	@Override
	public List<Integer> queryAssignRoleIdsByUserId(Integer id) {
		return userDao.queryAssignRoleIdsByUserId(id);
	}

	@Override
	public void updateUserOwnInfo(User user) {
		userDao.updateUserOwnInfo(user);
	}

	@Override
	public void updateUserLoginInfo(User user) {
		userDao.updateUserLoginInfo(user);
	}

	@Override
	public User queryUserByLoginacct(String loginacct) {
		return userDao.queryUserByLoginacct(loginacct);
	}
}
