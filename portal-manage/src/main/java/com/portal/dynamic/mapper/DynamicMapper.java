package com.portal.dynamic.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.portal.advertisement.entity.Advertisement;
import com.portal.dynamic.entity.Dynamic;

@Mapper
public interface DynamicMapper {

	public List<Dynamic> getDynamic();
	
	public Dynamic get(int id);
	
	public Dynamic getUp(int id);
	
	public Dynamic getDown(int id);
	public Dynamic getFirst();
	public void update(Dynamic dynamic);
	
	public void add(Dynamic dynamic);
	
	public void delete(int id);

	public List<Dynamic> getList(Dynamic dynamic);

}
