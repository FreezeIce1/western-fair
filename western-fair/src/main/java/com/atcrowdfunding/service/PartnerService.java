package com.atcrowdfunding.service;

import java.util.List;
import java.util.Map;

import com.atcrowdfunding.bean.Partner;
import com.atcrowdfunding.bean.PartnerType;

public interface PartnerService {
	//查询所有

	List<Partner> queryAll();
	//通过id 查询 合作伙伴|历届展商|媒体
	
	Partner queryById(Integer id);
	List<Partner> pageQueryData(Map<String, Object> map);

	int pageQueryCount(Map<String, Object> map);
	
	void insertPartner(Partner partner); //插入
	
	void updatePartner(Partner partner);
	
	void deletePartnerById(Integer id);

	void deletePartners(Map<String, Object> map);

	//查询类型
	List<PartnerType> findPartnerType();
	
	
}
