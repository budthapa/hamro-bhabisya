package dao;

import java.util.List;

public interface IBaseDao {
	<T> List<T> findAll();
	<T> boolean delete(T object);
	<T> int save(T object);
	<T> boolean saveOrUpdate(T object);
}
