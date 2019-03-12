package com.atcrowdfunding.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.atcrowdfunding.bean.AJAXResult;
import com.atcrowdfunding.bean.Page;
import com.atcrowdfunding.bean.Partner;
import com.atcrowdfunding.bean.PartnerType;
import com.atcrowdfunding.service.PartnerTypeService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
@RequestMapping("/partnerType")
public class PartnerTypeController {
	@Autowired
	private PartnerTypeService partnerTypeService;
	//跳转到合作伙伴的主页面
		@RequestMapping("/index")
		public String index() {
			
			return "partnerType/index";
		}
		
		//跳转到用户信息新增页面
		@RequestMapping("/add")
		public String add() {
			return "partnerType/add";
		}
		
		//跳转到用户信息的修改页面
		@RequestMapping("/edit")
		public String edit(Integer typeid,Model model) {
			//查询要修改的用户信息
			PartnerType partnerType = partnerTypeService.queryById(typeid);
			
			model.addAttribute("partnerType", partnerType);
			return "partnerType/edit";
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
				
				List<PartnerType>partnerTypes = partnerTypeService.pageQueryData(map);
				//向页面返回当前页码 和 总的页数（由总的记录数和页面大小确定）	
				//获取总的数据记录数
				int totalsize = partnerTypeService.pageQueryCount(map);
				//总页码
				int totalno = 0;
				if(totalsize % pagesize == 0) {
					totalno = totalsize / pagesize;
				}else {
					totalno = totalsize / pagesize + 1;
				}
				
				//创建分页对象
				Page<PartnerType> partnerTypePage = new Page<PartnerType>();
				partnerTypePage.setDatas(partnerTypes);
				partnerTypePage.setTotalno(totalno);
				partnerTypePage.setTotalsize(totalsize);
				partnerTypePage.setPageno(pageno);
				
				result.setData(partnerTypePage);
				result.setSuccess(true);
			}catch(Exception e) {
				e.printStackTrace();
				result.setSuccess(false);
			}
			
			return result;
		}
		
		
		//新增合作伙伴信息的方法
		@ResponseBody
		@RequestMapping("/insert")
		public Object insert(PartnerType partnerType,HttpServletRequest request) {
			AJAXResult result = new AJAXResult();
			
			try {
				//设置对象创建的时间
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				partnerType.setTypeaddtime(sdf.format(new Date()));
				partnerTypeService.insertPartnerType(partnerType);
				result.setSuccess(true);
			}catch(Exception e) {
				e.printStackTrace();
				result.setSuccess(false);
			}
			
			return result;
		}
		//修改合作伙伴信息的方法
		@ResponseBody
		@RequestMapping("/update")
		public Object update(PartnerType partnerType,HttpServletRequest request) throws IllegalStateException, IOException {
			AJAXResult result = new AJAXResult();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			partnerType.setTypeupdatetime(sdf.format(new Date()));
			try {
				partnerTypeService.updatePartnerType(partnerType);
				result.setSuccess(true);
			}catch(Exception e) {
				e.printStackTrace();
				result.setSuccess(false);
			}
			
			return result;
		}
		//删除合作伙伴信息的方法
		@ResponseBody
		@RequestMapping("/delete")
		public Object delete(Integer typeid) throws JsonProcessingException {
			//AJAXResult result = new AJAXResult();
			String success="";
			List<Partner> partner=partnerTypeService.findPartnerBytype(typeid);
			System.out.println(partner.size()+"!!!");
			if(partner.size()==0) { //没有关联数据可以删除
				try {
					partnerTypeService.deletePartnerTypeById(typeid);
				success="true";
				}catch(Exception e) {
					e.printStackTrace();
					success="false";
				}
			}else {
				success="no"; //不可以删除
			}
			System.out.println(success);
			 Map<String, Object> map = new HashMap<String, Object>();
				map.put("success", success);
				ObjectMapper mapper = new ObjectMapper();
				String jsonString = mapper.writeValueAsString(map);
				return jsonString;
			
		}
		
		//批量删除合作伙伴信息的方法
		//userid的名字要和复选框name属性值相同
		@ResponseBody
		@RequestMapping("/deletePartnerTypes")
		public Object deleteUsers(Integer[] partnerTypeid) throws JsonProcessingException { 
			//AJAXResult result = new AJAXResult();
			String success="";
			boolean flag=true;
			
				Map<String,Object> map = new HashMap<String,Object>();
				map.put("partnerTypeids", partnerTypeid);
				for(Integer typeid:partnerTypeid) {
					List<Partner> partner=partnerTypeService.findPartnerBytype(typeid);
					System.out.println(partner.size()+"!!!");
					if(partner.size()!=0) { //有关联数据
						flag=false;
						break;
					}
				}
				if(flag==true) {  //没有关联数据
					try {
						partnerTypeService.deletePartnerTypes(map);
					success="true";
					}catch(Exception e) {
						e.printStackTrace();
						success="false";
					}
				}else {
					success="no"; //不可以删除
				}
		
				System.out.println(success);
				 Map<String, Object> map1 = new HashMap<String, Object>();
					map1.put("success", success);
					ObjectMapper mapper = new ObjectMapper();
					String jsonString = mapper.writeValueAsString(map1);
					return jsonString;
				
		}
		
}
