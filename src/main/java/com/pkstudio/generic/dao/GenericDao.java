package com.pkstudio.generic.dao;

import java.util.List;

public interface GenericDao<T> {
	public void save(T object);

	public T getById(int id);

	public void deleteById(int id);
	
	public List<T> getAll();
}
