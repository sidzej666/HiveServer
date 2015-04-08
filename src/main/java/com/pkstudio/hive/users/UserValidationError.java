package com.pkstudio.hive.users;

import static com.pkstudio.hive.users.UserValidationError.Fields.*;

public enum UserValidationError {
	
	USERNAME_EMPTY(USERNAME_FIELD, "Username can't be empty"),
	USERNAME_TO_LONG(USERNAME_FIELD, "Username is to long, can contain maximum %s characters"),
	USERNAME_TAKEN(USERNAME_FIELD, "Username '%s' is already taken, choose another one"),
	
	PASSWORD_EMPTY(PASSWORD_FIELD, "Password can't be empty"),
	PASSWORD_TO_SHORT(PASSWORD_FIELD, "Password is to short, must contain minimum %s characters"),
	
	EMAIL_EMPTY(EMAIL_FIELD, "Email can't be empty"),
	EMAIL_INVALID(EMAIL_FIELD, "Provided email is not a valid email"),
	EMAIL_TO_LONG(EMAIL_FIELD, "Email is to long, can contain maximum %s characters")
	;
	
	private final String field;
	private final String message;
	
	UserValidationError(String field, String message) {
		this.field = field;
		this.message = message;
	}
	
	public String getField() {
		return field;
	}

	public String getMessage() {
		return message;
	}

	class Fields {
		static final String PASSWORD_FIELD = "password";
		static final String USERNAME_FIELD = "username";
		static final String EMAIL_FIELD = "email";
	}
}