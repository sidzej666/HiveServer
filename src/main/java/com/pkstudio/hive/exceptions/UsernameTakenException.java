package com.pkstudio.hive.exceptions;

public class UsernameTakenException extends HiveException {

	private static final long serialVersionUID = -6933977073493946648L;

	public UsernameTakenException() {
		super("Username already taken!");
	}

}
