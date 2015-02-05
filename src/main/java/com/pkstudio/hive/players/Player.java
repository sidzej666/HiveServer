package com.pkstudio.hive.players;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.pkstudio.generic.dao.GenericId;

@Entity
@Table(name = "players")
public class Player extends GenericId {
	@Column(name = "name", nullable = false, unique = true)
	private String name;
	@Column(name = "email", nullable = false, unique = true)
	private String email;
	@Column(name = "password_hash", nullable = false)
	private String passwordHash;
	@Column(name = "hash_seed", nullable = false)
	private String hashSeed;
	
	public Player(PlayerDto playerDto) {
		this.name = playerDto.getName();
		this.email = playerDto.getEmail();
		this.passwordHash = "";
		this.hashSeed = "";
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPasswordHash() {
		return passwordHash;
	}
	public void setPassword_hash(String passwordHash) {
		this.passwordHash = passwordHash;
	}
	public String getHashSeed() {
		return hashSeed;
	}
	public void setHash_seed(String hashSeed) {
		this.hashSeed = hashSeed;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
}
