package com.atcrowdfunding.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.atcrowdfunding.bean.AJAXResult;
import com.atcrowdfunding.bean.Activity;
import com.atcrowdfunding.bean.Page;
import com.atcrowdfunding.bean.User;
import com.atcrowdfunding.service.ActivityService;

@Controller
@RequestMapping("/activity")
public class ActivityController {
	
	@Autowired
	ActivityService activityService;
	
	@RequestMapping("/index")
	public String index() {
		return "activity/index";
	}
	
	@RequestMapping("/add")
	public String add() {
		return "activity/add";
	}
	
	@RequestMapping("/edit")
	public String edit(Integer id,Model model) {
		Activity activity = activityService.queryById(id);
		model.addAttribute("activity", activity);
		return "activity/edit";
	}
	
	@RequestMapping("/insert")
	@ResponseBody
	public Object insert(Activity activity) {
		AJAXResult result = new AJAXResult();
		try {
			activityService.insertActivity(activity);
			result.setSuccess(true);
		}catch(Exception e) {
			e.printStackTrace();
			result.setSuccess(false);
		}
		return result;
	}
	
	@RequestMapping("/update")
	@ResponseBody
	public Object update(Activity activity) {
		AJAXResult result = new AJAXResult();
		try {
			activityService.updateActivity(activity);
			result.setSuccess(true);
		}catch(Exception e) {
			e.printStackTrace();
			result.setSuccess(false);
		}
		return result;
	}
	
	@RequestMapping("/delete")
	@ResponseBody
	public Object delete(Integer id) {
		AJAXResult result = new AJAXResult();
		try {
			activityService.deleteActivityById(id);
			result.setSuccess(true);
		}catch(Exception e) {
			e.printStackTrace();
			result.setSuccess(false);
		}
		return result;
	}
	
	@RequestMapping("/deleteActivities")
	@ResponseBody
	public Object deleteActivities(Integer[] activityid) { 
		AJAXResult result = new AJAXResult();
		try {
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("acticityids", activityid);
			activityService.deleteActivities(map);
			result.setSuccess(true);
		}catch(Exception e) {
			e.printStackTrace();
			result.setSuccess(false);
		}
		
		return result;
	}
	
	@RequestMapping("/pageQuery")
	@ResponseBody
	public Object pageQuery(String queryText,Integer pageno,Integer pagesize) {
		AJAXResult result = new AJAXResult();
		try {
			Map<String,Object > map = new HashMap<String,Object>();
			map.put("start", (pageno-1)*pagesize);
			map.put("size", pagesize);
			map.put("queryText", queryText);
			
			List<Activity> activities = activityService.pageQueryData(map);
			//System.out.println(activities.size());
			int totalsize = activityService.pageQueryCount(map);
			int totalno = 0;
			if(totalsize % pagesize == 0) {
				totalno = totalsize / pagesize;
			}else {
				totalno = totalsize / pagesize + 1;
			}
			Page<Activity> activityPage = new Page<Activity>();
			activityPage.setDatas(activities);
			activityPage.setTotalno(totalno);
			activityPage.setTotalsize(totalsize);
			activityPage.setPageno(pageno);
			
			result.setData(activityPage);
			result.setSuccess(true);
		}catch(Exception e) {
			e.printStackTrace();
			result.setSuccess(false);
		}
		return result;
	}
}
