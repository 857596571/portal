package com.portal.mainbusiness.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.portal.advertisement.entity.Advertisement;
import com.portal.mainbusiness.entity.Mainbusiness;

@Mapper
public interface MainbusinessMapper {

	public Mainbusiness get(int id);
	
	public Mainbusiness getUp(int id);
	
	public Mainbusiness getDown(int id);
	public Mainbusiness getFirst(int id);
	public List<Mainbusiness> getMainbusiness();

	public void update(Mainbusiness mainbusiness);
	
	public void add(Mainbusiness mainbusiness);
	
	public void delete(int id);

	public List<Mainbusiness> getList(Mainbusiness mainbusiness);

	public List<Mainbusiness> getMainbusinessChild(int id);

}
