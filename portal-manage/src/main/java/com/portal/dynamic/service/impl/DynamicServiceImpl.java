package com.portal.dynamic.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.portal.dynamic.entity.Dynamic;
import com.portal.dynamic.mapper.DynamicMapper;
import com.portal.dynamic.service.DynamicService;
import com.portal.common.api.Paging;

@Service
public class DynamicServiceImpl implements DynamicService {

	@Autowired
	private DynamicMapper dynamicMapper;

	@Override
	public List<Dynamic> getDynamic( ) {
		return dynamicMapper.getDynamic();
	}

	@Override
	@Transactional
	public void update(Dynamic dynamic) {
		dynamicMapper.update(dynamic);
	}
	
	@Override
	@Transactional
	public void delete(int id) {
		dynamicMapper.delete(id);
	}
	
	@Override
	@Transactional
	public void add(Dynamic dynamic) {
		dynamicMapper.add(dynamic);
	}


	@Override
	public PageInfo<Dynamic> getList(Paging page, Dynamic dynamic) {
		 // 执行分页查询
        PageHelper.startPage(page.getPageNum(), page.getPageSize(), page.getOrderBy());
        List<Dynamic> list = dynamicMapper.getList(dynamic);
        return new PageInfo<>(list);
	}
	@Override
	@Transactional
	public void sortNum(int id,int upOrDown) {
		// 排序
		Dynamic ad1 = dynamicMapper.get(id);
		Dynamic ad2 = null;
		if(upOrDown == 3) {
			ad2 = dynamicMapper.getFirst();
			if(ad2  != null && ad1 != null && !ad1.getId().equals(ad2.getId())){
				ad1.setNum(ad2.getNum()-1);
				update(ad1);
			}
			return;
		}
		if(upOrDown == 0){
			ad2 = dynamicMapper.getUp(id);
		}else{
			ad2 = dynamicMapper.getDown(id);
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
