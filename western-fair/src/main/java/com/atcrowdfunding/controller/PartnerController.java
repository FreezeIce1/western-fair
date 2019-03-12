package com.atcrowdfunding.controller;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.atcrowdfunding.bean.AJAXResult;
import com.atcrowdfunding.bean.Page;
import com.atcrowdfunding.bean.Partner;
import com.atcrowdfunding.bean.PartnerType;
import com.atcrowdfunding.service.PartnerService;

@Controller
@RequestMapping("/partner")
public class PartnerController {
	@Autowired
	private PartnerService partnerService;
	//跳转到合作伙伴的主页面
		@RequestMapping("/index")
		public String index() {
			
			return "partner/index";
		}
		
		//跳转到用户信息新增页面
		@RequestMapping("/add")
		public String add(Model model) {
			List<PartnerType> list = partnerService.findPartnerType();
			model.addAttribute("partnerType",list);
			return "partner/add";
		}
		
		//跳转到用户信息的修改页面
		@RequestMapping("/edit")
		public String edit(Integer id,Model model) {
			//查询要修改的用户信息
			Partner partner = partnerService.queryById(id);
			List<PartnerType> list = partnerService.findPartnerType(); //下拉框中所有活动
			model.addAttribute("partnerType",list);
			model.addAttribute("partner", partner);
			return "partner/edit";
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
				
				List<Partner>partners = partnerService.pageQueryData(map);
				
				//向页面返回当前页码 和 总的页数（由总的记录数和页面大小确定）	
				//获取总的数据记录数
				int totalsize = partnerService.pageQueryCount(map);
				//总页码
				int totalno = 0;
				if(totalsize % pagesize == 0) {
					totalno = totalsize / pagesize;
				}else {
					totalno = totalsize / pagesize + 1;
				}
				
				//创建分页对象
				Page<Partner> partnerPage = new Page<Partner>();
				partnerPage.setDatas(partners);
				partnerPage.setTotalno(totalno);
				partnerPage.setTotalsize(totalsize);
				partnerPage.setPageno(pageno);
				
				result.setData(partnerPage);
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
		public Object insert(Partner partner,HttpServletRequest request) {
			AJAXResult result = new AJAXResult();
			
			try {
				//设置对象创建的时间
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				partner.setAddtime(sdf.format(new Date()));
				
				 //保存数据库的路径  
			      String sqlPath = null;   
			      //定义文件保存的本地路径   -->项目
			      String localPath="F:\\western-fair_img\\company";
			      File filePath= new File(localPath);
					//如果保存文件的地址不存在就先创建目录
					if(!filePath.exists()) {
						System.out.println("无");
						filePath.mkdirs();
					}
			      //定义 文件名  
			      String filename=null;
			      if(!partner.getFile().isEmpty()){    
			          //生成uuid作为文件名称    
			          String uuid = UUID.randomUUID().toString().replaceAll("-","");    
			          //获得文件类型（可以判断如果不是图片，禁止上传）    
			          String contentType=partner.getFile().getContentType();    
			          //获得文件后缀名   
			          String suffixName=contentType.substring(contentType.indexOf("/")+1);  
			          //得到 文件名  
			          filename=uuid+"."+suffixName;   
			        
			          //文件保存路径  
			          partner.getFile().transferTo(new File(localPath+"\\"+filename));    
			        
			      }  
			      //把图片的相对路径保存至数据库  
			      sqlPath = "img/"+filename;  
			    System.out.println(sqlPath);
			      partner.setImage(sqlPath);  
				
				partnerService.insertPartner(partner);
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
		public Object update(Partner partner,HttpServletRequest request)  {
			AJAXResult result = new AJAXResult();
			//设置对象创建的时间
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			partner.setUpdatetime(sdf.format(new Date()));
			try {
			 //保存数据库的路径  
		      String sqlPath = null;   
		      //定义文件保存的本地路径  磁盘映射
		      String localPath="F:\\western-fair_img\\company";
		      File filePath= new File(localPath);
				//如果保存文件的地址不存在就先创建目录
				if(!filePath.exists()) {
					System.out.println("无");
					filePath.mkdirs();
				}
		      //定义 文件名  
		      String filename=null;
		      if(!partner.getFile().isEmpty()){    
		          //生成uuid作为文件名称    
		          String uuid = UUID.randomUUID().toString().replaceAll("-","");    
		          //获得文件类型（可以判断如果不是图片，禁止上传）    
		          String contentType=partner.getFile().getContentType();    
		          //获得文件后缀名   
		          String suffixName=contentType.substring(contentType.indexOf("/")+1);  
		          //得到 文件名  
		          filename=uuid+"."+suffixName;   
		          //文件保存路径  
		          partner.getFile().transferTo(new File(localPath+"\\"+filename));    
		        
		      }  
		      //把图片的相对路径保存至数据库  
		      sqlPath = "img/"+filename;  
		      partner.setImage(sqlPath);  
			
				partnerService.updatePartner(partner);
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
		public Object delete(Integer id) {
			AJAXResult result = new AJAXResult();
			
			try {
				partnerService.deletePartnerById(id);
				result.setSuccess(true);
			}catch(Exception e) {
				e.printStackTrace();
				result.setSuccess(false);
			}
			
			return result;
		}
		
		//批量删除合作伙伴信息的方法
		//userid的名字要和复选框name属性值相同
		@ResponseBody
		@RequestMapping("/deletePartners")
		public Object deleteUsers(Integer[] partnerid) { 
			AJAXResult result = new AJAXResult();
			try {
				Map<String,Object> map = new HashMap<String,Object>();
				map.put("partnerids", partnerid);
				partnerService.deletePartners(map);
				result.setSuccess(true);
			}catch(Exception e) {
				e.printStackTrace();
				result.setSuccess(false);
			}
			
			return result;
		}
		@RequestMapping("/fileUpload")
		public String FileUpload(@RequestParam("uploadFile") MultipartFile file,HttpServletRequest request) throws Exception {
			//判断上传文件是否存在
				if(!file.isEmpty()) {
					//1.获得上传的文件/图片完整名称
					String fileStr = file.getOriginalFilename();
					//2.使用随机生成的字符串+源图片扩展名组成新的图片名称，防止图片重名
					String newfileName =UUID.randomUUID().toString()+fileStr.substring(fileStr.lastIndexOf("."));
					//3.将图片保存到硬盘
				/*	String dirPath=request.getServletContext().getRealPath("/upload/");
					File filePath= new File(dirPath);
					//如果保存文件的地址不存在就先创建目录
					if(!filePath.exists()) {
						filePath.mkdirs();
					}
					*/
					file.transferTo(new File("F:\\eclipse_image\\"+newfileName));
			}
				
				return "success";
			
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
			
			List<Partner>partners = partnerService.pageQueryData(map);
			model.addAttribute("partners", partners);
			//向页面返回当前页码 和 总的页数（由总的记录数和页面大小确定）
			model.addAttribute("pageno", pageno);
			
			//获取总的数据记录数
			int totalsize = partnerService.pageQueryCount(map);
			//总页码
			int totalno = 0;
			if(totalsize % pagesize == 0) {
				totalno = totalsize / pagesize;
			}else {
				totalno = totalsize / pagesize + 1;
			}
			model.addAttribute("totalno", totalno);
			return "partner/index";
		}
}
