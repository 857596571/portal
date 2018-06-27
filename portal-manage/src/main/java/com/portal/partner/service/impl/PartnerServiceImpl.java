package com.portal.partner.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.portal.partner.entity.Partner;
import com.portal.partner.mapper.PartnerMapper;
import com.portal.partner.service.PartnerService;
import com.portal.common.api.Paging;
import com.portal.dynamic.entity.Dynamic;

@Service
public class PartnerServiceImpl implements PartnerService {

	@Autowired
	private PartnerMapper partnerMapper;

	@Override
	public List<Partner> getPartner( ) {
		return partnerMapper.getPartner();
	}

	@Override
	@Transactional
	public void update(Partner partner) {
		partnerMapper.update(partner);
	}
	
	@Override
	@Transactional
	public void delete(int id) {
		partnerMapper.delete(id);
	}
	
	@Override
	@Transactional
	public void add(Partner partner) {
		partnerMapper.add(partner);
	}


	@Override
	public PageInfo<Partner> getList(Paging page, Partner partner) {
		 // 执行分页查询
        PageHelper.startPage(page.getPageNum(), page.getPageSize(), page.getOrderBy());
        List<Partner> list = partnerMapper.getList(partner);
        return new PageInfo<>(list);
	}
	
	@Override
	@Transactional
	public void sortNum(int id,int upOrDown) {
		// 排序
		Partner ad1 = partnerMapper.get(id);
		Partner ad2 = null;
		if(upOrDown == 3) {
			ad2 = partnerMapper.getFirst();
			if(ad2  != null && ad1 != null && !ad1.getId().equals(ad2.getId())){
				ad1.setNum(ad2.getNum()-1);
				update(ad1);
			}
			return;
		}
		if(upOrDown == 0){
			ad2 = partnerMapper.getUp(id);
		}else{
			ad2 = partnerMapper.getDown(id);
		}
		
		if(ad2  == null || ad1 == null){
			return;
		}
		int currNum = ad1.getNum();
		int reNum = ad2.getNum();
		ad1.setNum(reNum);
		ad2.setNum(currNum);
		update(ad1);
		update(ad2);
		
		
	}
}
