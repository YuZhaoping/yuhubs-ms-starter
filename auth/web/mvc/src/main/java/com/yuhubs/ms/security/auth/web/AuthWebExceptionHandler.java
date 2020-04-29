package com.yuhubs.ms.security.auth.web;

import com.yuhubs.ms.web.RestExceptionHandler;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

public class AuthWebExceptionHandler extends RestExceptionHandler {

	@ExceptionHandler(AuthenticationException.class)
	public ResponseEntity<Object> handleAuthenticationException(
			final AuthenticationException ex, WebRequest request) {
		return handleExceptionInternal(ex, UNAUTHORIZED, request);
	}

	@ExceptionHandler(AccessDeniedException.class)
	public ResponseEntity<Object> handleAccessDeniedException(
			final AccessDeniedException ex, WebRequest request) {
		return handleExceptionInternal(ex, FORBIDDEN, request);
	}

}
