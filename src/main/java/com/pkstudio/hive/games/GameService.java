package com.pkstudio.hive.games;

import java.util.List;

public interface GameService {
	public List<GameDto> getGamesList();
	public GameDto getGameById(int id);
}
