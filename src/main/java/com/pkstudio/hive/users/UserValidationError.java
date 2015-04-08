package com.pkstudio.hive.users;

import static com.pkstudio.hive.users.UserValidationError.Fields.*;

import com.pkstudio.hive.exceptions.ValidationError;

public enum UserValidationError implements ValidationError {
	
	USERNAME_EMPTY(USERNAME_FIELD, "validation.createUser.username.empty"),
	USERNAME_TO_LONG(USERNAME_FIELD, "validation.createUser.username.toLong"),
	USERNAME_TAKEN(USERNAME_FIELD, "validation.createUser.username.taken"),
	
	PASSWORD_EMPTY(PASSWORD_FIELD, "validation.createUser.password.empty"),
	PASSWORD_TO_SHORT(PASSWORD_FIELD, "validation.createUser.password.toShort"),
	PASSWORD_TO_LONG(PASSWORD_FIELD, "validation.createUser.password.toLong"),
	
	EMAIL_EMPTY(EMAIL_FIELD, "validation.createUser.email.empty"),
	EMAIL_INVALID(EMAIL_FIELD, "validation.createUser.email.invalid"),
	EMAIL_TO_LONG(EMAIL_FIELD, "validation.createUser.email.toLong"),
	EMAIL_TAKEN(EMAIL_FIELD, "validation.createUser.email.taken")
	;
	
	private final String field;
	private final String messageKey;
	
	UserValidationError(String field, String messageKey) {
		this.field = field;
		this.messageKey = messageKey;
	}
	
	@Override
	public String getField() {
		return field;
	}

	@Override
	public String getMessageKey() {
		return messageKey;
	}
	
	@Override
	public String getCode() {
		return this.toString();
	}

	public class Fields {
		static final String PASSWORD_FIELD = "password";
		static final String USERNAME_FIELD = "username";
		static final String EMAIL_FIELD = "email";
	}
}