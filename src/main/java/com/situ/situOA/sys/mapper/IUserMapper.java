package com.situ.situOA.sys.mapper;

import com.situ.situOA.sys.model.UserModel;

import tools.base.mapper.IBaseMapper;

public interface IUserMapper<T> extends IBaseMapper<T> {

	UserModel login(UserModel userModel);

	void deletefake(String[] id,String updateTime,Integer updateBy);
}
