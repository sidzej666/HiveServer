package com.pkstudio.hive.exceptions;

public class PasswordRequiredException extends HiveException {
	
	private static final long serialVersionUID = 3185506020593899748L;

	public PasswordRequiredException() {
		super("Password is required!");
	}

}
