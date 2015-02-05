package com.pkstudio.hive.exceptions;

public class PlayerNameIsNullException extends HiveException {

	private static final long serialVersionUID = 3475972263214292348L;

	public PlayerNameIsNullException() {
		super("Player name can't be null!");
	}

}
