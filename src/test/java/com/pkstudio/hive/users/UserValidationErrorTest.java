package com.pkstudio.hive.users;

import static org.assertj.core.api.Assertions.assertThat;

import javax.inject.Inject;

import org.junit.Test;

import com.pkstudio.commons.TestBase;
import com.pkstudio.hive.exceptions.FieldErrorFactory;
import com.pkstudio.hive.exceptions.rest.FieldError;


public class UserValidationErrorTest extends TestBase {
	
	@Inject
	private FieldErrorFactory fieldErrorFactory;
	
	@Test
	public void verifyMessageKeysForAllErrors() {
		for (UserValidationError userValidationError : UserValidationError.values()) {
			FieldError fieldError = fieldErrorFactory.newFieldError(userValidationError, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
			verifyFieldError(fieldError, userValidationError);
		}
	}

	private void verifyFieldError(FieldError fieldError,
			UserValidationError userValidationError) {
		assertThat(fieldError.getCode()).isEqualTo(userValidationError.toString());
		assertThat(fieldError.getFieldName()).isEqualTo(userValidationError.getField());
		assertThat(fieldError.getMessage()).isNotEmpty();
	}
}
