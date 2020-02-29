package com.yuhubs.ms.web.api;

public class RestApiData<T> extends RestApiEntity<T> {

	private final T data;


	public RestApiData(T data) {
		this.data = data;
	}


	public T getData() {
		return this.data;
	}

}
