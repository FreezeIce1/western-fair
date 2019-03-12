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
	
	
	//��ת���������ӹ������ҳ��
	@RequestMapping("/index")
	public String index() {
		return "link/index";
	}
	
	//��ת������������Ϣ��ҳ��
	@RequestMapping("/add")
	public String add() {
		return "link/add";
	}
	
	//��ת���޸�������Ϣ��ҳ��
	@RequestMapping("/edit")
	public String edit(Integer id,Model model) {
		//��ѯҪ�޸ĵ��û���Ϣ
		Link link = linkService.queryById(id);
		model.addAttribute("link", link);
		return "link/edit";
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
			
			List<Link> links = linkService.pageQueryData(map);
			//��ҳ�淵�ص�ǰҳ�� �� �ܵ�ҳ�������ܵļ�¼����ҳ���Сȷ����	
			//��ȡ�ܵ����ݼ�¼��
			int totalsize = linkService.pageQueryCount(map);
			
			
		
			//��ҳ��
			int totalno = 0;
			if(totalsize % pagesize == 0) {
				totalno = totalsize / pagesize;
			}else {
				totalno = totalsize / pagesize + 1;
			}
			
			//������ҳ����
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
	
	//���������Ϣ�ķ���
	@ResponseBody
	@RequestMapping("/insert")
	public Object insert(Link link) {
		AJAXResult result = new AJAXResult();
		try {
			//�����û�������ʱ��
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
	
	//�޸�������Ϣ�ķ���
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
	
	//ɾ��������Ϣ�ķ���
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
	
	//����ɾ��������Ϣ�ķ���
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
	
	//��������Ϣ������Excel��
	@RequestMapping("/exportExcel")
	public ModelAndView exportExcel(String strIds) {
        Map<String, Object> dataMap = new HashMap<String, Object>();
        List<String> titles = new ArrayList<String>();

        titles.add("��������");
        titles.add("���ӵ�ַ");

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
