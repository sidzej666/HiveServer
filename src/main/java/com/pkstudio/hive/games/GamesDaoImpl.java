package com.pkstudio.hive.games;

import javax.inject.Inject;

import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import com.pkstudio.generic.dao.GenericDaoImpl;

@Repository
public class GamesDaoImpl extends GenericDaoImpl<Game> implements GamesDao {
	
	@Inject
	public GamesDaoImpl(SessionFactory sessionFactory) {
		super(Game.class, sessionFactory);
	}
}
