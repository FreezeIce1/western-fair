package com.atcrowdfunding.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.atcrowdfunding.bean.Partner;
import com.atcrowdfunding.bean.PartnerType;
import com.atcrowdfunding.dao.PartnerDao;
import com.atcrowdfunding.service.PartnerService;
@Service
public class PartnerServiceImpl implements PartnerService {
	@Autowired
	private PartnerDao partnerDao;
	
	@Override
	public List<Partner> queryAll() {
		// TODO Auto-generated method stub
		return partnerDao.queryAll();
	}
	@Override
	public Partner queryById(Integer typeid) {
		// TODO Auto-generated method stub
		return partnerDao.queryById(typeid);
	}
	@Override
	public List<Partner> pageQueryData(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return partnerDao.pageQueryData(map);
	}
	@Override
	public int pageQueryCount(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return partnerDao.pageQueryCount(map);
	}
	@Override
	public void insertPartner(Partner partner) {
		// TODO Auto-generated method stub
		partnerDao.insertPartner(partner); //≤Â»Î
	}
	@Override
	public void updatePartner(Partner partner) {
		// TODO Auto-generated method stub
		partnerDao.updatePartner(partner);
	}
	@Override
	public void deletePartnerById(Integer typeid) {
		// TODO Auto-generated method stub
		partnerDao.deletePartnerById(typeid);
	}
	@Override
	public void deletePartners(Map<String, Object> map) {
		// TODO Auto-generated method stub
		partnerDao.deletePartners(map);
	}
	@Override
	public List<PartnerType> findPartnerType() {
		// TODO Auto-generated method stub
		return partnerDao.findPartnerType();
	}

}
