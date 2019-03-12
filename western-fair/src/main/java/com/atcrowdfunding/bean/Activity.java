package com.atcrowdfunding.bean;

public class Activity {
	
	private int id; // 活动编号
	private String name; // 活动名称
	private String address; // 活动地址
	private String time; // 活动时间
	private String introduce; // 活动日期
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getIntroduce() {
		return introduce;
	}
	public void setIntroduce(String introduce) {
		this.introduce = introduce;
	}
	@Override
	public String toString() {
		return "Activity [id=" + id + ", name=" + name + ", address=" + address + ", time=" + time + ", introduce="
				+ introduce + "]";
	}
	
}
