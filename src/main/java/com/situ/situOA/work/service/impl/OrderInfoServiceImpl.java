package com.situ.situOA.work.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.situ.situOA.work.mapper.IOrderInfoMapper;
import com.situ.situOA.work.service.IOrderInfoService;

import tools.base.mapper.IBaseMapper;
import tools.base.service.BaseService;

@Service
public class OrderInfoServiceImpl<T> extends BaseService<T> implements IOrderInfoService<T> {
	@Autowired
	private IOrderInfoMapper<T> mapper;

	@Override
	public IBaseMapper<T> getMapper() {
		// TODO Auto-generated method stub
		return mapper;
	}

}
