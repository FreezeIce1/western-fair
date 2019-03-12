package com.atcrowdfunding.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Select;

import com.atcrowdfunding.bean.NewsType;
import com.atcrowdfunding.bean.Partner;
import com.atcrowdfunding.bean.PartnerType;

public interface PartnerDao {
	//查询所有
	@Select("select * from xbh_partner")
	List<Partner> queryAll();
	
	//通过id 查询 合作伙伴|历届展商|媒体
	@Select("select * from xbh_partner where id = #{id}")
	Partner queryById(Integer id);
	
	List<Partner> pageQueryData(Map<String, Object> map);

	int pageQueryCount(Map<String, Object> map);
	
	void updatePartner(Partner partner);
	
	void insertPartner(Partner partner); //插入
	
	void deletePartnerById(Integer id);

	void deletePartners(Map<String, Object> map);
	//查询类型
	List<PartnerType> findPartnerType();
	
}
