package com.situ.situOA.sys.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.situ.situOA.sys.mapper.IUserMapper;
import com.situ.situOA.sys.model.UserModel;
import com.situ.situOA.sys.service.IUserService;

import tools.base.mapper.IBaseMapper;
import tools.base.service.BaseService;

@Service
public class UserServiceImpl<T> extends BaseService<T> implements IUserService<T> {

	@Autowired
	private IUserMapper<T> mapper;

	@Override
	public IBaseMapper<T> getMapper() {
		// TODO Auto-generated method stub
		return mapper;
	}

	@Override
	public UserModel login(UserModel userModel) {
		return mapper.login(userModel);
	}

	@Override
	public void deletefake(String[] id, String updateTime, Integer updateBy) {
		mapper.deletefake(id, updateTime, updateBy);
	}

}
