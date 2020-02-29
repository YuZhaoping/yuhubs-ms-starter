package com.yuhubs.ms.web.api;

import java.util.LinkedHashMap;
import java.util.Map;

public class RestApiError<T> extends RestApiEntity<T> {

	private final T error;


	public RestApiError(T error) {
		this.error = error;
	}


	public T getError() {
		return this.error;
	}

	public Map<String, Object> toAttributes() {
		Map<String, Object> attrs = new LinkedHashMap<>();

		attrs.put("apiVersion", getApiVersion());
		attrs.put("error", getError());

		return attrs;
	}

}
