package com.atcrowdfunding.service;

import java.util.List;
import java.util.Map;

import com.atcrowdfunding.bean.Partner;
import com.atcrowdfunding.bean.PartnerType;

public interface PartnerService {
	//��ѯ����

	List<Partner> queryAll();
	//ͨ��id ��ѯ �������|����չ��|ý��
	
	Partner queryById(Integer id);
	List<Partner> pageQueryData(Map<String, Object> map);

	int pageQueryCount(Map<String, Object> map);
	
	void insertPartner(Partner partner); //����
	
	void updatePartner(Partner partner);
	
	void deletePartnerById(Integer id);

	void deletePartners(Map<String, Object> map);

	//��ѯ����
	List<PartnerType> findPartnerType();
	
	
}
