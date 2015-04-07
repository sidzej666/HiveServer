package com.pkstudio.hive.exceptions.rest;

import java.util.List;

public class ValidationException extends RuntimeException {
	private static final long serialVersionUID = -2636947311219249194L;
	
	private final List<FieldError> fieldErrors;
	
	public ValidationException(List<FieldError> fieldErrors) {
		super("Validation error");
		this.fieldErrors = fieldErrors;
	}

	public List<FieldError> getFieldErrors() {
		return fieldErrors;
	}
	
	@Override
	public String toString() {
		return "ValidationException [fieldErrors=" + fieldErrors + "]";
	}
}
