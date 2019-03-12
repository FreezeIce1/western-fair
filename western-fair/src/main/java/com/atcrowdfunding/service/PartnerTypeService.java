package com.atcrowdfunding.service;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Select;

import com.atcrowdfunding.bean.Partner;
import com.atcrowdfunding.bean.PartnerType;

public interface PartnerTypeService {
	//��ѯ����
			@Select("select * from xbh_partnerType")
			List<PartnerType> queryAll();
			
			//ͨ��id ��ѯ �������|����չ��|ý��
			@Select("select * from xbh_partnerType where typeid = #{typeid}")
			PartnerType queryById(Integer typeid);
			
			List<PartnerType> pageQueryData(Map<String, Object> map);

			int pageQueryCount(Map<String, Object> map);
			
			void updatePartnerType(PartnerType partnerType);
			
			void insertPartnerType(PartnerType partnerType); //����
			
			void deletePartnerTypeById(Integer typeid);

			void deletePartnerTypes(Map<String, Object> map);

			//��ѯ��typeid�Ƿ��й�������
			List<Partner> findPartnerBytype(Integer typeid);
}
