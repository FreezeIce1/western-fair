package com.atcrowdfunding.web;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.atcrowdfunding.bean.Permission;
import com.atcrowdfunding.service.PermissionService;

public class AuthInterceptor extends HandlerInterceptorAdapter {
	
	@Autowired
	private PermissionService permissionService;
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		//获取用户当前的请求地址
		String uri = request.getRequestURI();
		HttpSession session = request.getSession();
		//判断当前路径是否需要进行权限验证
		//可以通过查询所有需要验证的路径集合  再判断当前路径是否需要进行权限验证
		List<Permission> permissions = permissionService.queryAll();
		//因为不希望路径重复  所以使用Set
		//表示需要权限验证的路径集合
		Set<String> uriSet = new HashSet<String>();
		for(Permission permission:permissions) {
			//为了避免空指针异常  将""写在前面
			if(permission.getUrl() != null || !"".equals(permission.getUrl())) {
				uriSet.add(request.getContextPath()+permission.getUrl());
			}
		}
		
		if(uriSet.contains(uri)) {
			//表示用户请求的当前路径是需要进行权限验证的
			//判断当前用户是否具有当前权限  获取当前用户可以访问的权限路径集合
			Set<String> authUriSet = (Set<String>)session.getAttribute("authUriSet");
			if(authUriSet.contains(uri)) {
				//说明用户具有权限
				return true;
			}else {
				response.sendRedirect(request.getContextPath()+"/error");
				return false;
			}
		}else {
			//表示不需要进行权限验证  放行
			return true;
		}
		
		
	}
}
