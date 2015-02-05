package com.pkstudio.hive.exceptions;

public class EmailIsNullException extends HiveException {

	private static final long serialVersionUID = -3070060196507310054L;

	public EmailIsNullException() {
		super("Email can't be null!");
	}

}
