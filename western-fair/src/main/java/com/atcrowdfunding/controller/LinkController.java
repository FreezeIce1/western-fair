package com.atcrowdfunding.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.atcrowdfunding.bean.AJAXResult;
import com.atcrowdfunding.bean.Link;
import com.atcrowdfunding.bean.Page;
import com.atcrowdfunding.bean.User;
import com.atcrowdfunding.service.LinkService;
import com.atcrowdfunding.util.ExcelViewWrite;
import com.atcrowdfunding.util.MD5Utils;

@Controller
@RequestMapping("/link")
public class LinkController {
	
	@Autowired
	private LinkService linkService;
	
	
	//跳转到友情链接管理的主页面
	@RequestMapping("/index")
	public String index() {
		return "link/index";
	}
	
	//跳转到新增链接信息的页面
	@RequestMapping("/add")
	public String add() {
		return "link/add";
	}
	
	//跳转到修改链接信息的页面
	@RequestMapping("/edit")
	public String edit(Integer id,Model model) {
		//查询要修改的用户信息
		Link link = linkService.queryById(id);
		model.addAttribute("link", link);
		return "link/edit";
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
			
			List<Link> links = linkService.pageQueryData(map);
			//向页面返回当前页码 和 总的页数（由总的记录数和页面大小确定）	
			//获取总的数据记录数
			int totalsize = linkService.pageQueryCount(map);
			
			
		
			//总页码
			int totalno = 0;
			if(totalsize % pagesize == 0) {
				totalno = totalsize / pagesize;
			}else {
				totalno = totalsize / pagesize + 1;
			}
			
			//创建分页对象
			Page<Link> linkPage = new Page<Link>();
			linkPage.setDatas(links);
			linkPage.setTotalno(totalno);
			linkPage.setTotalsize(totalsize);
			linkPage.setPageno(pageno);
			
			result.setData(linkPage);
			result.setSuccess(true);
		}catch(Exception e) {
			e.printStackTrace();
			result.setSuccess(false);
		}
		
		System.out.println(result.isSuccess());
		System.out.println(result.getData());
		
		return result;
	}
	
	//添加链接信息的方法
	@ResponseBody
	@RequestMapping("/insert")
	public Object insert(Link link) {
		AJAXResult result = new AJAXResult();
		try {
			//设置用户创建的时间
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			link.setAddtime(sdf.format(new Date()));
			link.setUpdatetime(sdf.format(new Date()));
			linkService.insertLink(link);
			result.setSuccess(true);
		}catch(Exception e) {
			e.printStackTrace();
			result.setSuccess(false);
		}
		
		return result;
	}
	
	//修改链接信息的方法
	@ResponseBody
	@RequestMapping("/update")
	public Object update(Link link) {
		System.out.println("jjj");
		AJAXResult result = new AJAXResult();
	
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			link.setUpdatetime(sdf.format(new Date()));
			linkService.updateLink(link);
			result.setSuccess(true);
		}catch(Exception e) {
			e.printStackTrace();
			result.setSuccess(false);
		}
		
		return result;
	}
	
	//删除链接信息的方法
	@ResponseBody
	@RequestMapping("/delete")
	public Object delete(Integer id) {
		AJAXResult result = new AJAXResult();
		
		try {
			linkService.deleteLinkById(id);
			result.setSuccess(true);
		}catch(Exception e) {
			e.printStackTrace();
			result.setSuccess(false);
		}
		
		return result;
	}
	
	//批量删除链接信息的方法
	@ResponseBody
	@RequestMapping("/deleteLinks")
	public Object deleteUsers(Integer[] linkid) { 
		AJAXResult result = new AJAXResult();
		try {
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("linkids", linkid);
			linkService.deleteLinks(map);
			result.setSuccess(true);
		}catch(Exception e) {
			e.printStackTrace();
			result.setSuccess(false);
		}
		
		return result;
	}
	
	//将链接信息导出到Excel表
	@RequestMapping("/exportExcel")
	public ModelAndView exportExcel(String strIds) {
        Map<String, Object> dataMap = new HashMap<String, Object>();
        List<String> titles = new ArrayList<String>();

        titles.add("链接名称");
        titles.add("链接地址");

        dataMap.put("titles", titles);

        List<HashMap<String, Object>> values = new ArrayList<HashMap<String, Object>>();
        String[] ids = strIds.split(",");
        for (int i = 0; i < ids.length; i++) {
            Link link = linkService.queryById(Integer.parseInt(ids[i]));
            HashMap<String, Object> map = new HashMap<String, Object>();
            map.put("var1", link.getName());
            map.put("var2", link.getUrl());
            values.add(map);
        }

        dataMap.put("values", values);

        ExcelViewWrite ev = new ExcelViewWrite();

        ModelAndView mv = new ModelAndView(ev, dataMap);

        return mv;
    }
}
