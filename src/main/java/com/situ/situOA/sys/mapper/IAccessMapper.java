package com.situ.situOA.sys.mapper;

import tools.base.mapper.IBaseMapper;

public interface IAccessMapper<T> extends IBaseMapper<T> {
	void deletefake(String[] ids, String updateTime, Integer updateBy);
}
