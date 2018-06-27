package com.portal.system.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.portal.common.api.Paging;
import com.portal.system.entity.SysUser;
import com.portal.system.mapper.SysUserMapper;
import com.portal.system.service.SysUserService;

@Service
public class SysUserServiceImpl implements SysUserService {

	@Autowired
	private SysUserMapper sysUserMapper;

	@Override
	public SysUser getByLoginName(SysUser sysUser) {
		return sysUserMapper.getByLoginName(sysUser);
	}

	@Override
	@Transactional
	public void resPwd(SysUser sysUser) {
		sysUserMapper.resPwd(sysUser);
	}

	@Override
	public PageInfo<SysUser> getList(Paging page, SysUser user) {
		 // 执行分页查询
        PageHelper.startPage(page.getPageNum(), page.getPageSize(), page.getOrderBy());
        List<SysUser> list = sysUserMapper.getList(user);
        return new PageInfo<>(list);
	}
	
}
