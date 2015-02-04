package com.pkstudio.hive.players;

public class PlayerDto {
	private String name;
	private int id;
	
	public PlayerDto(Player player) {
		this.name = player.getName();
		this.id = player.getId();
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}
