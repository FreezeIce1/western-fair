package com.atcrowdfunding.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.atcrowdfunding.bean.User;

/**
 * ��¼������  -- ��Ϊ��SpringMvc�ṩ��  ��Ҫ��SpringMvc�ĺ��������ļ��н�����Ӧ����
 * @author ACER
 *
 */

public class LoginInterceptor implements HandlerInterceptor {

	/**
	 * �ڿ�����ִ��֮ǰִ�д˷���  ���ҵ���߼�����
	 * �������صĽ����Ӱ���������ִ��
	 * ����true�������  ִ�п������е���Ӧ����
	 * ����false�������� ����ִ�п������еĵ���Ӧ����
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
		//�ж��û��Ƿ��Ѿ���¼
		HttpSession session = request.getSession();
		User loginUser = (User)session.getAttribute("loginUser");
		
		if(loginUser == null) {
			response.sendRedirect(request.getContextPath()+"/login");
			return false;
		}else {
			return true;
		}
		
		
	}

	/**
	 * �ڿ�����ִ�����֮��ִ�д˷��� ��ɵ��߼�����
	 * ��Ϊ����Ӱ���������ִ��  ����û�з���ֵ
	 */
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		// TODO Auto-generated method stub

	}

	/**
	 * ����ͼ��Ⱦ���֮��  ִ�д˷���  ���һЩ�߼�����
	 */
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// TODO Auto-generated method stub

	}

}
