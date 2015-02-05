package com.pkstudio.generic.dao;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class GenericId {
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", nullable = false, unique = true)
	private Integer id;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
}
