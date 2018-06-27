package com.portal.mainbusiness.service;

import java.util.List;

import com.github.pagehelper.PageInfo;
import com.portal.common.api.Paging;
import com.portal.mainbusiness.entity.Mainbusiness;

public interface MainbusinessService {

	List<Mainbusiness> getMainbusiness();

	void update(Mainbusiness mainbusiness);
	
	void add(Mainbusiness mainbusiness);
	
	void delete(int id);

	PageInfo<Mainbusiness> getList(Paging page, Mainbusiness mainbusiness);

	List<Mainbusiness> getMainbusinessChild(int id);
	
	void sortNum(int id,int upOrDown);

}
