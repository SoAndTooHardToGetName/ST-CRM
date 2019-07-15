package com.situ.situOA.sys.service;

import tools.base.service.IBaseService;

public interface IAccessService<T> extends IBaseService<T> {
	void deletefake(String[] ids, String updateTime, Integer updateBy);
}
