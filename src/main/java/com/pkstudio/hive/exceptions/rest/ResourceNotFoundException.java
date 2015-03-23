package com.pkstudio.hive.exceptions.rest;


public class ResourceNotFoundException extends RuntimeException {

	private static final long serialVersionUID = -3673531992515699235L;
	
	public ResourceNotFoundException(String message) {
		super(message);
	}
}
