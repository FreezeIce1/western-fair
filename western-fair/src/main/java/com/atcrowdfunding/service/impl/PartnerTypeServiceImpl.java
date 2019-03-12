package com.atcrowdfunding.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.atcrowdfunding.bean.Partner;
import com.atcrowdfunding.bean.PartnerType;
import com.atcrowdfunding.dao.PartnerTypeDao;
import com.atcrowdfunding.service.PartnerTypeService;
@Service
public class PartnerTypeServiceImpl implements PartnerTypeService {
	@Autowired
	private PartnerTypeDao partnerTypeDao;
	@Override
	public List<PartnerType> queryAll() {
		// TODO Auto-generated method stub
		return partnerTypeDao.queryAll();
	}

	@Override
	public PartnerType queryById(Integer id) {
		// TODO Auto-generated method stub
		return partnerTypeDao.queryById(id);
	}

	@Override
	public List<PartnerType> pageQueryData(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return partnerTypeDao.pageQueryData(map);
	}

	@Override
	public int pageQueryCount(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return partnerTypeDao.pageQueryCount(map);
	}

	@Override
	public void updatePartnerType(PartnerType partnerType) {
		// TODO Auto-generated method stub
		partnerTypeDao.updatePartnerType(partnerType);
	}

	@Override
	public void insertPartnerType(PartnerType partnerType) {
		// TODO Auto-generated method stub
		partnerTypeDao.insertPartnerType(partnerType);
	}

	@Override
	public void deletePartnerTypeById(Integer typeid) {
		// TODO Auto-generated method stub
		partnerTypeDao.deletePartnerTypeById(typeid);
	}



	@Override
	public void deletePartnerTypes(Map<String, Object> map) {
		// TODO Auto-generated method stub
		partnerTypeDao.deletePartnerTypes(map);
	}

	@Override
	public List<Partner> findPartnerBytype(Integer typeid) {
		// TODO Auto-generated method stub
		return partnerTypeDao.findPartnerBytype(typeid);
	}

}
