package com.atcrowdfunding.controller;


import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.atcrowdfunding.bean.AJAXResult;
import com.atcrowdfunding.bean.NewsType;
import com.atcrowdfunding.bean.Page;
import com.atcrowdfunding.service.NewsTypeService;


@Controller
@RequestMapping("/newsType")
public class NewsTypeController {
	@Autowired
	private NewsTypeService newsTypeService;
	
	
	@RequestMapping("/index")
	public String index() {
		return "newsType/index";
	}
	
	@RequestMapping("/add")
	public String add(Model model) {
		return "newsType/add";
	}
	
	@RequestMapping("/edit")
	public String edit(Model model,int id) {
		model.addAttribute("id",id);
		return "newsType/edit";
	}
	
	@ResponseBody
	@RequestMapping("/pageQuery")
	public Object pageQuery( String queryText, Integer pageno, Integer pagesize ) {
		
		AJAXResult result = new AJAXResult();
		
		try {
			
			// 分页查询
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("start", (pageno-1)*pagesize);
			map.put("size", pagesize);
			map.put("queryText", queryText);
			
			List<NewsType> newsTypes = newsTypeService.pageQueryData( map );
			// 当前页码			
			// 总的数据条数
			int totalsize = newsTypeService.pageQueryCount( map );
			// 最大页码（总页码）
			int totalno = 0;
			if ( totalsize % pagesize == 0 ) {
				totalno = totalsize / pagesize;
			} else {
				totalno = totalsize / pagesize + 1;
			}
			
			// 分页对象
			Page<NewsType> newsTypePage = new Page<NewsType>();
			newsTypePage.setDatas(newsTypes);
			newsTypePage.setTotalno(totalno);
			newsTypePage.setTotalsize(totalsize);
			newsTypePage.setPageno(pageno);
			
			result.setData(newsTypePage);
			result.setSuccess(true);
		} catch ( Exception e ) {
			e.printStackTrace();
			result.setSuccess(false);
		}
		
		return result;
		
	}
	
	
	//添加类别
	@ResponseBody
	@RequestMapping("/insert")
	public Object insert(NewsType newsType) {
		AJAXResult result = new AJAXResult();
		try {
			// 设置用户创建的时间
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			newsType.setAddtime(sdf.format(new Date()));
			
			newsTypeService.insertNewsType(newsType);
			result.setSuccess(true);
		} catch (Exception e) {
			e.printStackTrace();
			result.setSuccess(false);
		}

		return result;
	}
	

	@ResponseBody
	@RequestMapping("/findNewsTypeById")
	public NewsType findNewsTypeById(int id) {
		return newsTypeService.findNewsTypeById(id);
	}
	
	//更新类别
	@ResponseBody
	@RequestMapping("/update")
	public Object update(NewsType newsType) {
		AJAXResult result = new AJAXResult();
		try {
			// 设置用户创建的时间
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			// 更新修改时间
			newsType.setAddtime(sdf.format(new Date()));

			newsTypeService.updateNewsType(newsType);
			result.setSuccess(true);
		} catch (Exception e) {
			e.printStackTrace();
			result.setSuccess(false);
		}

		return result;
	}
	
	// 删除用户信息的方法
	@ResponseBody
	@RequestMapping("/delete")
	public Object delete(int id) {
		AJAXResult result = new AJAXResult();
		try {
			//验证是否有被使用的类型
			int count = newsTypeService.findNewsTypeInNews(id);
			if(count>0) {
				result.setSuccess(false);
				return result;
			}
			newsTypeService.deleteNewsType(id);
			result.setSuccess(true);
		} catch (Exception e) {
			e.printStackTrace();
			result.setSuccess(false);
		}

		return result;
	}
	
	
	// 批量删除用户信息的方法
	// newsid的名字要和复选框name属性值相同
	@ResponseBody
	@RequestMapping("/deleteNewsTypeList")
	public Object deleteNewsList(Integer[] id) {
		AJAXResult result = new AJAXResult();
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("id", id);
			//验证是否有被使用的类型
			int count = newsTypeService.findNewsTypeByIds(map);
			if(count>0) {
				result.setSuccess(false);
				return result;
			}
			newsTypeService.deleteNewsTypeList(map);
			result.setSuccess(true);
		} catch (Exception e) {
			e.printStackTrace();
			result.setSuccess(false);
		}

		return result;
	}
	
	

}
