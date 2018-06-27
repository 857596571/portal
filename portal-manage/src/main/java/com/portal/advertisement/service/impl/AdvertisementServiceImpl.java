package com.portal.advertisement.service.impl;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Random;

import org.aspectj.weaver.ast.Var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.portal.OSSClientUtil;
import com.portal.advertisement.entity.Advertisement;
import com.portal.advertisement.mapper.AdvertisementMapper;
import com.portal.advertisement.service.AdvertisementService;
import com.portal.common.api.Paging;
import com.portal.common.upload.AliyunCloudStorageService;
import com.portal.common.upload.CloudStorageConfig;


@Service
public class AdvertisementServiceImpl implements AdvertisementService {

	@Autowired
	private AdvertisementMapper advertisementMapper;
	
	

	@Override
	public List<Advertisement> getAdvertisement( ) {
		return advertisementMapper.getAdvertisement();
	}

	@Override
	@Transactional
	public void update(Advertisement advertisement) {
		advertisementMapper.update(advertisement);
	}
	
	@Override
	@Transactional
	public void delete(int id) {
		advertisementMapper.delete(id);
	}
	
	@Override
	@Transactional
	public void add(Advertisement advertisement) {
		advertisementMapper.add(advertisement);
	}


	@Override
	public PageInfo<Advertisement> getList(Paging page, Advertisement advertisement) {
		 // 执行分页查询
        PageHelper.startPage(page.getPageNum(), page.getPageSize(), page.getOrderBy());
        List<Advertisement> list = advertisementMapper.getList(advertisement);
        return new PageInfo<>(list);
	}

	@Override
	@Transactional
	public void sortNum(int id,int upOrDown) {
		// 排序
		Advertisement ad1 = advertisementMapper.get(id);
		Advertisement ad2 = null;

		if(upOrDown == 3) {
			ad2 = advertisementMapper.getFirst();
			if(ad2  != null && ad1 != null && !ad1.getId().equals(ad2.getId())){
				ad1.setNum(ad2.getNum()-1);
				update(ad1);
			}
			return;
		}
		if(upOrDown == 0){
			ad2 = advertisementMapper.getUp(id);
		}else{
			ad2 = advertisementMapper.getDown(id);
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
	

	
	
	@Override
	  public String updateHead(MultipartFile file) throws IOException{
	    if (file == null || file.getSize() <= 0) {
	    	System.out.println("图片上传失败");
	    }
	    OSSClientUtil ossClient = new OSSClientUtil();
	    String name = ossClient.uploadImg2Oss(file);
	    String imgUrl = ossClient.getImgUrl(name);
	    //userDao.updateHead(userId, imgUrl);//只是本地上传使用的
	    return imgUrl;
	  }
}
