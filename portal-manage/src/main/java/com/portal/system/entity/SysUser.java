package com.portal.system.entity;

import com.portal.common.api.DataEntity;

public class SysUser extends DataEntity {
	
	private String name;
	private String newPwd;
	private String pwd;
	private String avatar;
	private String userid;
	private String notifyCount;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	public String getAvatar() {
		return avatar;
	}
	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getNotifyCount() {
		return notifyCount;
	}
	public void setNotifyCount(String notifyCount) {
		this.notifyCount = notifyCount;
	}
	public String getNewPwd() {
		return newPwd;
	}
	public void setNewPwd(String newPwd) {
		this.newPwd = newPwd;
	}	
	
}
