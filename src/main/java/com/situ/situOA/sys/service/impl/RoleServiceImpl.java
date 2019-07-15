package com.situ.situOA.sys.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.situ.situOA.sys.mapper.IRoleMapper;
import com.situ.situOA.sys.service.IRoleService;

import tools.base.mapper.IBaseMapper;
import tools.base.service.BaseService;

@Service
public class RoleServiceImpl<T> extends BaseService<T> implements IRoleService<T> {

	@Autowired
	private IRoleMapper<T> mapper;

	@Override
	public IBaseMapper<T> getMapper() {
		// TODO Auto-generated method stub
		return mapper;
	}

}
