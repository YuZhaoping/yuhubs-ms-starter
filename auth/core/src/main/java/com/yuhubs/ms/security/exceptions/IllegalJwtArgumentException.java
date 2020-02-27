package com.yuhubs.ms.security.exceptions;

import com.yuhubs.ms.exceptions.api.ApiError;
import com.yuhubs.ms.exceptions.api.CodeApiErrors;
import com.yuhubs.ms.exceptions.api.RestApiCodeError;
import org.springframework.security.core.AuthenticationException;

import java.util.List;

public class IllegalJwtArgumentException extends AuthenticationException
		implements RestApiCodeError {

	private static final long serialVersionUID = 3895926252448659654L;


	protected final CodeApiErrors codeApiErrors;


	public IllegalJwtArgumentException(String message) {
		super(message);

		this.codeApiErrors = new CodeApiErrors(initCode());
	}

	public IllegalJwtArgumentException(String message, Throwable cause) {
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
		return 401400;
	}

}
