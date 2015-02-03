package com.pkstudio.hive.games;


public class Game {
	private int id;
	private String playerOne;
	private String playerTwo;
	private String name;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getPlayerOne() {
		return playerOne;
	}
	public void setPlayerOne(String playerOne) {
		this.playerOne = playerOne;
	}
	public String getPlayerTwo() {
		return playerTwo;
	}
	public void setPlayerTwo(String playerTwo) {
		this.playerTwo = playerTwo;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}
