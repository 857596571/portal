package com.portal.mainbusiness.entity;

import java.util.List;

import com.portal.common.api.DataEntity;

public class Mainbusiness extends DataEntity {
	
	private String name;
	private int num;
	private int pid;
	private String uname;
	private String desce;
	private String status= "0";
	private String url;
	private String imgUrl;
	private String key;
	private List<Mainbusiness> children;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	public String getUname() {
		return uname;
	}
	public void setUname(String uname) {
		this.uname = uname;
	}
	public String getDesce() {
		return desce;
	}
	public void setDesce(String desce) {
		this.desce = desce;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		if(status == "true"){
			this.status = "1";
			return;
		}else if( status == "false"){
			this.status = "0";
			return;
		}
		this.status = status;
	}
	
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getImgUrl() {
		return imgUrl;
	}
	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}
	public int getPid() {
		return pid;
	}
	public void setPid(int pid) {
		this.pid = pid;
	}
	public List<Mainbusiness> getChildren() {
		return children;
	}
	public void setChildren(List<Mainbusiness> children) {
		this.children = children;
	}
	public String getKey() {
		return this.getId();
	}
	
	
}
