package com.pkstudio.generic.dao;

public interface GenericDao<T> {
	public void save(T object);

	public T getById(int id);

	public void deleteById(int id);
}
