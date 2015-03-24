package com.pkstudio.hive.users;

import javax.inject.Inject;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.pkstudio.generic.dao.GenericDaoImpl;

@Repository
public class UsersDaoImpl extends GenericDaoImpl<User> implements UsersDao {

	@Inject
	public UsersDaoImpl(SessionFactory sessionFactory) {
		super(User.class, sessionFactory);
	}

	@Override
	public User findByUsername(String username) {
		Session currentSession = getSessionFactory().getCurrentSession();
		return (User) currentSession.createCriteria(User.class).add(Restrictions.eq("username", username)).uniqueResult();
	}

}
