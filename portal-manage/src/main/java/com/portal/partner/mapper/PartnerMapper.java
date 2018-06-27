package com.portal.partner.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.portal.partner.entity.Partner;

@Mapper
public interface PartnerMapper {

	public List<Partner> getPartner();
	public Partner get(int id);
	
	public Partner getUp(int id);
	
	public Partner getDown(int id);
	public Partner getFirst();
	public void update(Partner partner);
	
	public void add(Partner partner);
	
	public void delete(int id);

	public List<Partner> getList(Partner partner);

}
