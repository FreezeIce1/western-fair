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
	
	//跳转到添加许可信息页面
	@RequestMapping("/add")
	public String add() {
		return "permission/add";
	}
	
	//跳转到修改许可信息页面
	@RequestMapping("/edit")
	public String edit(Integer id,Model model) {
		
		Permission permission = permissionService.queryById(id);
		model.addAttribute("permission", permission);
		
		return "permission/edit";
	}
	
	//修改许可信息的方法
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
	
	//删除许可信息
	
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
	
	//添加许可信息的方法
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
	
	//查询已经为某个角色分配的许可（菜单）信息
	@ResponseBody
	@RequestMapping("/loadAssignData")
	public Object loadAssignData(Integer roleid) {
		List<Permission> permissions = new ArrayList<Permission>();
		List<Permission> ps = permissionService.queryAll();
		
		//获取当前角色已经分配的许可信息
		List<Integer> permissionids = permissionService.queryPermissionidsByRoleid(roleid);
		Map<Integer,Permission> permissionMap = new HashMap<Integer,Permission>();
		for(Permission p:ps) {
			if(permissionids.contains(p.getId())) {
				p.setChecked(true);
			}else {
				p.setChecked(false);
			}
			//设置每个节点的索引为其id
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
		
		//读取数据库中菜单表(t_permission)中的数据  并组成树形结构
		//通常认为根节点就是pid为空的结点，且只有一个
		
		
		//这种方式并不通用   当树形结构很复杂的时候  代码就会很多  但是思路是一样的
		/*Permission root = permissionService.queryRootPermission();
		
		//获取顶级结点的子节点
		List<Permission> childPermissions = permissionService.queryChildPermissions(root.getId());
		for(Permission childPermission:childPermissions) {
			List<Permission> childChildPermissions = permissionService.queryChildPermissions(childPermission.getId());
			childPermission.setChildren(childChildPermissions);
		}
		
		root.setChildren(childPermissions);
		permissions.add(root);*/
		
		//递归查询数据--查询数据库次数太多  效率不太高
		//为了统一进行递归处理  将根节点的父节点pid设置为0
		/*Permission parent = new Permission();
		parent.setId(0);
		queryChildPermission(parent);
		return parent.getChildren();*/
		
		//查询所有许可数据(菜单信息) --- 只查询一次数据库 
		/*List<Permission> ps = permissionService.queryAll();
		//依次找到父节点及其对应的子节点  然后再组合他们之间的关系
		for(Permission p:ps) {
			//把每个节点都当成子节点  然后再相应的去找父节点
			Permission child = p;
			if(p.getPid() == 0) {
				//表示该节点是顶级节点
				permissions.add(p);
			}else {
				for(Permission innerPermission:ps) {
					//循环遍历所有的节点 判断该子节点的父节点
					if(child.getPid().equals(innerPermission.getId())) {
						//获取到该节点的父节点
						Permission parent = innerPermission;
						//组合父节点和字节点之间的关系
						parent.getChildren().add(child);
						//一个父节点只有一个子节点
						break;
					}
				}
			}
		}*/
		
		
		//ArrayList因为使用到了索引  所以查询速度会比较快些  
		//但上述方式中并没有使用到索引  所以还是线性查找   但数据量大的时候  效率还是比较低的
		//所以以下采取map的方式  map是一定使用到了索引
		//相比上述方式  这里只用到了一层循环  效率提高很多
		List<Permission> ps = permissionService.queryAll();
		Map<Integer,Permission> permissionMap = new HashMap<Integer,Permission>();
		for(Permission p:ps) {
			//设置每个节点的索引为其id
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
	 * 递归查询许可（菜单）信息
	 * 使用递归算法应该注意：
	 * 1）递归方法自己调用自己
	 * 2）方法一定要存在跳出的条件
	 * 3）方法调用时，参数之间应该要有规律
	 * 
	 * 递归算法虽然代码简单  逻辑清晰  但效率比较低（操作数据库的次数太多）  
	 * 一般在实际在开发中不会使用
	 * @param parent
	 */
	private void queryChildPermission(Permission parent) {
		List<Permission> childPermissions = permissionService.queryChildPermissions(parent.getId());
		
		//此处存在跳出逻辑  当查询到的子节点childPermissions为空时  下面循环就无法执行
		for(Permission permission:childPermissions) {
			//递归调用
			queryChildPermission(permission);
		}
		
		parent.setChildren(childPermissions);
	}
}
