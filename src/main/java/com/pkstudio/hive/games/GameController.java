package com.pkstudio.hive.games;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/games")
public class GameController {

	private final GameService gameService;
	
	@Inject
	public GameController(GameService gameService) {
		this.gameService = gameService;
	}
	
	@RequestMapping(method = GET)
	public List<Game> getGames() {
		List<Game> games = new ArrayList<Game>();
		games.add(new Game());
		games.add(new Game());
		return games;
	}
	
	@RequestMapping(value = "/{id}", method = GET)
	public Game getGame(@PathVariable int id) {
		return gameService.getGameById(id);
	}
}
