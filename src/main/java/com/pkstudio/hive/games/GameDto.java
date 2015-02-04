package com.pkstudio.hive.games;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.ALWAYS;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.pkstudio.hive.players.PlayerDto;

@JsonInclude(value = ALWAYS)
public class GameDto {
	private int id;
	private String name;
	private boolean isPrivate;
	private PlayerDto playerOne;
	private PlayerDto playerTwo;
	
	public GameDto(Game game) {
		this.setId(game.getId());
		this.name = game.getName();
		this.isPrivate = game.getPassword() == null ? false : true;
		this.setPlayerOne(new PlayerDto(game.getPlayerOne()));
		if (game.getPlayerTwo() != null) {
			this.setPlayerTwo(new PlayerDto(game.getPlayerTwo()));
		}
	}
	
	public static List<GameDto> fromGamesList(List<Game> games) {
		List<GameDto> result = new ArrayList<GameDto>();
		for (Game game: games) {
			result.add(new GameDto(game));
		}
		return result;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public boolean isPrivate() {
		return isPrivate;
	}
	public void setPrivate(boolean isPrivate) {
		this.isPrivate = isPrivate;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}

	public PlayerDto getPlayerOne() {
		return playerOne;
	}

	public void setPlayerOne(PlayerDto playerOne) {
		this.playerOne = playerOne;
	}

	public PlayerDto getPlayerTwo() {
		return playerTwo;
	}

	public void setPlayerTwo(PlayerDto playerTwo) {
		this.playerTwo = playerTwo;
	}
}
