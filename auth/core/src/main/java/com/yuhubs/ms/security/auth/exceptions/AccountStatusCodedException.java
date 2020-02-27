package com.yuhubs.ms.security.auth.exceptions;

import com.yuhubs.ms.exceptions.api.ApiError;
import com.yuhubs.ms.exceptions.api.CodeApiErrors;
import com.yuhubs.ms.exceptions.api.RestApiCodeError;
import org.springframework.security.authentication.AccountStatusException;

import java.util.List;

public class AccountStatusCodedException extends AccountStatusException
		implements RestApiCodeError {

	private static final long serialVersionUID = 5317358058856524584L;


	protected final CodeApiErrors codeApiErrors;


	public AccountStatusCodedException(String message) {
		super(message);

		this.codeApiErrors = new CodeApiErrors(initCode());
	}

	public AccountStatusCodedException(String message, Throwable cause) {
		super(message, cause);

		this.codeApiErrors = new CodeApiErrors(initCode());
		if (cause != null) {
			this.codeApiErrors.addError(cause);
		}
	}


	protected int initCode() {
		return 401000;
	}


	@Override
	public int getCode() {
		return this.codeApiErrors.getCode();
	}

	@Override
	public List<ApiError> getErrors() {
		return this.codeApiErrors.getErrors();
	}

}
