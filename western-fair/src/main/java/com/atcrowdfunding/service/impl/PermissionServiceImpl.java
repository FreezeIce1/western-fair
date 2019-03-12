package com.atcrowdfunding.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.atcrowdfunding.bean.Permission;
import com.atcrowdfunding.bean.User;
import com.atcrowdfunding.dao.PermissionDao;
import com.atcrowdfunding.service.PermissionService;

@Service
public class PermissionServiceImpl implements PermissionService{
	@Autowired
	private PermissionDao permissionDao;

	@Override
	public Permission queryRootPermission() {
		return permissionDao.queryRootPermission();
	}

	@Override
	public List<Permission> queryChildPermissions(Integer pid) {
		return permissionDao.queryChildPermissions(pid);
	}

	@Override
	public List<Permission> queryAll() {
		return permissionDao.queryAll();
	}

	@Override
	public void insertPermission(Permission permission) {
		permissionDao.insertPermission(permission);
	}

	@Override
	public Permission queryById(Integer id) {
		return permissionDao.queryById(id);
	}

	@Override
	public void updatePermission(Permission permission) {
		permissionDao.updatePermission(permission);
	}

	@Override
	public void deletePermission(Integer id) {
		permissionDao.deletePermission(id);
	}

	@Override
	public List<Integer> queryPermissionidsByRoleid(Integer roleid) {
		return permissionDao.queryPermissionidsByRoleid(roleid); 
	}

	@Override
	public List<Permission> queryPermissionByUser(User dbuser) {
		return permissionDao.queryPermissionByUser(dbuser);
	}
	
	
}
