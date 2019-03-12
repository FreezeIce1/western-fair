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
	
	//跳转到用户维护的主页面
	@RequestMapping("/index")
	public String index() {
		return "user/index";
	}
	
	//跳转到用户信息新增页面
	@RequestMapping("/add")
	public String add() {
		return "user/add";
	}
	
	//跳转到用户信息的修改页面
	@RequestMapping("/edit")
	public String edit(Integer id,Model model) {
		//查询要修改的用户信息
		User user = userService.queryById(id);
		model.addAttribute("user", user);
		return "user/edit";
	}
	
	//跳转到用户角色分配页面
	@RequestMapping("/assign")
	public String assign(Integer id,Model model) {
		User user = userService.queryById(id);
		model.addAttribute("user", user);
		
		//查询所有角色
		List<Role> roles = roleService.queryAll();
		
		List<Role> assignRoles = new ArrayList<Role>();
		List<Role> unassignRoles = new ArrayList<Role>();
		
		//获取关系表中与该用户相关的记录中角色id  就是代表已经为该用户分配的角色
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
	
	//新增用户信息的方法
	@ResponseBody
	@RequestMapping("/insert")
	public Object insert(User user) {
		AJAXResult result = new AJAXResult();
		try {
			//设置用户创建的时间
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			user.setCreatetime(sdf.format(new Date()));
			//设置用户的默认密码
			user.setUserpswd(MD5Utils.md5("12345678"));
			userService.insertUser(user);
			result.setSuccess(true);
		}catch(Exception e) {
			e.printStackTrace();
			result.setSuccess(false);
		}
		
		return result;
	}
	
	//修改用户信息的方法
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
	
	//删除用户信息的方法
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
	
	//批量删除用户信息的方法
	//userid的名字要和复选框name属性值相同
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
			//分页查询
			Map<String,Object > map = new HashMap<String,Object>();
			map.put("start", (pageno-1)*pagesize);
			map.put("size", pagesize);
			map.put("queryText", queryText);
			
			List<User>users = userService.pageQueryData(map);
			//向页面返回当前页码 和 总的页数（由总的记录数和页面大小确定）	
			//获取总的数据记录数
			int totalsize = userService.pageQueryCount(map);
			//总页码
			int totalno = 0;
			if(totalsize % pagesize == 0) {
				totalno = totalsize / pagesize;
			}else {
				totalno = totalsize / pagesize + 1;
			}
			
			//创建分页对象
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
	
	//用户角色分配的方法
	@ResponseBody
	@RequestMapping("/doAssign")
	public Object doAssign(Integer userid,Integer[] unassignroleids) {
		AJAXResult result = new AJAXResult();
		
		try {
			//实际就是在用户角色关系表中添加相应数据
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
	
	//用户角色取消分配的方法
	@ResponseBody
	@RequestMapping("/doUnAssign")
	public Object doUnAssign(Integer userid,Integer[] assignroleids) {
		AJAXResult result = new AJAXResult();
		
		try {
			//实际就是在用户角色关系表中删除相应数据
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
	 * 用户首页
	 * @return
	 */
	@RequestMapping("/index1")
	public String index(@RequestParam(required=false,defaultValue="1")Integer pageno,@RequestParam(required=false,defaultValue="2")Integer pagesize,Model model) {
		//默认查询第一页
		
		//pageno  当前页
		//pagesize  当前页的大小
		//totalsize 总的数据记录数
		//totalno 总的页码
		
		//分页查询
		//limit start,size
		Map<String,Object > map = new HashMap<String,Object>();
		map.put("start", (pageno-1)*pagesize);
		map.put("size", pagesize);
		
		List<User>users = userService.pageQueryData(map);
		model.addAttribute("users", users);
		//向页面返回当前页码 和 总的页数（由总的记录数和页面大小确定）
		model.addAttribute("pageno", pageno);
		
		//获取总的数据记录数
		int totalsize = userService.pageQueryCount(map);
		//总页码
		int totalno = 0;
		if(totalsize % pagesize == 0) {
			totalno = totalsize / pagesize;
		}else {
			totalno = totalsize / pagesize + 1;
		}
		model.addAttribute("totalno", totalno);
		return "user/index";
	}
	
	//将用户信息导出到Excel表
	@RequestMapping("/exportExcel")
	public ModelAndView exportExcel(String strIds) {
        Map<String, Object> dataMap = new HashMap<String, Object>();
        List<String> titles = new ArrayList<String>();

        titles.add("账号");
        titles.add("名称");
        titles.add("邮箱地址");
        titles.add("联系电话");
        titles.add("登录次数");
        titles.add("最近登录");

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
     * 跳转到发送邮件页面
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
    
    //验证用户名是否已经存在
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
