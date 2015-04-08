package com.pkstudio.hive.exceptions;

public interface ValidationError {
	public String getField();
	public String getMessageKey();
	public String getCode();
}
