package com.pkstudio.hive.exceptions;

public class UsernameRequiredException extends HiveException {

	private static final long serialVersionUID = 3475972263214292348L;

	public UsernameRequiredException() {
		super("Username is required!");
	}

}
