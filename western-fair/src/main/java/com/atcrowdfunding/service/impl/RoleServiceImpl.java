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
		//��ɾ��֮ǰ�ĸý�ɫ��Ӧ�����Ϣ   ���������Ӹý�ɫ��Ӧ�������Ϣ  �����ظ�
		roleDao.deleteRolePermissions(paramMap);
		roleDao.insertRolePermission(paramMap);
	}

}
