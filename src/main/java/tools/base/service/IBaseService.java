package tools.base.service;

import java.util.List;

public interface IBaseService<T> {

	int insert(T model);// 插入单行数据

	int deleteModel(T id);// 删除单行

	void delete(Object[] ids);// 删除多行

	int update(T model);// 更新基本信息

	int updateActive(T model);// 状态激活

	T select(T model);// 查单条数据

	List<T> selectAll(T model);// 按条件查全部

	List<T> selectModel(T model);// 按条件查，分页

	int count(T model);// 查总数
}
