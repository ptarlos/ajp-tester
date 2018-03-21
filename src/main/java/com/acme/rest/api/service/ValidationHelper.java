package com.acme.rest.api.service;

public class ValidationHelper {
	public static void assertRequiredParam(Object field, String fieldName) throws ValidationException {
		if (field == null) {
			throw new ValidationException(fieldName + " param is required");
		}
	}

}
