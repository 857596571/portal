package com.portal.person.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.portal.person.entity.Person;

@Mapper
public interface PersonMapper {

	public List<Person> getPerson();
	public Person get(int id);
	
	public Person getUp(int id);
	
	public Person getDown(int id);
	public Person getFirst();
	public void update(Person person);
	
	public void add(Person person);
	
	public void delete(int id);

	public List<Person> getList(Person person);

}
