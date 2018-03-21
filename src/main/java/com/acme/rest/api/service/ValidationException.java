package com.acme.rest.api.service;

public class ValidationException extends RuntimeException {

	private static final long serialVersionUID = 4530251445145691294L;

	public ValidationException(String message) {
		super(message);
	}
}
