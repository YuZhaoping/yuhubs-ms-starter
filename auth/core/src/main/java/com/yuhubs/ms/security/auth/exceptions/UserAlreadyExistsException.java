package com.yuhubs.ms.security.auth.exceptions;

import com.yuhubs.ms.exceptions.RestApiException;

public class UserAlreadyExistsException extends RestApiException {

	private static final long serialVersionUID = -1L;


	public UserAlreadyExistsException() {
		super();
	}

	public UserAlreadyExistsException(String message) {
		super(message);
	}

	public UserAlreadyExistsException(String message, Throwable cause) {
		super(message, cause);
	}

	public UserAlreadyExistsException(Throwable cause) {
		super(cause);
	}


	@Override
	protected int initCode() {
		return 400401;
	}

}
