package com.portal.dynamic.entity;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.portal.common.api.DataEntity;

public class Dynamic extends DataEntity {

	private String name;
	private int num;
	private String content;
	private String status = "0";
	private String person;
	private String url;
	private String imgUrl;
	private String key;
	private String type;
	private String wys;

	private String createBy;

	private Date updateTime;
	private String createDateStr;
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:SS");

	public String getCreateDateStr() {
		return createDateStr;
	}
	public void setCreateDateStr(String createDateStr) {
		this.createDateStr = createDateStr;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
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
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
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
	public String getPerson() {
		return person;
	}
	public void setPerson(String person) {
		this.person = person;
	}
	public String getImgUrl() {
		return imgUrl;
	}
	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}
	public String getKey() {
		return this.getId();
	}
	public String getCreateBy() {
		return createBy;
	}
	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		if(updateTime!=null) {
			this.createDateStr = sdf.format(updateTime);
		}
		this.updateTime = updateTime;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getWys() {
		return wys;
	}

	public void setWys(String wys) {
		this.wys = wys;
	}
}