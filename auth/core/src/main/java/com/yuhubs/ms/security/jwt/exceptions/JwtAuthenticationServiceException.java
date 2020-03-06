package com.yuhubs.ms.security.jwt.exceptions;

import com.yuhubs.ms.exceptions.api.ApiError;
import com.yuhubs.ms.exceptions.api.CodeApiErrors;
import com.yuhubs.ms.exceptions.api.RestApiCodeError;
import org.springframework.security.authentication.InternalAuthenticationServiceException;

import java.util.List;

public class JwtAuthenticationServiceException extends InternalAuthenticationServiceException
		implements RestApiCodeError {

	private static final long serialVersionUID = -3222221181929401053L;


	protected final CodeApiErrors codeApiErrors;


	public JwtAuthenticationServiceException(String message) {
		super(message);

		this.codeApiErrors = new CodeApiErrors(initCode());
	}

	public JwtAuthenticationServiceException(String message, Throwable cause) {
		super(message, cause);

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
		return 401500;
	}

}
