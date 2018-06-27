package com.portal.person.entity;

import com.portal.common.api.DataEntity;

public class Person extends DataEntity {
	
	private String name;
	private int num;
	private String post;
	private String status = "0";
	private String detail;
	private String desce;
	private String url;
	private String imgUrl;
	private String key;
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
	
	public String getPost() {
		return post;
	}
	public void setPost(String post) {
		this.post = post;
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
	public String getDetail() {
		return detail;
	}
	public void setDetail(String detail) {
		this.detail = detail;
	}
	public String getDesce() {
		return desce;
	}
	public void setDesce(String desce) {
		this.desce = desce;
	}
	public String getKey() {
		return this.getId();
	}
	
	
}
