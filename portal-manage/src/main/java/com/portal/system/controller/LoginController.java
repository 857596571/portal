package com.portal.system.controller;

import org.aspectj.weaver.ast.Var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.portal.common.api.Paging;
import com.portal.common.utils.http.ResponseMessage;
import com.portal.common.utils.http.Result;
import com.portal.system.Md5Encrypt;
import com.portal.system.entity.SysUser;
import com.portal.system.service.SysUserService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;

@Api
@RestController
@RequestMapping("")
public class LoginController {
	
	@Autowired
	private SysUserService sysUserService;
	
	@PostMapping(value = "/login")
    public ResponseMessage login(@RequestBody SysUser sysUser) {
		SysUser login = sysUserService.getByLoginName(sysUser);
		Md5Encrypt md5 = new Md5Encrypt();
		String lPwd = md5.md5(sysUser.getPwd());
		System.out.println(lPwd);
		if(login.getPwd().equals(lPwd)){
			return Result.success(login);
		}
		return Result.error("用户名或密码不正确");
    }
	
	@GetMapping(value = "/currentUser")
	public SysUser currentUser(){
		SysUser sysUser = new SysUser();
		sysUser.setName("admin");
		sysUser.setAvatar("https://gw.alipayobjects.com/zos/rmsportal/BiazfanxmamNRoxxVxka.png");
		sysUser.setUserid("001");
		sysUser.setNotifyCount("12");
		SysUser login = sysUserService.getByLoginName(sysUser);
		sysUser.setUpdateDate(login.getUpdateDate());
		return sysUser;
	}
	
	@ApiImplicitParams({
        // Filter Param
        @ApiImplicitParam(name = "query", value = "查询条件", paramType = "query", dataType = "Map"),
        // Paging Param
        @ApiImplicitParam(name = "pageNumber", value = "当前页（从0开始）", paramType = "query", dataType = "int", required = true),
        @ApiImplicitParam(name = "pageSize", value = "每页显示记录数", paramType = "query", dataType = "int", required = true)
	})
	@GetMapping(value = "/getList")
    public ResponseMessage getList(SysUser user, Paging page) {
        return Result.success(sysUserService.getList(page, user));
    }
	
	@PostMapping(value = "/mdfPwd")
    public ResponseMessage resPwd(@RequestBody SysUser sysUser) {
		sysUser.setName("admin");
		SysUser login = sysUserService.getByLoginName(sysUser);
		Md5Encrypt md5 = new Md5Encrypt();
		String lPwd = md5.md5(sysUser.getPwd());
		if(login.getPwd().equals(lPwd)){
			login.setPwd(md5.md5(sysUser.getNewPwd()));
			login.preInsert();
			sysUserService.resPwd(login);
			return Result.success();
		}else{
			return Result.error("原密码不正确");
		}
		
		
        
    }
}
