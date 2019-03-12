package com.atcrowdfunding.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Select;

import com.atcrowdfunding.bean.Link;

public interface LinkDao {

	List<Link> pageQueryData(Map<String, Object> map);

	int pageQueryCount(Map<String, Object> map);

	void insertLink(Link link);

	@Select("select * from xbh_link where id=#{id}")
	Link queryById(Integer id);

	void updateLink(Link link);

	void deleteLinkById(Integer id);

	void deleteLinks(Map<String, Object> map);

}
