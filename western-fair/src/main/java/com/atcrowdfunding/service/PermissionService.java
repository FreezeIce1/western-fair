package com.atcrowdfunding.service;

import java.util.List;

import com.atcrowdfunding.bean.Permission;
import com.atcrowdfunding.bean.User;

public interface PermissionService {

	Permission queryRootPermission();

	List<Permission> queryChildPermissions(Integer pid);

	List<Permission> queryAll();

	void insertPermission(Permission permission);

	Permission queryById(Integer id);

	void updatePermission(Permission permission);

	void deletePermission(Integer id);

	List<Integer> queryPermissionidsByRoleid(Integer roleid);

	List<Permission> queryPermissionByUser(User dbuser);

}
