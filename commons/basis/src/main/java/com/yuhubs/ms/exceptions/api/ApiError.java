package com.yuhubs.ms.exceptions.api;

public abstract class ApiError {

	public static ApiError of(Throwable ex) {
		return new ThrowsApiError(ex);
	}

}
