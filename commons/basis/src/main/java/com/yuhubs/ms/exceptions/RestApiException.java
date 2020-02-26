package com.yuhubs.ms.exceptions;

import com.yuhubs.ms.exceptions.api.ApiError;
import com.yuhubs.ms.exceptions.api.CodeApiErrors;
import com.yuhubs.ms.exceptions.api.RestApiCodeError;

import java.util.List;

public class RestApiException extends RuntimeException
		implements RestApiCodeError {

	private static final long serialVersionUID = -3601992245624311127L;


	protected final CodeApiErrors codeApiErrors;


	public RestApiException() {
		super();

		this.codeApiErrors = new CodeApiErrors(initCode());
	}

	public RestApiException(String message) {
		super(message);

		this.codeApiErrors = new CodeApiErrors(initCode());
	}

	public RestApiException(String message, Throwable cause) {
		super(message, cause);

		this.codeApiErrors = new CodeApiErrors(initCode());
		if (cause != null) {
			this.codeApiErrors.addError(cause);
		}
	}

	public RestApiException(Throwable cause) {
		super(cause);

		this.codeApiErrors = new CodeApiErrors(initCode());
		if (cause != null) {
			this.codeApiErrors.addError(cause);
		}
	}


	@Override
	public int getCode() {
		return this.codeApiErrors.getCode();
	}

	@Override
	public List<ApiError> getErrors() {
		return this.codeApiErrors.getErrors();
	}


	protected int initCode() {
		return -1;
	}

}
