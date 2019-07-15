package com.situ.situOA.sys.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.situ.situOA.sys.mapper.IMenuMapper;
import com.situ.situOA.sys.service.IMenuService;

import tools.base.mapper.IBaseMapper;
import tools.base.service.BaseService;

@Service
public class MenuServiceImpl<T> extends BaseService<T> implements IMenuService<T> {

	@Autowired
	private IMenuMapper<T> mapper;

	@Override
	public IBaseMapper<T> getMapper() {
		// TODO Auto-generated method stub
		return mapper;
	}

	@Override
	public void deletefake(String[] ids, String updateTime, Integer updateBy) {
		// TODO Auto-generated method stub
		mapper.deletefake(ids, updateTime, updateBy);
	}

}
