package com.yuhubs.ms.exceptions;

public class BadRequestException extends RestApiException {

	private static final long serialVersionUID = 7735103108979580435L;


	public BadRequestException() {
		super();
	}

	public BadRequestException(String message) {
		super(message);
	}

	public BadRequestException(String message, Throwable cause) {
		super(message, cause);
	}

	public BadRequestException(Throwable cause) {
		super(cause);
	}


	@Override
	protected int initCode() {
		return 400;
	}

}
