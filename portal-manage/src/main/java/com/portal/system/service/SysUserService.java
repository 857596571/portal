package com.portal.system.service;

import java.util.List;

import com.github.pagehelper.PageInfo;
import com.portal.common.api.Paging;
import com.portal.system.entity.SysUser;

public interface SysUserService {

	SysUser getByLoginName(SysUser sysUser);

	void resPwd(SysUser sysUser);

	PageInfo<SysUser> getList(Paging page, SysUser user);

}
