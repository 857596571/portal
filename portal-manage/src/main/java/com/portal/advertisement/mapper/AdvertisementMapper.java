package com.portal.advertisement.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;
import com.portal.advertisement.entity.Advertisement;

@Mapper
public interface AdvertisementMapper {

	public List<Advertisement> getAdvertisement();

	public Advertisement get(int id);
	
	public Advertisement getUp(int id);
	
	public Advertisement getDown(int id);
	public Advertisement getFirst();

	public void update(Advertisement advertisement);
	
	public void add(Advertisement advertisement);
	
	public void delete(int id);

	public List<Advertisement> getList(Advertisement advertisement);

}
