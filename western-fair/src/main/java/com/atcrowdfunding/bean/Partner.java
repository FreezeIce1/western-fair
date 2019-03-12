package com.atcrowdfunding.bean;

import org.springframework.web.multipart.MultipartFile;

public class Partner {
	private Integer id; 
	private String name; // 名称
	private String image;//图片地址
	private String url;  //链接地址
	private String addtime; //添加时间
	private String updatetime; //修改时间
	private Integer type;
	private MultipartFile file;  
	private PartnerType partnerType;
	public PartnerType getPartnerType() {
		return partnerType;
	}
	public void setPartnerType(PartnerType partnerType) {
		this.partnerType = partnerType;
	}
	public MultipartFile getFile() {
		return file;
	}
	public void setFile(MultipartFile file) {
		this.file = file;
	}
	public String getUpdatetime() {
		return updatetime;
	}
	public void setUpdatetime(String updatetime) {
		this.updatetime = updatetime;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getAddtime() {
		return addtime;
	}
	public void setAddtime(String addtime) {
		this.addtime = addtime;
	}
}
