package com.atcrowdfunding.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.atcrowdfunding.bean.AJAXResult;
import com.atcrowdfunding.bean.Permission;
import com.atcrowdfunding.service.PermissionService;

@Controller
@RequestMapping("/permission")
public class PermissionController {
	@Autowired
	private PermissionService permissionService;
	
	@RequestMapping("/index")
	public String index() {
		return "permission/index";
	}
	
	//��ת����������Ϣҳ��
	@RequestMapping("/add")
	public String add() {
		return "permission/add";
	}
	
	//��ת���޸������Ϣҳ��
	@RequestMapping("/edit")
	public String edit(Integer id,Model model) {
		
		Permission permission = permissionService.queryById(id);
		model.addAttribute("permission", permission);
		
		return "permission/edit";
	}
	
	//�޸������Ϣ�ķ���
	@ResponseBody
	@RequestMapping("/update")
	public Object update(Permission permission) {
		AJAXResult result = new AJAXResult();
		
		try {
			permissionService.updatePermission(permission);
			result.setSuccess(true);
		}catch(Exception e) {
			e.printStackTrace();
			result.setSuccess(false);
		}
		
		return result;
	}
	
	//ɾ�������Ϣ
	
	@ResponseBody
	@RequestMapping("/delete")
	public Object delete(Integer id) {
		AJAXResult result = new AJAXResult();
		
		try {
			permissionService.deletePermission(id);
			result.setSuccess(true);
		}catch(Exception e) {
			e.printStackTrace();
			result.setSuccess(false);
		}
		
		return result;
	}
	
	//��������Ϣ�ķ���
	@ResponseBody
	@RequestMapping("/insert")
	public Object insert(Permission permission) {
		AJAXResult result = new AJAXResult();
		
		try {
			permissionService.insertPermission(permission);
			result.setSuccess(true);
		}catch(Exception e) {
			e.printStackTrace();
			result.setSuccess(false);
		}
		
		return result;
	}
	
	//��ѯ�Ѿ�Ϊĳ����ɫ�������ɣ��˵�����Ϣ
	@ResponseBody
	@RequestMapping("/loadAssignData")
	public Object loadAssignData(Integer roleid) {
		List<Permission> permissions = new ArrayList<Permission>();
		List<Permission> ps = permissionService.queryAll();
		
		//��ȡ��ǰ��ɫ�Ѿ�����������Ϣ
		List<Integer> permissionids = permissionService.queryPermissionidsByRoleid(roleid);
		Map<Integer,Permission> permissionMap = new HashMap<Integer,Permission>();
		for(Permission p:ps) {
			if(permissionids.contains(p.getId())) {
				p.setChecked(true);
			}else {
				p.setChecked(false);
			}
			//����ÿ���ڵ������Ϊ��id
			permissionMap.put(p.getId(), p);
		}
		for(Permission p:ps) {
			Permission child = p;
			if(child.getPid() == 0) {
				permissions.add(p);
			}else {
				Permission parent = permissionMap.get(child.getPid());
				parent.getChildren().add(child);
			}
		}
		return permissions;
	}
	
	@ResponseBody
	@RequestMapping("/loadData")
	public Object loadData() {
		List<Permission> permissions = new ArrayList<Permission>();
		
		//��ȡ���ݿ��в˵���(t_permission)�е�����  ��������νṹ
		//ͨ����Ϊ���ڵ����pidΪ�յĽ�㣬��ֻ��һ��
		
		
		//���ַ�ʽ����ͨ��   �����νṹ�ܸ��ӵ�ʱ��  ����ͻ�ܶ�  ����˼·��һ����
		/*Permission root = permissionService.queryRootPermission();
		
		//��ȡ���������ӽڵ�
		List<Permission> childPermissions = permissionService.queryChildPermissions(root.getId());
		for(Permission childPermission:childPermissions) {
			List<Permission> childChildPermissions = permissionService.queryChildPermissions(childPermission.getId());
			childPermission.setChildren(childChildPermissions);
		}
		
		root.setChildren(childPermissions);
		permissions.add(root);*/
		
		//�ݹ��ѯ����--��ѯ���ݿ����̫��  Ч�ʲ�̫��
		//Ϊ��ͳһ���еݹ鴦��  �����ڵ�ĸ��ڵ�pid����Ϊ0
		/*Permission parent = new Permission();
		parent.setId(0);
		queryChildPermission(parent);
		return parent.getChildren();*/
		
		//��ѯ�����������(�˵���Ϣ) --- ֻ��ѯһ�����ݿ� 
		/*List<Permission> ps = permissionService.queryAll();
		//�����ҵ����ڵ㼰���Ӧ���ӽڵ�  Ȼ�����������֮��Ĺ�ϵ
		for(Permission p:ps) {
			//��ÿ���ڵ㶼�����ӽڵ�  Ȼ������Ӧ��ȥ�Ҹ��ڵ�
			Permission child = p;
			if(p.getPid() == 0) {
				//��ʾ�ýڵ��Ƕ����ڵ�
				permissions.add(p);
			}else {
				for(Permission innerPermission:ps) {
					//ѭ���������еĽڵ� �жϸ��ӽڵ�ĸ��ڵ�
					if(child.getPid().equals(innerPermission.getId())) {
						//��ȡ���ýڵ�ĸ��ڵ�
						Permission parent = innerPermission;
						//��ϸ��ڵ���ֽڵ�֮��Ĺ�ϵ
						parent.getChildren().add(child);
						//һ�����ڵ�ֻ��һ���ӽڵ�
						break;
					}
				}
			}
		}*/
		
		
		//ArrayList��Ϊʹ�õ�������  ���Բ�ѯ�ٶȻ�ȽϿ�Щ  
		//��������ʽ�в�û��ʹ�õ�����  ���Ի������Բ���   �����������ʱ��  Ч�ʻ��ǱȽϵ͵�
		//�������²�ȡmap�ķ�ʽ  map��һ��ʹ�õ�������
		//���������ʽ  ����ֻ�õ���һ��ѭ��  Ч����ߺܶ�
		List<Permission> ps = permissionService.queryAll();
		Map<Integer,Permission> permissionMap = new HashMap<Integer,Permission>();
		for(Permission p:ps) {
			//����ÿ���ڵ������Ϊ��id
			permissionMap.put(p.getId(), p);
		}
		for(Permission p:ps) {
			Permission child = p;
			if(child.getPid() == 0) {
				permissions.add(p);
			}else {
				Permission parent = permissionMap.get(child.getPid());
				parent.getChildren().add(child);
			}
		}
		return permissions;
	}
	
	/**
	 * �ݹ��ѯ��ɣ��˵�����Ϣ
	 * ʹ�õݹ��㷨Ӧ��ע�⣺
	 * 1���ݹ鷽���Լ������Լ�
	 * 2������һ��Ҫ��������������
	 * 3����������ʱ������֮��Ӧ��Ҫ�й���
	 * 
	 * �ݹ��㷨��Ȼ�����  �߼�����  ��Ч�ʱȽϵͣ��������ݿ�Ĵ���̫�ࣩ  
	 * һ����ʵ���ڿ����в���ʹ��
	 * @param parent
	 */
	private void queryChildPermission(Permission parent) {
		List<Permission> childPermissions = permissionService.queryChildPermissions(parent.getId());
		
		//�˴����������߼�  ����ѯ�����ӽڵ�childPermissionsΪ��ʱ  ����ѭ�����޷�ִ��
		for(Permission permission:childPermissions) {
			//�ݹ����
			queryChildPermission(permission);
		}
		
		parent.setChildren(childPermissions);
	}
}
