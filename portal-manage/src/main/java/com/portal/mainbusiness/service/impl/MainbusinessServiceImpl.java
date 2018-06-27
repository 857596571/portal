package com.portal.mainbusiness.service.impl;

import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.portal.mainbusiness.entity.Mainbusiness;
import com.portal.mainbusiness.mapper.MainbusinessMapper;
import com.portal.mainbusiness.service.MainbusinessService;
import com.portal.advertisement.entity.Advertisement;
import com.portal.common.api.Paging;

@Service
public class MainbusinessServiceImpl implements MainbusinessService {

	@Autowired
	private MainbusinessMapper mainbusinessMapper;

	@Override
	public List<Mainbusiness> getMainbusiness() {
		return mainbusinessMapper.getMainbusiness();
	}

	@Override
	@Transactional
	public void update(Mainbusiness mainbusiness) {
		mainbusinessMapper.update(mainbusiness);
	}
	
	@Override
	@Transactional
	public void delete(int id) {
		mainbusinessMapper.delete(id);
	}
	
	@Override
	@Transactional
	public void add(Mainbusiness mainbusiness) {
		mainbusinessMapper.add(mainbusiness);
	}


	@Override
	public PageInfo<Mainbusiness> getList(Paging page, Mainbusiness mainbusiness) {
		 // 执行分页查询
        PageHelper.startPage(page.getPageNum(), page.getPageSize(), page.getOrderBy());
        List<Mainbusiness> list = mainbusinessMapper.getList(mainbusiness);
        for (int i = 0; i < list.size(); i++) {
        	List<Mainbusiness> children = mainbusinessMapper.getMainbusinessChild(Integer.valueOf(list.get(i).getId()));
			
        	for (int y = 0; y < children.size(); y++) {
        		children.get(y).setUname(list.get(i).getName());
			}
        	list.get(i).setChildren(children);
		}
        return new PageInfo<>(list);
	}

	@Override
	public List<Mainbusiness> getMainbusinessChild(int id) {
		// TODO Auto-generated method stub
		return mainbusinessMapper.getMainbusinessChild(id);
	}
	
	@Override
	@Transactional
	public void sortNum(int id,int upOrDown) {
		// 排序
		Mainbusiness ad1 = mainbusinessMapper.get(id);
		Mainbusiness ad2 = null;
		if(upOrDown == 3) {
			ad2 = mainbusinessMapper.getFirst(id);
			if(ad2  != null && ad1 != null && !ad1.getId().equals(ad2.getId())){
				ad1.setNum(ad2.getNum()-1);
				update(ad1);
			}
			return;
		}
		if(upOrDown == 0){
			ad2 = mainbusinessMapper.getUp(id);
		}else{
			mainbusinessMapper.getDown(id); 
			ad2 = mainbusinessMapper.getDown(id);
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
