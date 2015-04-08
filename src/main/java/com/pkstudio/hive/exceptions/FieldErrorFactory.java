package com.pkstudio.hive.exceptions;

import javax.inject.Inject;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import com.pkstudio.hive.exceptions.rest.FieldError;

@Component
public class FieldErrorFactory {

	private static MessageSource messageSource;
	
	@Inject
	public FieldErrorFactory(MessageSource messageSource) {
		this.messageSource = messageSource;
	}
	
	public static FieldError newFieldError(ValidationError validationError, Object... messageParams) {
		String code = validationError.getCode();
		String field = validationError.getField();
		String message = messageSource.getMessage(validationError.getMessageKey(), messageParams, null);
		return new FieldError(field, message, code);
	}
}
