package com.yuhubs.ms.exceptions.api;

import java.util.ArrayList;
import java.util.List;

public final class CodeApiErrors {

	private int code = -1;

	private List<ApiError> errors = null;


	public CodeApiErrors() {
	}

	public CodeApiErrors(int code) {
		this.code = code;
	}


	public int getCode() {
		return code;
	}

	public CodeApiErrors setCode(int code) {
		this.code = code;
		return this;
	}

	public List<ApiError> getErrors() {
		return errors;
	}

	public CodeApiErrors addError(ApiError error) {
		ensureErrorList().add(error);
		return this;
	}

	public CodeApiErrors addError(Throwable t) {
		ensureErrorList().add(ApiError.of(t));
		return this;
	}


	private List<ApiError> ensureErrorList() {
		if (this.errors == null) {
			this.errors = createErrorList();
		}
		return this.errors;
	}

	private List<ApiError> createErrorList() {
		return new ArrayList<>();
	}

}
