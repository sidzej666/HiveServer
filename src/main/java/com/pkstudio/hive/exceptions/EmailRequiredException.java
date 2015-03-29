package com.pkstudio.hive.exceptions;

public class EmailRequiredException extends HiveException {
	
	private static final long serialVersionUID = -745172687741556549L;

	public EmailRequiredException() {
		super("Email is required");
	}

}
