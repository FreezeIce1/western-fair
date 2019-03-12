package com.atcrowdfunding.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.atcrowdfunding.bean.AJAXResult;
import com.atcrowdfunding.bean.Permission;
import com.atcrowdfunding.bean.RandomValidateCode;
import com.atcrowdfunding.bean.User;
import com.atcrowdfunding.service.PermissionService;
import com.atcrowdfunding.service.UserService;
import com.atcrowdfunding.util.MD5Utils;

@Controller
public class DispatcherController {

	@Autowired
	private UserService userService;
	
	@Autowired
	private PermissionService permissionService;
	
	@RequestMapping("/login")
	public String login() {
		return "login";
	}
	
	@RequestMapping("/main")
	public String main() {
		
		return "main";
	}
	
	@RequestMapping("/error")
	public String error() {
		
		return "error";
	}
	
	@RequestMapping(value="/checkCode")
	public String checkCode(HttpServletRequest request,HttpServletResponse response) throws Exception{
		 response.setContentType("image/jpeg");
		 
		 response.setHeader("pragma", "no-cache");
         response.setHeader("Cache-Control", "no-cache");
         response.setDateHeader("Expire", 0);
        
         RandomValidateCode randomValidateCode = new RandomValidateCode();
         try {
            randomValidateCode.getRandcode(request, response);//输出图片方法
         } catch (Exception e) {
             e.printStackTrace();
         } 
         
         return null;
	}
	
	//Ajax线程是不会跳转页面的  因为跳转页面时UI线程的任务  
	//所以此处方法的返回值不是String  而是Object(代表返回页面的数据)
	//但对象在网络中是无法进行传送的,也无法传递数字，只能传递传递字符串
	//但传递json格式的字符串{success:""},浏览器可以进行很好的解析
	//所以要是用@ResponseBody将对象转为json的格式的字符串来传送给浏览器
	@ResponseBody
	@RequestMapping("doAjaxLogin")
	public Object doAjaxLogin(User user,HttpSession session,String checkCode) {
		AJAXResult result = new AJAXResult();
	
		//1.先判断用户验证码是否输入争取
		String ramdomCode = (String)session.getAttribute("ramdomCode");
		System.out.println(checkCode);
		if(!ramdomCode.equals(checkCode)) {
			result.setData("验证码不正确");
			result.setSuccess(false);
		}else {
			//因为用户信息表中的密码是加密过的  
			//所以要对用户输入的密码进行加密后  再进行判断
			user.setUserpswd(MD5Utils.md5(user.getUserpswd()));
			
			User dbuser = userService.query4Login(user);
			
			if(dbuser != null) {
				if(dbuser.getLocked() == 1) {
					//表示用户已经被锁定
					result.setSuccess(false);
					result.setData("用户已经被锁定");
				}else {
					//登录成功  
					//将用户登录信息保存到session中
					session.setAttribute("loginUser", dbuser);
					
					//设置用户最后登录时间 和 总共登录次数
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					dbuser.setLastLogin(sdf.format(new Date()));
					dbuser.setLoginCount(dbuser.getLoginCount()+1);
					
					userService.updateUserLoginInfo(dbuser);
					
					//获取用户权限信息
					List<Permission> permissions = permissionService.queryPermissionByUser(dbuser);
					//组合用户权限具有的上下级关系
					Map<Integer,Permission> permissionMap = new HashMap<Integer,Permission>();
					Permission root = null; //保存当前用户可以范文的权限路径的集合
					Set<String> uriSet = new HashSet<String>();
					for(Permission permission:permissions) {
						permissionMap.put(permission.getId(), permission);
						if(permission.getUrl() != null && !"".equals(permission.getUrl())) {
							uriSet.add(session.getServletContext().getContextPath()+permission.getUrl());
						}
					}
					session.setAttribute("authUriSet", uriSet);
					for(Permission permission:permissions) {
						Permission child = permission;
						if(child.getPid() == 0) {
							root = permission;
						}else {
							Permission parent = permissionMap.get(child.getPid());
							parent.getChildren().add(child);
						}
					}
					
					session.setAttribute("rootPermission", root);
					result.setSuccess(true);
				}
			}else {
				//登录失败
				result.setData("用户名或密码不正确");
				result.setSuccess(false);
			}	
		}
		return result;
	}
	
	/**
	 * 实现执行登录功能
	 * @return
	 */
	@RequestMapping("/doLogin")
	public String doLogin(User user,Model model) throws Exception{
		String loginacct = user.getLoginacct();
		
		/**这种一处一处解决乱码的方式并不可取  统一使用spring提供的过滤器进行解决  **/
		/*//将乱码字符串按照错误的编码方式转换为原始的字节码序列
		byte[] bs = loginacct.getBytes("ISO8859-1");
		//将得到的原始的字节码序列按照正确的编码方式转换为正确的文字
		loginacct = new String(bs,"UTF-8");*/
		
		//1)获取表单提交的登录信息
		//1-1) 使用HttpServletRequest对象进行获取
		//1-2) 使用方法参数列表获取表单提交的信息  要求方法参数的名称要和表单组件的name属性值相同
		//1-3) 将表单数据封装为实体类对象，在方法参数中使用一个实体类对象来接收  要求实体类的中的属性名称要和input组件的name属性值相等
		
		
		//2) 根据表单提交的信息查询用户信息
		User dbuser = userService.query4Login(user);
		
		//3) 判断用户信息是否存在
		if(dbuser != null) {
			//登录成功  跳转到主页面  默认使用转发的方式
			return "main";
		}else {
			//登录失败 跳转回到登录页面  并且提示错误信息
			String errorMsg = "登录账号或密码不正确，请重新输入";
			model.addAttribute("errorMsg",errorMsg);
			return "redirect:login";
		}
	}
	
	@RequestMapping("/logout")
	public String logout(HttpSession session) {
		
		//设置用户的默认密码
		session.invalidate();
		//重定向到当前类中的login方法中
		return "redirect:login";
		//return "login";  请求转发到login.jsp
	}
}
