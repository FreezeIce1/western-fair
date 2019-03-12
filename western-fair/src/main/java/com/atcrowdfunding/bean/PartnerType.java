package com.atcrowdfunding.bean;

import java.util.List;

public class PartnerType {
	private int typeid;
	private String typename;
	private String typeaddtime;
	private String typeupdatetime;
	private List<Partner> partnersList;
	public List<Partner> getPartnersList() {
		return partnersList;
	}
	public void setPartnersList(List<Partner> partnersList) {
		this.partnersList = partnersList;
	}
	public int getTypeid() {
		return typeid;
	}
	public void setTypeid(int typeid) {
		this.typeid = typeid;
	}
	public String getTypename() {
		return typename;
	}
	public void setTypename(String typename) {
		this.typename = typename;
	}
	public String getTypeaddtime() {
		return typeaddtime;
	}
	public void setTypeaddtime(String typeaddtime) {
		this.typeaddtime = typeaddtime;
	}
	public String getTypeupdatetime() {
		return typeupdatetime;
	}
	public void setTypeupdatetime(String typeupdatetime) {
		this.typeupdatetime = typeupdatetime;
	}


	
}
