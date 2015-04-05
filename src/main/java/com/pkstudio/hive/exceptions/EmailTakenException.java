package com.pkstudio.hive.exceptions;

import com.pkstudio.hive.exceptions.rest.RestError;

public class EmailTakenException extends HiveException {
	
	public EmailTakenException(String email) {
		super(String.format("Email '%s' already taken!", email));
	}
}
