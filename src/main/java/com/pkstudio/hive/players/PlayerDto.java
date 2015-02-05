package com.pkstudio.hive.players;

import com.pkstudio.hive.exceptions.EmailIsNullException;
import com.pkstudio.hive.exceptions.PlayerNameIsNullException;

public class PlayerDto {
	private String name;
	private Integer id;
	private String email;
	
	public PlayerDto() {
		
	}
	
	public PlayerDto(Player player) {
		this.name = player.getName();
		this.id = player.getId();
		this.setEmail(player.getEmail());
	}
	
	public void validate() {
		if (name == null) {
			throw new PlayerNameIsNullException();
		}
		if (email == null) {
			throw new EmailIsNullException();
		}
	}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
}
