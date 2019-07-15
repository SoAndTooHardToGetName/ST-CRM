package tools.base.service;

import java.util.List;

import tools.base.mapper.IBaseMapper;

public abstract class BaseService<T> implements IBaseService<T> {

	public abstract IBaseMapper<T> getMapper();

	@Override
	public int insert(T model) {
		return getMapper().insert(model);
	}

	@Override
	public int deleteModel(T id) {
		return getMapper().deleteModel(id);
	}

	@Override
	public void delete(Object[] ids) {
		getMapper().delete(ids);
	}

	@Override
	public int update(T model) {
		return getMapper().update(model);
	}

	@Override
	public int updateActive(T model) {
		return getMapper().updateActive(model);
	}

	@Override
	public T select(T model) {
		return getMapper().select(model);
	}

	@Override
	public List<T> selectAll(T model) {
		return getMapper().selectAll(model);
	}

	@Override
	public List<T> selectModel(T model) {
		return getMapper().selectModel(model);
	}

	@Override
	public int count(T model) {
		return getMapper().count(model);
	}

}
