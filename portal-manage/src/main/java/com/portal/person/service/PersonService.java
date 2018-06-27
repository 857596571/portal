package com.portal.person.service;

import java.util.List;

import com.github.pagehelper.PageInfo;
import com.portal.person.entity.Person;
import com.portal.common.api.Paging;

public interface PersonService {

	List<Person> getPerson();

	void update(Person person);
	
	void add(Person person);
	
	void delete(int id);

	PageInfo<Person> getList(Paging page, Person person);
	
	void sortNum(int id,int upOrDown);

}
