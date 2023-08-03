package org.in.com.exception;

public class CustomException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	private String message;

	public CustomException() {

	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public CustomException(String message) {
		this.message = message;
	}
}
