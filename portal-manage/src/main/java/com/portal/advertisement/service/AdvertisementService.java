package com.portal.advertisement.service;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.github.pagehelper.PageInfo;
import com.portal.advertisement.entity.Advertisement;
import com.portal.common.api.Paging;

public interface AdvertisementService {

	List<Advertisement> getAdvertisement();

	void update(Advertisement advertisement);
	
	void add(Advertisement advertisement);
	
	void delete(int id);

	PageInfo<Advertisement> getList(Paging page, Advertisement advertisement);
	
	


	void sortNum(int id, int upOrDown);

	String updateHead(MultipartFile file) throws IOException;

}
