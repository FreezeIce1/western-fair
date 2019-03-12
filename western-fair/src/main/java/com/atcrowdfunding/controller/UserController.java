package com.atcrowdfunding.controller;

import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.activiti.engine.impl.util.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.atcrowdfunding.bean.AJAXResult;
import com.atcrowdfunding.bean.Page;
import com.atcrowdfunding.bean.Role;
import com.atcrowdfunding.bean.User;
import com.atcrowdfunding.service.RoleService;
import com.atcrowdfunding.service.UserService;
import com.atcrowdfunding.util.ExcelViewWrite;
import com.atcrowdfunding.util.JavaEmailSender;
import com.atcrowdfunding.util.MD5Utils;

@Controller
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private RoleService roleService;
	
	//��ת���û�ά������ҳ��
	@RequestMapping("/index")
	public String index() {
		return "user/index";
	}
	
	//��ת���û���Ϣ����ҳ��
	@RequestMapping("/add")
	public String add() {
		return "user/add";
	}
	
	//��ת���û���Ϣ���޸�ҳ��
	@RequestMapping("/edit")
	public String edit(Integer id,Model model) {
		//��ѯҪ�޸ĵ��û���Ϣ
		User user = userService.queryById(id);
		model.addAttribute("user", user);
		return "user/edit";
	}
	
	//��ת���û���ɫ����ҳ��
	@RequestMapping("/assign")
	public String assign(Integer id,Model model) {
		User user = userService.queryById(id);
		model.addAttribute("user", user);
		
		//��ѯ���н�ɫ
		List<Role> roles = roleService.queryAll();
		
		List<Role> assignRoles = new ArrayList<Role>();
		List<Role> unassignRoles = new ArrayList<Role>();
		
		//��ȡ��ϵ��������û���صļ�¼�н�ɫid  ���Ǵ����Ѿ�Ϊ���û�����Ľ�ɫ
		List<Integer> assignRoleIds = userService.queryAssignRoleIdsByUserId(id);
		for(Role role:roles) {
			if(assignRoleIds.contains(role.getId())) {
				assignRoles.add(role);
			}else {
				unassignRoles.add(role);
			}
		}
		
		model.addAttribute("assignRoles", assignRoles);
		model.addAttribute("unassignRoles", unassignRoles);
		return "user/assign";
	}
	
	//�����û���Ϣ�ķ���
	@ResponseBody
	@RequestMapping("/insert")
	public Object insert(User user) {
		AJAXResult result = new AJAXResult();
		try {
			//�����û�������ʱ��
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			user.setCreatetime(sdf.format(new Date()));
			//�����û���Ĭ������
			user.setUserpswd(MD5Utils.md5("12345678"));
			userService.insertUser(user);
			result.setSuccess(true);
		}catch(Exception e) {
			e.printStackTrace();
			result.setSuccess(false);
		}
		
		return result;
	}
	
	//�޸��û���Ϣ�ķ���
	@ResponseBody
	@RequestMapping("/update")
	public Object update(User user) {
		AJAXResult result = new AJAXResult();
	
		try {
			userService.updateUser(user);
			result.setSuccess(true);
		}catch(Exception e) {
			e.printStackTrace();
			result.setSuccess(false);
		}
		
		return result;
	}
	
	//ɾ���û���Ϣ�ķ���
	@ResponseBody
	@RequestMapping("/delete")
	public Object delete(Integer id) {
		AJAXResult result = new AJAXResult();
		
		try {
			userService.deleteUserById(id);
			result.setSuccess(true);
		}catch(Exception e) {
			e.printStackTrace();
			result.setSuccess(false);
		}
		
		return result;
	}
	
	//����ɾ���û���Ϣ�ķ���
	//userid������Ҫ�͸�ѡ��name����ֵ��ͬ
	@ResponseBody
	@RequestMapping("/deleteUsers")
	public Object deleteUsers(Integer[] userid) { 
		AJAXResult result = new AJAXResult();
		try {
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("userids", userid);
			userService.deleteUsers(map);
			result.setSuccess(true);
		}catch(Exception e) {
			e.printStackTrace();
			result.setSuccess(false);
		}
		
		return result;
	}
	
	@ResponseBody
	@RequestMapping("/pageQuery")
	public Object pageQuery(String queryText,Integer pageno,Integer pagesize) {
		AJAXResult result = new AJAXResult();
		
		try {
			//��ҳ��ѯ
			Map<String,Object > map = new HashMap<String,Object>();
			map.put("start", (pageno-1)*pagesize);
			map.put("size", pagesize);
			map.put("queryText", queryText);
			
			List<User>users = userService.pageQueryData(map);
			//��ҳ�淵�ص�ǰҳ�� �� �ܵ�ҳ�������ܵļ�¼����ҳ���Сȷ����	
			//��ȡ�ܵ����ݼ�¼��
			int totalsize = userService.pageQueryCount(map);
			//��ҳ��
			int totalno = 0;
			if(totalsize % pagesize == 0) {
				totalno = totalsize / pagesize;
			}else {
				totalno = totalsize / pagesize + 1;
			}
			
			//������ҳ����
			Page<User> userPage = new Page<User>();
			userPage.setDatas(users);
			userPage.setTotalno(totalno);
			userPage.setTotalsize(totalsize);
			userPage.setPageno(pageno);
			
			result.setData(userPage);
			result.setSuccess(true);
		}catch(Exception e) {
			e.printStackTrace();
			result.setSuccess(false);
		}
		
		return result;
	}
	
	//�û���ɫ����ķ���
	@ResponseBody
	@RequestMapping("/doAssign")
	public Object doAssign(Integer userid,Integer[] unassignroleids) {
		AJAXResult result = new AJAXResult();
		
		try {
			//ʵ�ʾ������û���ɫ��ϵ���������Ӧ����
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("userid", userid);
			map.put("roleids", unassignroleids);
			
			userService.insertUserRoles(map);
			result.setSuccess(true);
		}catch(Exception e) {
			e.printStackTrace();
			result.setSuccess(false);
		}
		
		return result;
	}
	
	//�û���ɫȡ������ķ���
	@ResponseBody
	@RequestMapping("/doUnAssign")
	public Object doUnAssign(Integer userid,Integer[] assignroleids) {
		AJAXResult result = new AJAXResult();
		
		try {
			//ʵ�ʾ������û���ɫ��ϵ����ɾ����Ӧ����
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("userid", userid);
			map.put("roleids", assignroleids);
			
			userService.deleteUserRoles(map);
			result.setSuccess(true);
		}catch(Exception e) {
			e.printStackTrace();
			result.setSuccess(false);
		}
		
		return result;
	}
	
	/**
	 * �û���ҳ
	 * @return
	 */
	@RequestMapping("/index1")
	public String index(@RequestParam(required=false,defaultValue="1")Integer pageno,@RequestParam(required=false,defaultValue="2")Integer pagesize,Model model) {
		//Ĭ�ϲ�ѯ��һҳ
		
		//pageno  ��ǰҳ
		//pagesize  ��ǰҳ�Ĵ�С
		//totalsize �ܵ����ݼ�¼��
		//totalno �ܵ�ҳ��
		
		//��ҳ��ѯ
		//limit start,size
		Map<String,Object > map = new HashMap<String,Object>();
		map.put("start", (pageno-1)*pagesize);
		map.put("size", pagesize);
		
		List<User>users = userService.pageQueryData(map);
		model.addAttribute("users", users);
		//��ҳ�淵�ص�ǰҳ�� �� �ܵ�ҳ�������ܵļ�¼����ҳ���Сȷ����
		model.addAttribute("pageno", pageno);
		
		//��ȡ�ܵ����ݼ�¼��
		int totalsize = userService.pageQueryCount(map);
		//��ҳ��
		int totalno = 0;
		if(totalsize % pagesize == 0) {
			totalno = totalsize / pagesize;
		}else {
			totalno = totalsize / pagesize + 1;
		}
		model.addAttribute("totalno", totalno);
		return "user/index";
	}
	
	//���û���Ϣ������Excel��
	@RequestMapping("/exportExcel")
	public ModelAndView exportExcel(String strIds) {
        Map<String, Object> dataMap = new HashMap<String, Object>();
        List<String> titles = new ArrayList<String>();

        titles.add("�˺�");
        titles.add("����");
        titles.add("�����ַ");
        titles.add("��ϵ�绰");
        titles.add("��¼����");
        titles.add("�����¼");

        dataMap.put("titles", titles);

        List<HashMap<String, Object>> values = new ArrayList<HashMap<String, Object>>();
        String[] ids = strIds.split(",");
        for (int i = 0; i < ids.length; i++) {
            User user = userService.queryById(Integer.parseInt(ids[i]));
            HashMap<String, Object> map = new HashMap<String, Object>();
            map.put("var1", user.getLoginacct());
            map.put("var2", user.getUsername());
            map.put("var3", user.getEmail());
            map.put("var4", user.getPhone());
            map.put("var5", user.getLoginacct());
            map.put("var6", user.getLastLogin());
            values.add(map);
        }

        dataMap.put("values", values);

        ExcelViewWrite ev = new ExcelViewWrite();

        ModelAndView mv = new ModelAndView(ev, dataMap);

        return mv;
    }
	
	/**
     * ��ת�������ʼ�ҳ��
     *
     * @return
     */
    @RequestMapping("/goSendEmail")
    public String goSendEmailPage(String toEmails,Model model) {
       model.addAttribute("toEmails", toEmails);
       return "common/send_email";
    }
    
    @RequestMapping(value = "/sendEmail", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public void sendEmail(@RequestParam("toEmail") String toEmail, @RequestParam("title") String title,
                          @RequestParam("content") String content, HttpServletResponse response) throws Exception {

        JavaEmailSender.sendEmail(toEmail, title, content);

        JSONObject obj = new JSONObject();
        obj.put("msg", "ok");
        PrintWriter out;

        response.setCharacterEncoding("utf-8");
        out = response.getWriter();
        out.write(obj.toString());

        out.flush();
        out.close();

    }
    
    //��֤�û����Ƿ��Ѿ�����
    @RequestMapping("/checkLoginAcct")
    @ResponseBody
    public Object checkLoginAcct(String loginacct) {
    	AJAXResult result = new AJAXResult();    	
    	User user = userService.queryUserByLoginacct(loginacct);
    	if(user != null) {
    		result.setData("exist");
    	}
    	
    	return result;
    }
}
