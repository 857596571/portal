package com.portal.person.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.portal.person.entity.Person;
import com.portal.person.mapper.PersonMapper;
import com.portal.person.service.PersonService;
import com.portal.common.api.Paging;
import com.portal.dynamic.entity.Dynamic;

@Service
public class PersonServiceImpl implements PersonService {

	@Autowired
	private PersonMapper personMapper;

	@Override
	public List<Person> getPerson( ) {
		return personMapper.getPerson();
	}

	@Override
	@Transactional
	public void update(Person person) {
		personMapper.update(person);
	}
	
	@Override
	@Transactional
	public void delete(int id) {
		personMapper.delete(id);
	}
	
	@Override
	@Transactional
	public void add(Person person) {
		personMapper.add(person);
	}


	@Override
	public PageInfo<Person> getList(Paging page, Person person) {
		 // 执行分页查询
        PageHelper.startPage(page.getPageNum(), page.getPageSize(), page.getOrderBy());
        List<Person> list = personMapper.getList(person);
        return new PageInfo<>(list);
	}
	@Override
	@Transactional
	public void sortNum(int id,int upOrDown) {
		// 排序
		Person ad1 = personMapper.get(id);
		Person ad2 = null;
		if(upOrDown == 3) {
			ad2 = personMapper.getFirst();
			if(ad2  != null && ad1 != null && !ad1.getId().equals(ad2.getId())){
				ad1.setNum(ad2.getNum()-1);
				update(ad1);
			}
			return;
		}
		if(upOrDown == 0){
			ad2 = personMapper.getUp(id);
		}else{
			ad2 = personMapper.getDown(id);
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
