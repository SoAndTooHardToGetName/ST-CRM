package com.situ.situOA.work.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.situ.situOA.work.mapper.ICustomerInfoMapper;
import com.situ.situOA.work.service.ICustomerInfoService;

import tools.base.mapper.IBaseMapper;
import tools.base.service.BaseService;

@Service
public class CustomerInfoServiceImpl<T> extends BaseService<T> implements ICustomerInfoService<T> {
	@Autowired
	private ICustomerInfoMapper<T> mapper;

	@Override
	public IBaseMapper<T> getMapper() {
		// TODO Auto-generated method stub
		return mapper;
	}

}
