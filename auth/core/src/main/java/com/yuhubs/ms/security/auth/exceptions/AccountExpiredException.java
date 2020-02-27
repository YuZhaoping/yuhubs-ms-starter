package com.yuhubs.ms.security.auth.exceptions;

public class AccountExpiredException extends AccountStatusCodedException {

	private static final long serialVersionUID = -3854609057123637032L;


	public AccountExpiredException(String message) {
		super(message);
	}

	public AccountExpiredException(String message, Throwable cause) {
		super(message, cause);
	}


	@Override
	protected int initCode() {
		return 401001;
	}

}
