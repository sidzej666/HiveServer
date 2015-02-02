package com.pkstudio.hive.games;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/games")
public class GameController {

	@RequestMapping(method = GET)
	public List<Game> getGames() {
		List<Game> games = new ArrayList<Game>();
		games.add(new Game());
		games.add(new Game());
		return games;
	}
}
