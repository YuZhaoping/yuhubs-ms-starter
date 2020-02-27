package com.yuhubs.ms.security.exceptions;

public class JwtExpiredException extends BadCredentialsCodedException {

	private static final long serialVersionUID = 5296798059380524512L;


	public JwtExpiredException(String message) {
		super(message);
	}

	public JwtExpiredException(String message, Throwable cause) {
		super(message, cause);
	}


	@Override
	protected int initCode() {
		return 401411;
	}

}
