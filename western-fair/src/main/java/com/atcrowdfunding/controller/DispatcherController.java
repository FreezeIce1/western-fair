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
            randomValidateCode.getRandcode(request, response);//���ͼƬ����
         } catch (Exception e) {
             e.printStackTrace();
         } 
         
         return null;
	}
	
	//Ajax�߳��ǲ�����תҳ���  ��Ϊ��תҳ��ʱUI�̵߳�����  
	//���Դ˴������ķ���ֵ����String  ����Object(������ҳ�������)
	//�����������������޷����д��͵�,Ҳ�޷��������֣�ֻ�ܴ��ݴ����ַ���
	//������json��ʽ���ַ���{success:""},��������Խ��кܺõĽ���
	//����Ҫ����@ResponseBody������תΪjson�ĸ�ʽ���ַ��������͸������
	@ResponseBody
	@RequestMapping("doAjaxLogin")
	public Object doAjaxLogin(User user,HttpSession session,String checkCode) {
		AJAXResult result = new AJAXResult();
	
		//1.���ж��û���֤���Ƿ�������ȡ
		String ramdomCode = (String)session.getAttribute("ramdomCode");
		System.out.println(checkCode);
		if(!ramdomCode.equals(checkCode)) {
			result.setData("��֤�벻��ȷ");
			result.setSuccess(false);
		}else {
			//��Ϊ�û���Ϣ���е������Ǽ��ܹ���  
			//����Ҫ���û������������м��ܺ�  �ٽ����ж�
			user.setUserpswd(MD5Utils.md5(user.getUserpswd()));
			
			User dbuser = userService.query4Login(user);
			
			if(dbuser != null) {
				if(dbuser.getLocked() == 1) {
					//��ʾ�û��Ѿ�������
					result.setSuccess(false);
					result.setData("�û��Ѿ�������");
				}else {
					//��¼�ɹ�  
					//���û���¼��Ϣ���浽session��
					session.setAttribute("loginUser", dbuser);
					
					//�����û�����¼ʱ�� �� �ܹ���¼����
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					dbuser.setLastLogin(sdf.format(new Date()));
					dbuser.setLoginCount(dbuser.getLoginCount()+1);
					
					userService.updateUserLoginInfo(dbuser);
					
					//��ȡ�û�Ȩ����Ϣ
					List<Permission> permissions = permissionService.queryPermissionByUser(dbuser);
					//����û�Ȩ�޾��е����¼���ϵ
					Map<Integer,Permission> permissionMap = new HashMap<Integer,Permission>();
					Permission root = null; //���浱ǰ�û����Է��ĵ�Ȩ��·���ļ���
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
				//��¼ʧ��
				result.setData("�û��������벻��ȷ");
				result.setSuccess(false);
			}	
		}
		return result;
	}
	
	/**
	 * ʵ��ִ�е�¼����
	 * @return
	 */
	@RequestMapping("/doLogin")
	public String doLogin(User user,Model model) throws Exception{
		String loginacct = user.getLoginacct();
		
		/**����һ��һ���������ķ�ʽ������ȡ  ͳһʹ��spring�ṩ�Ĺ��������н��  **/
		/*//�������ַ������մ���ı��뷽ʽת��Ϊԭʼ���ֽ�������
		byte[] bs = loginacct.getBytes("ISO8859-1");
		//���õ���ԭʼ���ֽ������а�����ȷ�ı��뷽ʽת��Ϊ��ȷ������
		loginacct = new String(bs,"UTF-8");*/
		
		//1)��ȡ���ύ�ĵ�¼��Ϣ
		//1-1) ʹ��HttpServletRequest������л�ȡ
		//1-2) ʹ�÷��������б��ȡ���ύ����Ϣ  Ҫ�󷽷�����������Ҫ�ͱ������name����ֵ��ͬ
		//1-3) �������ݷ�װΪʵ��������ڷ���������ʹ��һ��ʵ�������������  Ҫ��ʵ������е���������Ҫ��input�����name����ֵ���
		
		
		//2) ���ݱ��ύ����Ϣ��ѯ�û���Ϣ
		User dbuser = userService.query4Login(user);
		
		//3) �ж��û���Ϣ�Ƿ����
		if(dbuser != null) {
			//��¼�ɹ�  ��ת����ҳ��  Ĭ��ʹ��ת���ķ�ʽ
			return "main";
		}else {
			//��¼ʧ�� ��ת�ص���¼ҳ��  ������ʾ������Ϣ
			String errorMsg = "��¼�˺Ż����벻��ȷ������������";
			model.addAttribute("errorMsg",errorMsg);
			return "redirect:login";
		}
	}
	
	@RequestMapping("/logout")
	public String logout(HttpSession session) {
		
		//�����û���Ĭ������
		session.invalidate();
		//�ض��򵽵�ǰ���е�login������
		return "redirect:login";
		//return "login";  ����ת����login.jsp
	}
}
