package com.pkstudio.hive.exceptions.rest;

import java.util.List;

public class ValidationError extends RuntimeException {
	private static final long serialVersionUID = -2636947311219249194L;
	
	private final List<FieldError> fieldErrors;
	
	public ValidationError(List<FieldError> fieldErrors) {
		super("Validation error");
		this.fieldErrors = fieldErrors;
	}

	public List<FieldError> getFieldErrors() {
		return fieldErrors;
	}
}
