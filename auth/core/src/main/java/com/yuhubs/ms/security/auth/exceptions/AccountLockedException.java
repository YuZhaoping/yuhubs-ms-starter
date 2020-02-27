package com.yuhubs.ms.security.auth.exceptions;

public class AccountLockedException extends AccountStatusCodedException {

	private static final long serialVersionUID = -3410655748593422144L;


	public AccountLockedException(String message) {
		super(message);
	}

	public AccountLockedException(String message, Throwable cause) {
		super(message, cause);
	}


	@Override
	protected int initCode() {
		return 401002;
	}

}
