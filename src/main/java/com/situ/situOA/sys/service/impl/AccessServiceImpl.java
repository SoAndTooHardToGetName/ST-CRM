package com.situ.situOA.sys.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.situ.situOA.sys.mapper.IAccessMapper;
import com.situ.situOA.sys.service.IAccessService;

import tools.base.mapper.IBaseMapper;
import tools.base.service.BaseService;

@Service
public class AccessServiceImpl<T> extends BaseService<T> implements IAccessService<T> {
	@Autowired
	private IAccessMapper<T> mapper;

	@Override
	public IBaseMapper<T> getMapper() {
		return mapper;
	}

	@Override
	public void deletefake(String[] ids, String updateTime, Integer updateBy) {
		// TODO Auto-generated method stub
		mapper.deletefake(ids, updateTime, updateBy);
	}

}
