package com.pkstudio.generic.dao;

import static javax.transaction.Transactional.TxType.REQUIRES_NEW;

import javax.inject.Inject;
import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;

@Transactional(value = REQUIRES_NEW)
public class GenericDaoImpl<T> implements GenericDao<T> {
	private SessionFactory sessionFactory;
	private final Class<T> typeParameterClass;
	
	public GenericDaoImpl(Class<T> typeParameterClass) {
		this.typeParameterClass = typeParameterClass;
	}
	
	@Inject
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	public void save(T object) {
		Session currentSession = sessionFactory.getCurrentSession();
		currentSession.save(object);
	}
	
	@SuppressWarnings("unchecked")
	public T getById(int id) {
		Session currentSession = sessionFactory.getCurrentSession();
		return (T) currentSession.get(typeParameterClass.getClass(), id);
	}
	
	@SuppressWarnings("unchecked")
	public void deleteById(int id) {
		Session currentSession = sessionFactory.getCurrentSession();
		T result = (T) currentSession.createCriteria(typeParameterClass.getClass())
									 .add(Restrictions.idEq(id))
									 .uniqueResult();

		if (result != null) {
			currentSession.delete(result);
		}
	}
}
