package com.acme.rest.api.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.acme.rest.api.service.ValidationException;

@ControllerAdvice
public class ExceptionControllerAdvice {

	private Log log = LogFactory.getLog(this.getClass());

	@ExceptionHandler(ValidationException.class)
	public ResponseEntity<?> exceptionHandler(ValidationException ex) {
		String message = ex.getMessage();
		log.error(message);
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
	}

	@ExceptionHandler(MissingServletRequestParameterException.class)
	public ResponseEntity<?> exceptionHandler(MissingServletRequestParameterException ex) {
		String message = ex.getMessage();
		log.error(message);
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<?> exceptionHandler(Exception ex) {
		log.error(null, ex);
		String message = ex.toString();
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(message);
	}

}
