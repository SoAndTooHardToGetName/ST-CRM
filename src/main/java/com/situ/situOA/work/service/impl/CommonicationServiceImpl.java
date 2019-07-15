package com.situ.situOA.work.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.situ.situOA.work.mapper.ICommunicationMapper;
import com.situ.situOA.work.service.ICommunicationService;

import tools.base.mapper.IBaseMapper;
import tools.base.service.BaseService;

@Service
public class CommonicationServiceImpl<T> extends BaseService<T> implements ICommunicationService<T> {

	@Autowired
	private ICommunicationMapper<T> mapper;

	@Override
	public IBaseMapper<T> getMapper() {
		return mapper;
	}

}
