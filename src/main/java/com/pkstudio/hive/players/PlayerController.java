package com.pkstudio.hive.players;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

import javax.inject.Inject;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/players")
public class PlayerController {
	
	private PlayerService playerService;
	
	@Inject
	public PlayerController(PlayerService playerService) {
		this.playerService = playerService;
	}
	
	@RequestMapping(method = POST)
	public PlayerDto createPlayer(@RequestBody PlayerDto playerDto) {
		playerDto = playerService.createPlayer(playerDto);
		return playerDto;
	}
}
