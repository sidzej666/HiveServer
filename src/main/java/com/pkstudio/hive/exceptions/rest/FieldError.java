package com.pkstudio.hive.exceptions.rest;

public class FieldError {
	private final String fieldName;
	private final String message;
	
	public FieldError(String fieldName, String message) {
		this.fieldName = fieldName;
		this.message = message;				
	}

	public String getMessage() {
		return message;
	}

	public String getFieldName() {
		return fieldName;
	}
}
