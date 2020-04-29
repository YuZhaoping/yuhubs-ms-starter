package com.yuhubs.ms.security.auth.web;

import com.yuhubs.ms.web.RestExceptionHandler;
import com.yuhubs.ms.web.annotation.ExceptionStatusMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;

public class AuthWebExceptionHandler extends RestExceptionHandler {

	@ExceptionStatusMapper(AuthenticationException.class)
	public HttpStatus mapAuthenticationException() {
		return HttpStatus.UNAUTHORIZED;
	}

	@ExceptionStatusMapper(AccessDeniedException.class)
	public HttpStatus mapAccessDeniedException() {
		return HttpStatus.FORBIDDEN;
	}

}
