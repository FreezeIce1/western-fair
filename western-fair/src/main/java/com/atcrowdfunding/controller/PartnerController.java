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
	//��ת������������ҳ��
		@RequestMapping("/index")
		public String index() {
			
			return "partner/index";
		}
		
		//��ת���û���Ϣ����ҳ��
		@RequestMapping("/add")
		public String add(Model model) {
			List<PartnerType> list = partnerService.findPartnerType();
			model.addAttribute("partnerType",list);
			return "partner/add";
		}
		
		//��ת���û���Ϣ���޸�ҳ��
		@RequestMapping("/edit")
		public String edit(Integer id,Model model) {
			//��ѯҪ�޸ĵ��û���Ϣ
			Partner partner = partnerService.queryById(id);
			List<PartnerType> list = partnerService.findPartnerType(); //�����������л
			model.addAttribute("partnerType",list);
			model.addAttribute("partner", partner);
			return "partner/edit";
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
				
				List<Partner>partners = partnerService.pageQueryData(map);
				
				//��ҳ�淵�ص�ǰҳ�� �� �ܵ�ҳ�������ܵļ�¼����ҳ���Сȷ����	
				//��ȡ�ܵ����ݼ�¼��
				int totalsize = partnerService.pageQueryCount(map);
				//��ҳ��
				int totalno = 0;
				if(totalsize % pagesize == 0) {
					totalno = totalsize / pagesize;
				}else {
					totalno = totalsize / pagesize + 1;
				}
				
				//������ҳ����
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
		
		
		//�������������Ϣ�ķ���
		@ResponseBody
		@RequestMapping("/insert")
		public Object insert(Partner partner,HttpServletRequest request) {
			AJAXResult result = new AJAXResult();
			
			try {
				//���ö��󴴽���ʱ��
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				partner.setAddtime(sdf.format(new Date()));
				
				 //�������ݿ��·��  
			      String sqlPath = null;   
			      //�����ļ�����ı���·��   -->��Ŀ
			      String localPath="F:\\western-fair_img\\company";
			      File filePath= new File(localPath);
					//��������ļ��ĵ�ַ�����ھ��ȴ���Ŀ¼
					if(!filePath.exists()) {
						System.out.println("��");
						filePath.mkdirs();
					}
			      //���� �ļ���  
			      String filename=null;
			      if(!partner.getFile().isEmpty()){    
			          //����uuid��Ϊ�ļ�����    
			          String uuid = UUID.randomUUID().toString().replaceAll("-","");    
			          //����ļ����ͣ������ж��������ͼƬ����ֹ�ϴ���    
			          String contentType=partner.getFile().getContentType();    
			          //����ļ���׺��   
			          String suffixName=contentType.substring(contentType.indexOf("/")+1);  
			          //�õ� �ļ���  
			          filename=uuid+"."+suffixName;   
			        
			          //�ļ�����·��  
			          partner.getFile().transferTo(new File(localPath+"\\"+filename));    
			        
			      }  
			      //��ͼƬ�����·�����������ݿ�  
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
		//�޸ĺ��������Ϣ�ķ���
		@ResponseBody
		@RequestMapping("/update")
		public Object update(Partner partner,HttpServletRequest request)  {
			AJAXResult result = new AJAXResult();
			//���ö��󴴽���ʱ��
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			partner.setUpdatetime(sdf.format(new Date()));
			try {
			 //�������ݿ��·��  
		      String sqlPath = null;   
		      //�����ļ�����ı���·��  ����ӳ��
		      String localPath="F:\\western-fair_img\\company";
		      File filePath= new File(localPath);
				//��������ļ��ĵ�ַ�����ھ��ȴ���Ŀ¼
				if(!filePath.exists()) {
					System.out.println("��");
					filePath.mkdirs();
				}
		      //���� �ļ���  
		      String filename=null;
		      if(!partner.getFile().isEmpty()){    
		          //����uuid��Ϊ�ļ�����    
		          String uuid = UUID.randomUUID().toString().replaceAll("-","");    
		          //����ļ����ͣ������ж��������ͼƬ����ֹ�ϴ���    
		          String contentType=partner.getFile().getContentType();    
		          //����ļ���׺��   
		          String suffixName=contentType.substring(contentType.indexOf("/")+1);  
		          //�õ� �ļ���  
		          filename=uuid+"."+suffixName;   
		          //�ļ�����·��  
		          partner.getFile().transferTo(new File(localPath+"\\"+filename));    
		        
		      }  
		      //��ͼƬ�����·�����������ݿ�  
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
		//ɾ�����������Ϣ�ķ���
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
		
		//����ɾ�����������Ϣ�ķ���
		//userid������Ҫ�͸�ѡ��name����ֵ��ͬ
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
			//�ж��ϴ��ļ��Ƿ����
				if(!file.isEmpty()) {
					//1.����ϴ����ļ�/ͼƬ��������
					String fileStr = file.getOriginalFilename();
					//2.ʹ��������ɵ��ַ���+ԴͼƬ��չ������µ�ͼƬ���ƣ���ֹͼƬ����
					String newfileName =UUID.randomUUID().toString()+fileStr.substring(fileStr.lastIndexOf("."));
					//3.��ͼƬ���浽Ӳ��
				/*	String dirPath=request.getServletContext().getRealPath("/upload/");
					File filePath= new File(dirPath);
					//��������ļ��ĵ�ַ�����ھ��ȴ���Ŀ¼
					if(!filePath.exists()) {
						filePath.mkdirs();
					}
					*/
					file.transferTo(new File("F:\\eclipse_image\\"+newfileName));
			}
				
				return "success";
			
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
			
			List<Partner>partners = partnerService.pageQueryData(map);
			model.addAttribute("partners", partners);
			//��ҳ�淵�ص�ǰҳ�� �� �ܵ�ҳ�������ܵļ�¼����ҳ���Сȷ����
			model.addAttribute("pageno", pageno);
			
			//��ȡ�ܵ����ݼ�¼��
			int totalsize = partnerService.pageQueryCount(map);
			//��ҳ��
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
