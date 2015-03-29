package com.pkstudio.hive.exceptions;

public class PasswordToShortException extends HiveException {
	
	private static final long serialVersionUID = 114423531012151971L;

	public PasswordToShortException() {
		super("Password need to be at least 5 characters long!");
	}

}
