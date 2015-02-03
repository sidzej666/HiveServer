package com.pkstudio.hive.games;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.pkstudio.generic.dao.GenericId;
import com.pkstudio.hive.players.Player;

@Entity
@Table(name = "games")
public class Game extends GenericId {
		
	@ManyToOne(optional = false)
	@JoinColumn(name = "player_one_id")
	private Player playerOne;
	
	@ManyToOne(optional = true)
	@JoinColumn(name = "player_two_id")
	private Player playerTwo;
	
	@Column(name = "name", unique = false, nullable = true)
	private String name;
	
	public Player getPlayerOne() {
		return playerOne;
	}
	public void setPlayerOne(Player playerOne) {
		this.playerOne = playerOne;
	}
	public Player getPlayerTwo() {
		return playerTwo;
	}
	public void setPlayerTwo(Player playerTwo) {
		this.playerTwo = playerTwo;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}
