package com.pkstudio.hive.games;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

import java.util.List;

import javax.inject.Inject;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pkstudio.hive.exceptions.rest.ResourceNotFoundException;

@RestController
@RequestMapping(value = "/rest/games")
public class GameController {

	private final GameService gameService;
	
	@Inject
	public GameController(GameService gameService) {
		this.gameService = gameService;
	}
	
	@RequestMapping(method = GET)
	public List<GameDto> getGames() {
		return gameService.getGamesList();
	}
	
	@RequestMapping(value = "/{id}", method = GET)
	public GameDto getGame(@PathVariable int id) {
		GameDto gameDto = gameService.getGameById(id);
		if (gameDto == null) {
			throw new ResourceNotFoundException("Unable to find game with id '" + id + "'");
		}
		return gameDto;
	}
}
