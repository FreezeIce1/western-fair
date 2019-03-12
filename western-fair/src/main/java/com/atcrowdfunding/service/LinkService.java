package com.atcrowdfunding.service;

import java.util.List;
import java.util.Map;

import com.atcrowdfunding.bean.Link;

public interface LinkService {

	List<Link> pageQueryData(Map<String, Object> map);

	int pageQueryCount(Map<String, Object> map);

	void insertLink(Link link);

	Link queryById(Integer id);

	void updateLink(Link link);

	void deleteLinkById(Integer id);

	void deleteLinks(Map<String, Object> map);

}
