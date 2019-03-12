package com.atcrowdfunding.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.atcrowdfunding.bean.Link;
import com.atcrowdfunding.dao.LinkDao;
import com.atcrowdfunding.service.LinkService;

@Service
public class LinkServiceImpl implements LinkService{

	@Autowired
	private LinkDao linkDao;
	
	@Override
	public List<Link> pageQueryData(Map<String, Object> map) {
		return linkDao.pageQueryData(map);
	}

	@Override
	public int pageQueryCount(Map<String, Object> map) {
		return linkDao.pageQueryCount(map);
	}

	@Override
	public void insertLink(Link link) {
		linkDao.insertLink(link);
	}

	@Override
	public Link queryById(Integer id) {
		return linkDao.queryById(id);
	}

	@Override
	public void updateLink(Link link) {
		linkDao.updateLink(link);
	}

	@Override
	public void deleteLinkById(Integer id) {
		linkDao.deleteLinkById(id);
	}

	@Override
	public void deleteLinks(Map<String, Object> map) {
		linkDao.deleteLinks(map);
	}

}
