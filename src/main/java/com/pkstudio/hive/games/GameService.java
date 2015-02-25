package com.pkstudio.hive.games;

import java.util.List;

import org.springframework.security.access.annotation.Secured;

public interface GameService {
	@Secured("ROLE_PLAYER")
	public List<GameDto> getGamesList();
	public GameDto getGameById(int id);
}
