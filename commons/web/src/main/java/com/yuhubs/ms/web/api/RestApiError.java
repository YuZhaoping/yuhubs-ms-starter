package com.yuhubs.ms.web.api;

public class RestApiError<T> extends RestApiEntity<T> {

	private final T error;


	public RestApiError(T error) {
		this.error = error;
	}


	public T getError() {
		return this.error;
	}

}
