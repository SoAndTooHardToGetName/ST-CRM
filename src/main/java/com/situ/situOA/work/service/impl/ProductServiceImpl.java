package com.situ.situOA.work.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.situ.situOA.work.mapper.IProductMapper;
import com.situ.situOA.work.service.IProductInfoService;

import tools.base.mapper.IBaseMapper;
import tools.base.service.BaseService;

@Service
public class ProductServiceImpl<T> extends BaseService<T> implements IProductInfoService<T> {
	@Autowired
	private IProductMapper<T> mapper;

	@Override
	public IBaseMapper<T> getMapper() {
		return mapper;
	}

}
