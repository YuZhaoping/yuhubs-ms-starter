package com.yuhubs.ms.security.auth.exceptions;

public class EmailNotVerifiedException extends AccountStatusCodedException {

	private static final long serialVersionUID = -8410451105011098639L;


	public EmailNotVerifiedException(String message) {
		super(message);
	}

	public EmailNotVerifiedException(String message, Throwable cause) {
		super(message, cause);
	}


	@Override
	protected int initCode() {
		return 401005;
	}

}
