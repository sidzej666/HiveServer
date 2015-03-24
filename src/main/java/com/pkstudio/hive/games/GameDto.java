package com.pkstudio.hive.games;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.ALWAYS;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(value = ALWAYS)
public class GameDto {
	private Integer id;
	private String name;
	private boolean isPrivate;
	//private UserDto playerOne;
	//private UserDto playerTwo;
	
	public GameDto() {
		
	}
	
	public GameDto(Game game) {
		this.setId(game.getId());
		this.name = game.getName();
		this.isPrivate = game.getPassword() == null ? false : true;
		//this.setPlayerOne(new PlayerDto(game.getPlayerOne()));
		//if (game.getPlayerTwo() != null) {
		//	this.setPlayerTwo(new PlayerDto(game.getPlayerTwo()));
		//}
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
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
}
