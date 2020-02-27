package com.yuhubs.ms.security.exceptions;

public class InvalidJwtClaimException extends InvalidJwtException {

	private static final long serialVersionUID = -7382861745604146157L;


	public InvalidJwtClaimException(String message) {
		super(message);
	}

	public InvalidJwtClaimException(String message, Throwable cause) {
		super(message, cause);
	}


	@Override
	protected int initCode() {
		return 401402;
	}

}
