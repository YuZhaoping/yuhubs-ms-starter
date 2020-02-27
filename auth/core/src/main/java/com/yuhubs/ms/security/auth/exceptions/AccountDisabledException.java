package com.yuhubs.ms.security.auth.exceptions;

public class AccountDisabledException extends AccountStatusCodedException {

	private static final long serialVersionUID = -346352114578198564L;


	public AccountDisabledException(String message) {
		super(message);
	}

	public AccountDisabledException(String message, Throwable cause) {
		super(message, cause);
	}


	@Override
	protected int initCode() {
		return 401004;
	}

}
