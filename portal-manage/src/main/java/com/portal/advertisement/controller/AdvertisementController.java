package com.portal.advertisement.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.portal.OSSClientUtil;
import com.portal.advertisement.entity.Advertisement;
import com.portal.advertisement.service.AdvertisementService;
import com.portal.common.api.Paging;
import com.portal.common.utils.http.ResponseMessage;
import com.portal.common.utils.http.Result;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;

@Api
@RestController
@RequestMapping("advertisement")
public class AdvertisementController {
	
	@Autowired
	private AdvertisementService advertisementService;
	
	@PostMapping(value = "/search")
    public ResponseMessage getAdvertisement() {
		List<Advertisement> su = advertisementService.getAdvertisement();
		
        return Result.success(su);
    }
	
	@PostMapping(value = "/add")
    public ResponseMessage add(@RequestBody Advertisement advertisement) {
		if(advertisement.getId()!=null){
			advertisement.preInsert();
			advertisementService.update(advertisement);
		}else{
			advertisement.preInsert();
			advertisementService.add(advertisement);
		}
		
		
        return Result.success();
    }
	
	@PostMapping(value = "/delete")
    public ResponseMessage delete(@RequestBody int id) {
		advertisementService.delete(id);
		
        return Result.success();
    }
	@PostMapping(value = "/deletes")
    public ResponseMessage deletes(@RequestBody String[] ids) {
		//String[] id = ids.split(",");
		for(String v:ids){
			advertisementService.delete(Integer.valueOf(v));
		}
        return Result.success();
    }
	
	@PostMapping(value = "sortUp")
	public ResponseMessage sortUp(@RequestParam("id") Integer id){
		advertisementService.sortNum(id, 0);
		
		return Result.success();
	}
	
	@PostMapping(value = "sortDown")
	public ResponseMessage sortDown(@RequestParam("id") Integer id){
		advertisementService.sortNum(id, 1);
		
		return Result.success();
	}

	@PostMapping(value = "sortFirst")
	public ResponseMessage sortFirst(@RequestParam("id") Integer id){
		advertisementService.sortNum(id, 3);

		return Result.success();
	}
	
	@ApiImplicitParams({
        // Filter Param
        @ApiImplicitParam(name = "query", value = "查询条件", paramType = "query", dataType = "Map"),
        // Paging Param
        @ApiImplicitParam(name = "pageNum", value = "当前页（从0开始）", paramType = "query", dataType = "int", required = true),
        @ApiImplicitParam(name = "pageSize", value = "每页显示记录数", paramType = "query", dataType = "int", required = true)
	})
	@GetMapping(value = "/getList")
    public ResponseMessage getList(Advertisement advertisement, Paging page) {
        return Result.success(advertisementService.getList(page, advertisement));
    }
	
	@PostMapping(value = "/update")
    public ResponseMessage update(@RequestBody Advertisement advertisement) {
		advertisement.preInsert();
		advertisementService.update(advertisement);
        return Result.success();
    }
	
    @PostMapping(value = "/upload")
    public ResponseMessage headImgUpload(@RequestParam("file") MultipartFile file) {
	   
	 //此处是调用上传服务接口，4是需要更新的userId 测试数据。
	 //String head = advertisementService.updateHead(file);
	 OSSClientUtil ossClient = new OSSClientUtil();
	 String name = ossClient.uploadImg2Oss(file);
	 String imgUrl = ossClient.getImgUrl(name);
	 System.out.println(imgUrl);
	 return Result.success(imgUrl);
	   
	   
    }
    
}
