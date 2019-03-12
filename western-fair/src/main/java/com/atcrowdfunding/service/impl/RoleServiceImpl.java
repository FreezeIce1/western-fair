package com.atcrowdfunding.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.atcrowdfunding.bean.Role;
import com.atcrowdfunding.dao.RoleDao;
import com.atcrowdfunding.service.RoleService;

@Service
public class RoleServiceImpl implements RoleService {

	@Autowired
	private RoleDao roleDao;


	public List<Role> pageQueryData(Map<String, Object> map) {
		return roleDao.pageQueryData(map);
	}

	public int pageQueryCount(Map<String, Object> map) {
		return roleDao.pageQueryCount(map);
	}

	public List<Role> queryAll() {
		return roleDao.queryAll();
	}

	@Override
	public void insertRolePermission(Map<String, Object> paramMap) {
		//先删除之前的该角色对应许可信息   再重新增加该角色对应的许可信息  避免重复
		roleDao.deleteRolePermissions(paramMap);
		roleDao.insertRolePermission(paramMap);
	}

}
