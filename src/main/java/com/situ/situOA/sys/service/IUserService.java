package com.situ.situOA.sys.service;

import com.situ.situOA.sys.model.UserModel;

import tools.base.service.IBaseService;

public interface IUserService<T> extends IBaseService<T> {

	UserModel login(UserModel userModel);

	void deletefake(String[] id, String updateTime, Integer updateBy);
}
