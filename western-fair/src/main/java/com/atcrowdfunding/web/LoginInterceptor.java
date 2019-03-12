package com.atcrowdfunding.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.atcrowdfunding.bean.User;

/**
 * 登录拦截器  -- 因为是SpringMvc提供的  需要在SpringMvc的核心配置文件中进行相应配置
 * @author ACER
 *
 */

public class LoginInterceptor implements HandlerInterceptor {

	/**
	 * 在控制器执行之前执行此方法  完成业务逻辑操作
	 * 方法返回的结果会影响控制器的执行
	 * 返回true代表放行  执行控制器中的相应方法
	 * 返回false代表拦截 不会执行控制器中的的相应方法
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
		//判断用户是否已经登录
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
	 * 在控制器执行完毕之后执行此方法 完成的逻辑操作
	 * 因为不会影响控制器的执行  所以没有返回值
	 */
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		// TODO Auto-generated method stub

	}

	/**
	 * 在视图渲染完成之后  执行此方法  完成一些逻辑操作
	 */
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// TODO Auto-generated method stub

	}

}
