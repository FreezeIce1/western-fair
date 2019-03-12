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
		//��ȡ�û���ǰ�������ַ
		String uri = request.getRequestURI();
		HttpSession session = request.getSession();
		//�жϵ�ǰ·���Ƿ���Ҫ����Ȩ����֤
		//����ͨ����ѯ������Ҫ��֤��·������  ���жϵ�ǰ·���Ƿ���Ҫ����Ȩ����֤
		List<Permission> permissions = permissionService.queryAll();
		//��Ϊ��ϣ��·���ظ�  ����ʹ��Set
		//��ʾ��ҪȨ����֤��·������
		Set<String> uriSet = new HashSet<String>();
		for(Permission permission:permissions) {
			//Ϊ�˱����ָ���쳣  ��""д��ǰ��
			if(permission.getUrl() != null || !"".equals(permission.getUrl())) {
				uriSet.add(request.getContextPath()+permission.getUrl());
			}
		}
		
		if(uriSet.contains(uri)) {
			//��ʾ�û�����ĵ�ǰ·������Ҫ����Ȩ����֤��
			//�жϵ�ǰ�û��Ƿ���е�ǰȨ��  ��ȡ��ǰ�û����Է��ʵ�Ȩ��·������
			Set<String> authUriSet = (Set<String>)session.getAttribute("authUriSet");
			if(authUriSet.contains(uri)) {
				//˵���û�����Ȩ��
				return true;
			}else {
				response.sendRedirect(request.getContextPath()+"/error");
				return false;
			}
		}else {
			//��ʾ����Ҫ����Ȩ����֤  ����
			return true;
		}
		
		
	}
}
