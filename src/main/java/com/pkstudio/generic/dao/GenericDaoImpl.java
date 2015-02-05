package com.pkstudio.generic.dao;

import static javax.transaction.Transactional.TxType.REQUIRED;

import java.util.List;

import javax.inject.Inject;
import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;

@Transactional(value = REQUIRED)
public class GenericDaoImpl<T> implements GenericDao<T> {
	private SessionFactory sessionFactory;
	private final Class<T> typeParameterClass;
	
	public GenericDaoImpl(Class<T> typeParameterClass, SessionFactory sessionFactory) {
		this.typeParameterClass = typeParameterClass;
		this.sessionFactory = sessionFactory;
	}
	
	@Override
	public void save(T object) {
		Session currentSession = sessionFactory.getCurrentSession();
		currentSession.save(object);
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public T getById(int id) {
		Session currentSession = sessionFactory.getCurrentSession();
		return (T) currentSession.get(typeParameterClass.getCanonicalName(), id);
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public void deleteById(int id) {
		Session currentSession = sessionFactory.getCurrentSession();
		T result = (T) currentSession.createCriteria(typeParameterClass.getCanonicalName())
									 .add(Restrictions.idEq(id))
									 .uniqueResult();

		if (result != null) {
			currentSession.delete(result);
		}
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<T> getAll() {
		Session currentSession = sessionFactory.getCurrentSession();
		List<T> result = currentSession.createCriteria(typeParameterClass.getCanonicalName()).list();
		return result;
	}
}
