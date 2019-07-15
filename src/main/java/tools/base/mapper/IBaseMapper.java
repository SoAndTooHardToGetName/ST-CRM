package tools.base.mapper;

import java.util.List;

public interface IBaseMapper<T> {

	int insert(T model);// 添加

	int deleteModel(T id);// 单删除

	void delete(Object... ids);// 多删除

	int update(T model);// 更改

	int updateActive(T model);// 更改状态

	T select(T model);// 单查

	List<T> selectAll(T model);// 多查

	List<T> selectModel(T model);// 分页多查

	int count(T model);// 总数
}
