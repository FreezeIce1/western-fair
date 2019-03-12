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
import com.atcrowdfunding.bean.News;
import com.atcrowdfunding.bean.NewsType;
import com.atcrowdfunding.bean.Page;
import com.atcrowdfunding.service.NewsService;
import com.atcrowdfunding.service.NewsTypeService;

@Controller
@RequestMapping("/news")
public class NewsController {
	
	@Autowired
	private NewsService newsService;
	@Autowired
	private NewsTypeService newsTypeService;
	
	@RequestMapping("/index")
	public String index(Model model) {
		//���Ż���
		List<NewsType> list = newsService.findNewsType();
		model.addAttribute("newsType",list);
		return "news/index";
	}
	
	@RequestMapping("/add")
	public String add(Model model) {
		List<NewsType> list = newsService.findNewsType();
		model.addAttribute("newsType",list);
		return "news/add";
	}
	
	@RequestMapping("/edit")
	public String edit(Model model,int newsid) {
		List<NewsType> list = newsService.findNewsType();
		model.addAttribute("newsType",list);
		model.addAttribute("newsid",newsid);
		return "news/edit";
	}
	
	
	@ResponseBody
	@RequestMapping("/pageQuery")
	public Object pageQuery(String queryText, Integer newsType, Integer pageno, Integer pagesize) {
		AJAXResult result = new AJAXResult();
		try {
		// ��ҳ��ѯ
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("start", (pageno - 1) * pagesize);
		map.put("size", pagesize);
		map.put("queryText", queryText);
		map.put("newsType", newsType);
		//��ѯ���Ż����
		List<News> news = newsService.pageQueryData(map);

		// ��ǰҳ��			
		// �ܵ���������
		int totalsize = newsService.pageQueryCount(map);
		
		int totalno = 0;
		if (totalsize % pagesize == 0) {
			totalno = totalsize / pagesize;
		} else {
			totalno = totalsize / pagesize + 1;
		}

		// ��ҳ����
		Page<News> newsPage = new Page<News>();
		newsPage.setDatas(news);
		newsPage.setTotalno(totalno);
		newsPage.setTotalsize(totalsize);
		newsPage.setPageno(pageno);

		result.setData(newsPage);
		result.setSuccess(true);
		}catch (Exception e) {
			e.printStackTrace();
			result.setSuccess(false);
		}
		

		return result;
	}
	
	//������Ż
	@ResponseBody
	@RequestMapping("/insert")
	public Object insert(News news) {
		AJAXResult result = new AJAXResult();
		try {
			//�����û�������ʱ��
			SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
			SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			news.setCreatetime(sdf1.format(new Date()));
			news.setUpdatetime(sdf2.format(new Date()));
			//��������ҳ����ȡ����������ҳ����
			if(news.getTop()==1) {
				newsService.updateNews(news);
				int newsid = newsService.findNewsWhereTop();
				newsService.cancelTop(newsid);
				
			}
			newsService.insertNews(news);
			
			result.setSuccess(true);
		}catch(Exception e) {
			e.printStackTrace();
			result.setSuccess(false);
		}
		
		return result;
	}
	
	@ResponseBody
	@RequestMapping("/findNewsById")
	public News findNewsById(int newsid) {
		return newsService.findNews(newsid);
	}
	
	//�������Ż
	@ResponseBody
	@RequestMapping("/update")
	public Object update(News news) {
		AJAXResult result = new AJAXResult();
		try {
			// �����û�������ʱ��
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			// �����޸�ʱ��
			news.setUpdatetime(sdf.format(new Date()));

			if(news.getTop()==1) {
				newsService.updateNews(news);
				int newsid = newsService.findNewsWhereTop();
				newsService.cancelTop(newsid);
				
			}
			newsService.updateNews(news);
			result.setSuccess(true);
		} catch (Exception e) {
			e.printStackTrace();
			result.setSuccess(false);
		}

		return result;
	}

		
	// ɾ���û���Ϣ�ķ���
	@ResponseBody
	@RequestMapping("/delete")
	public Object delete(int newsid) {
		AJAXResult result = new AJAXResult();

		try {
			newsService.deleteNews(newsid);
			result.setSuccess(true);
		} catch (Exception e) {
			e.printStackTrace();
			result.setSuccess(false);
		}

		return result;
	}
		
	// ����ɾ���û���Ϣ�ķ���
	// newsid������Ҫ�͸�ѡ��name����ֵ��ͬ
	@ResponseBody
	@RequestMapping("/deleteNewsList")
	public Object deleteNewsList(Integer[] newsid) {
		AJAXResult result = new AJAXResult();
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("newsid", newsid);
			newsService.deleteNewsList(map);
			result.setSuccess(true);
		} catch (Exception e) {
			e.printStackTrace();
			result.setSuccess(false);
		}

		return result;
	}
	
	//��ȡ��������͵�ǰ��
	@ResponseBody
	@RequestMapping("/findNewsType")
	public void findNewsType(Model model,HttpServletRequest request ,HttpServletResponse response){
		List<NewsType> list = newsTypeService.findNewsType();
		request.setAttribute("typeList",list);
		try {
			request.getRequestDispatcher("/WEB-INF/jsp/news_center.jsp").forward(request, response);
		} catch (ServletException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//��������б�ǰ��
	@RequestMapping("/getNewsData")
	public void getNewsData(HttpServletRequest request, HttpServletResponse response,int id) {
		List<NewsType> list = newsTypeService.findNewsType();
		request.setAttribute("typeList",list);
		NewsType newsType = newsTypeService.findNewsTypeById(id);
		request.setAttribute("newsType", newsType);
		try {
			request.getRequestDispatcher("/jsp/news_center.jsp").forward(request, response);
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//���������ϸ��Ϣ��ǰ��
	@RequestMapping("/showNews")
	public void showNews(HttpServletRequest request, HttpServletResponse response, int newsid) {
		News news = newsService.findNews(newsid);
		request.setAttribute("news", news);
		NewsType newsType = newsTypeService.findNewsTypeById(news.getType());
		request.setAttribute("newsType", newsType);
		try {
			request.getRequestDispatcher("/jsp/showNews.jsp").forward(request, response);
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//����ͷ������
	@ResponseBody
	@RequestMapping("/topNews")
	public News topNews() {
		//ͷ�����ŵı��Ϊ1
		News topNews = newsService.findTopNews(1);
		String content = topNews.getContent();
		if(content.length()>25) {
			content = content.substring(0, 25);
			content +="...." ;
			topNews.setContent(content);
		}
		
		return topNews;
		
	}
	
	@ResponseBody
	@RequestMapping("/lastedNews")
	public List<News> lastedNews(){
		List<News> list = newsService.findLastedNews();

		return list;
	}
		
}
