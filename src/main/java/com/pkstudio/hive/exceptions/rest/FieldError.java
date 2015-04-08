package com.pkstudio.hive.exceptions.rest;


public class FieldError {
	private final String fieldName;
	private final String message;
	private final String code;
	
	public FieldError(String fieldName, String message, String code) {
		this.fieldName = fieldName;
		this.message = message;
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public String getFieldName() {
		return fieldName;
	}

	public String getCode() {
		return code;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((code == null) ? 0 : code.hashCode());
		result = prime * result
				+ ((fieldName == null) ? 0 : fieldName.hashCode());
		result = prime * result + ((message == null) ? 0 : message.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		FieldError other = (FieldError) obj;
		if (code == null) {
			if (other.code != null)
				return false;
		} else if (!code.equals(other.code))
			return false;
		if (fieldName == null) {
			if (other.fieldName != null)
				return false;
		} else if (!fieldName.equals(other.fieldName))
			return false;
		if (message == null) {
			if (other.message != null)
				return false;
		} else if (!message.equals(other.message))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "FieldError [fieldName=" + fieldName + ", message=" + message
				+ ", code=" + code + "]";
	}
}
