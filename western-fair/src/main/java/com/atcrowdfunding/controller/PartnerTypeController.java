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
	//��ת������������ҳ��
		@RequestMapping("/index")
		public String index() {
			
			return "partnerType/index";
		}
		
		//��ת���û���Ϣ����ҳ��
		@RequestMapping("/add")
		public String add() {
			return "partnerType/add";
		}
		
		//��ת���û���Ϣ���޸�ҳ��
		@RequestMapping("/edit")
		public String edit(Integer typeid,Model model) {
			//��ѯҪ�޸ĵ��û���Ϣ
			PartnerType partnerType = partnerTypeService.queryById(typeid);
			
			model.addAttribute("partnerType", partnerType);
			return "partnerType/edit";
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
				
				List<PartnerType>partnerTypes = partnerTypeService.pageQueryData(map);
				//��ҳ�淵�ص�ǰҳ�� �� �ܵ�ҳ�������ܵļ�¼����ҳ���Сȷ����	
				//��ȡ�ܵ����ݼ�¼��
				int totalsize = partnerTypeService.pageQueryCount(map);
				//��ҳ��
				int totalno = 0;
				if(totalsize % pagesize == 0) {
					totalno = totalsize / pagesize;
				}else {
					totalno = totalsize / pagesize + 1;
				}
				
				//������ҳ����
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
		
		
		//�������������Ϣ�ķ���
		@ResponseBody
		@RequestMapping("/insert")
		public Object insert(PartnerType partnerType,HttpServletRequest request) {
			AJAXResult result = new AJAXResult();
			
			try {
				//���ö��󴴽���ʱ��
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
		//�޸ĺ��������Ϣ�ķ���
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
		//ɾ�����������Ϣ�ķ���
		@ResponseBody
		@RequestMapping("/delete")
		public Object delete(Integer typeid) throws JsonProcessingException {
			//AJAXResult result = new AJAXResult();
			String success="";
			List<Partner> partner=partnerTypeService.findPartnerBytype(typeid);
			System.out.println(partner.size()+"!!!");
			if(partner.size()==0) { //û�й������ݿ���ɾ��
				try {
					partnerTypeService.deletePartnerTypeById(typeid);
				success="true";
				}catch(Exception e) {
					e.printStackTrace();
					success="false";
				}
			}else {
				success="no"; //������ɾ��
			}
			System.out.println(success);
			 Map<String, Object> map = new HashMap<String, Object>();
				map.put("success", success);
				ObjectMapper mapper = new ObjectMapper();
				String jsonString = mapper.writeValueAsString(map);
				return jsonString;
			
		}
		
		//����ɾ�����������Ϣ�ķ���
		//userid������Ҫ�͸�ѡ��name����ֵ��ͬ
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
					if(partner.size()!=0) { //�й�������
						flag=false;
						break;
					}
				}
				if(flag==true) {  //û�й�������
					try {
						partnerTypeService.deletePartnerTypes(map);
					success="true";
					}catch(Exception e) {
						e.printStackTrace();
						success="false";
					}
				}else {
					success="no"; //������ɾ��
				}
		
				System.out.println(success);
				 Map<String, Object> map1 = new HashMap<String, Object>();
					map1.put("success", success);
					ObjectMapper mapper = new ObjectMapper();
					String jsonString = mapper.writeValueAsString(map1);
					return jsonString;
				
		}
		
}
