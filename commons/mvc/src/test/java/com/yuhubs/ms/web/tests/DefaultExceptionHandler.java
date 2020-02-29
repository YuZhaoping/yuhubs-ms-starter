package com.yuhubs.ms.web.tests;

import com.yuhubs.ms.web.RestExceptionHandler;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import static org.springframework.http.HttpStatus.*;

@RestControllerAdvice
public class DefaultExceptionHandler extends RestExceptionHandler {

	@ExceptionHandler(Exception.class)
	public ResponseEntity<Object> handleBaseException(
			final Exception ex, WebRequest request) {
		return handleExceptionInternal(ex, INTERNAL_SERVER_ERROR, request);
	}

	@ExceptionHandler(UnsupportedOperationException.class)
	public ResponseEntity<Object> handleUnsupportedOperationException(
			final UnsupportedOperationException ex, WebRequest request) {
		return handleExceptionInternal(ex, NOT_IMPLEMENTED, request);
	}

}
