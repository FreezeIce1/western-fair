package com.atcrowdfunding.service;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Select;

import com.atcrowdfunding.bean.Partner;
import com.atcrowdfunding.bean.PartnerType;

public interface PartnerTypeService {
	//查询所有
			@Select("select * from xbh_partnerType")
			List<PartnerType> queryAll();
			
			//通过id 查询 合作伙伴|历届展商|媒体
			@Select("select * from xbh_partnerType where typeid = #{typeid}")
			PartnerType queryById(Integer typeid);
			
			List<PartnerType> pageQueryData(Map<String, Object> map);

			int pageQueryCount(Map<String, Object> map);
			
			void updatePartnerType(PartnerType partnerType);
			
			void insertPartnerType(PartnerType partnerType); //插入
			
			void deletePartnerTypeById(Integer typeid);

			void deletePartnerTypes(Map<String, Object> map);

			//查询该typeid是否有关联数据
			List<Partner> findPartnerBytype(Integer typeid);
}
