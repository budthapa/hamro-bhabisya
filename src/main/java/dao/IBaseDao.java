package dao;

import java.util.List;

public interface IBaseDao {
	<T> List<T> findAll();
	<T> boolean delete(T object);
	<T> boolean save(T object);
	<T> boolean saveOrUpdate(T object);
}
