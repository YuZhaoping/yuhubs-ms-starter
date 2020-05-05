package com.yuhubs.ms.security.auth.exceptions;

import com.yuhubs.ms.exceptions.RestApiException;

public class UsernameAlreadyExistsException extends RestApiException {

	private static final long serialVersionUID = -1L;


	public UsernameAlreadyExistsException() {
		super();
	}

	public UsernameAlreadyExistsException(String message) {
		super(message);
	}

	public UsernameAlreadyExistsException(String message, Throwable cause) {
		super(message, cause);
	}

	public UsernameAlreadyExistsException(Throwable cause) {
		super(cause);
	}


	@Override
	protected int initCode() {
		return 400401;
	}

}
