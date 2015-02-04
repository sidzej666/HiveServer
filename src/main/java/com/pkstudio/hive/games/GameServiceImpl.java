package com.pkstudio.hive.games;

import java.util.List;

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
	public GameDto getGameById(int id) {
		Game game = gamesDao.getById(id);
		return new GameDto(game);
	}

	@Override
	public List<GameDto> getGamesList() {
		List<Game> games = gamesDao.getAll();
		return GameDto.fromGamesList(games);
	}
}
