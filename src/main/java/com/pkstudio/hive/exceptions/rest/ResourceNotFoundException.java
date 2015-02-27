package com.pkstudio.hive.exceptions.rest;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

public class ResourceNotFoundException extends RuntimeException {

	private static final long serialVersionUID = -3673531992515699235L;
	
	public ResourceNotFoundException(String message) {
		super(message);
	}
}
