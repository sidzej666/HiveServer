package com.pkstudio.hive.games;

import javax.inject.Inject;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

@Service
@Transactional
public class GameServiceImpl implements GameService {
	private final GamesDao gamesDao;
	
	@Inject
	public GameServiceImpl(GamesDao gamesDao) {
		this.gamesDao = gamesDao;
	}

	@Override
	public Game getGameById(int id) {
		Game game = gamesDao.getById(id);
		return game;
	}
}
