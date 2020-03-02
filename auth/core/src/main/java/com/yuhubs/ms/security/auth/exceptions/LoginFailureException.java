package com.yuhubs.ms.security.auth.exceptions;

import org.springframework.security.core.AuthenticationException;

public class LoginFailureException extends AuthenticationException {

	private static final long serialVersionUID = 3450572612974716392L;


	public LoginFailureException(String message) {
		super(message);
	}

	public LoginFailureException(String message, Throwable cause) {
		super(message, cause);
	}

}
