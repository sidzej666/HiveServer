package com.pkstudio.hive.games;

import javax.inject.Inject;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

@Repository
public class GamesDaoImpl implements GamesDao {
	
	private SessionFactory sessionFactory;
	
	@Inject
	public GamesDaoImpl(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@SuppressWarnings("unchecked")
	public Game getById(int id) {
		Session currentSession = sessionFactory.getCurrentSession();
		return (Game) currentSession.get(Game.class, id);
	}
}
