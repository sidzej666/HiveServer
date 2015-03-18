package com.pkstudio.hive.games;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.pkstudio.generic.dao.GenericId;
import com.pkstudio.hive.security.User;

@Entity
@Table(name = "games")
public class Game extends GenericId {
		
	@ManyToOne(optional = false)
	@JoinColumn(name = "player_one_id")
	private User playerOne;
	
	@ManyToOne(optional = true)
	@JoinColumn(name = "player_two_id")
	private User playerTwo;
	
	@Column(name = "name", unique = false, nullable = true)
	private String name;
	
	@Column(name = "password", nullable = true)
	private String password;
	
	public User getPlayerOne() {
		return playerOne;
	}
	public void setPlayerOne(User playerOne) {
		this.playerOne = playerOne;
	}
	public User getPlayerTwo() {
		return playerTwo;
	}
	public void setPlayerTwo(User playerTwo) {
		this.playerTwo = playerTwo;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
}
