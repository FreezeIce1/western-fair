package com.atcrowdfunding.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Select;

import com.atcrowdfunding.bean.NewsType;
import com.atcrowdfunding.bean.Partner;
import com.atcrowdfunding.bean.PartnerType;

public interface PartnerDao {
	//��ѯ����
	@Select("select * from xbh_partner")
	List<Partner> queryAll();
	
	//ͨ��id ��ѯ �������|����չ��|ý��
	@Select("select * from xbh_partner where id = #{id}")
	Partner queryById(Integer id);
	
	List<Partner> pageQueryData(Map<String, Object> map);

	int pageQueryCount(Map<String, Object> map);
	
	void updatePartner(Partner partner);
	
	void insertPartner(Partner partner); //����
	
	void deletePartnerById(Integer id);

	void deletePartners(Map<String, Object> map);
	//��ѯ����
	List<PartnerType> findPartnerType();
	
}
