package com.pkstudio.hive.players;

import javax.persistence.Table;

import com.pkstudio.generic.dao.GenericId;

@Table(name = "players")
public class Player extends GenericId {
	private String name;
	private String password_hash;
	private String hash_seed;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPassword_hash() {
		return password_hash;
	}
	public void setPassword_hash(String password_hash) {
		this.password_hash = password_hash;
	}
	public String getHash_seed() {
		return hash_seed;
	}
	public void setHash_seed(String hash_seed) {
		this.hash_seed = hash_seed;
	}
}
