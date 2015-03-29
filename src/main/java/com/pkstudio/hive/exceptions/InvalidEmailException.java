package com.pkstudio.hive.exceptions;

public class InvalidEmailException extends HiveException {
	
	private static final long serialVersionUID = -8857101504097890398L;

	public InvalidEmailException(String email) {
		super(String.format("Provided email '%s' is invalid!", email));
	}

}
