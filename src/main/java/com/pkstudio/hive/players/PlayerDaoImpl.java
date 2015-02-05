package com.pkstudio.hive.players;

import javax.inject.Inject;

import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import com.pkstudio.generic.dao.GenericDaoImpl;

@Repository
public class PlayerDaoImpl extends GenericDaoImpl<Player> implements PlayerDao {

	@Inject
	public PlayerDaoImpl(SessionFactory sessionFactory) {
		super(Player.class, sessionFactory);
	}
}
