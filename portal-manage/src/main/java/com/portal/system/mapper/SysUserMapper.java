package com.portal.system.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.portal.system.entity.SysUser;

@Mapper
public interface SysUserMapper {

	public SysUser getByLoginName(SysUser sysUser);

	public void resPwd(SysUser sysUser);

	public List<SysUser> getList(SysUser user);

}
