package com.portal.partner.service;

import java.util.List;

import com.github.pagehelper.PageInfo;
import com.portal.partner.entity.Partner;
import com.portal.common.api.Paging;

public interface PartnerService {

	List<Partner> getPartner();

	void update(Partner partner);
	
	void add(Partner partner);
	
	void delete(int id);

	PageInfo<Partner> getList(Paging page, Partner partner);
	
	void sortNum(int id,int upOrDown);

}
