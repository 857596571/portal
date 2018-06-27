package com.portal.dynamic.service;

import java.util.List;

import com.github.pagehelper.PageInfo;
import com.portal.dynamic.entity.Dynamic;
import com.portal.common.api.Paging;

public interface DynamicService {

	List<Dynamic> getDynamic();

	void update(Dynamic dynamic);
	
	void add(Dynamic dynamic);
	
	void delete(int id);

	PageInfo<Dynamic> getList(Paging page, Dynamic dynamic);
	
	void sortNum(int id,int upOrDown);

}
