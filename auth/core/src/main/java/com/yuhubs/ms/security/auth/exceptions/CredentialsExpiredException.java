package com.yuhubs.ms.security.auth.exceptions;

public class CredentialsExpiredException extends AccountStatusCodedException {

	private static final long serialVersionUID = 5441974028981201377L;


	public CredentialsExpiredException(String message) {
		super(message);
	}

	public CredentialsExpiredException(String message, Throwable cause) {
		super(message, cause);
	}


	@Override
	protected int initCode() {
		return 401003;
	}

}
