package com.portal.advertisement.entity;

import com.portal.common.api.DataEntity;

public class Advertisement extends DataEntity {
	
	private String name;
	private int num;
	private String desce;
	private String status = "0";
	private String url;
	private String imgUrl;
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
	
	
}
