package com.yuhubs.ms.web;

import com.yuhubs.ms.exceptions.api.ApiError;
import com.yuhubs.ms.exceptions.api.RestApiCodeError;
import com.yuhubs.ms.web.api.RestApiError;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class RestErrorResponse {

	private final Integer statusCode;

	private final Integer code;

	private final String message;

	private final List<ApiError> errors;

	private final Object body;


	public static RestErrorResponse of(HttpStatus status, Throwable ex, Object body) {
		if (ex == null) {
			return new RestErrorResponse(
					status.value(),
					status.value(),
					status.getReasonPhrase(),
					null,
					body);
		} else  if (ex instanceof RestApiCodeError) {
			RestApiCodeError codeError = (RestApiCodeError)ex;
			int code = codeError.getCode();
			if (code < 0) {
				code = status.value();
			}
			return new RestErrorResponse(
					status.value(),
					code,
					ex.getMessage(),
					codeError.getErrors(),
					body);
		} else {
			List<ApiError> errors = new ArrayList<>();
			errors.add(ApiError.of(ex));
			return new RestErrorResponse(
					status.value(),
					status.value(),
					ex.getMessage(),
					errors,
					body);
		}
	}

	public static RestErrorResponse of(HttpStatus status, Throwable ex) {
		return of(status, ex, null);
	}

	public static RestErrorResponse of(HttpStatus status) {
		return of(status, null, null);
	}


	protected RestErrorResponse(Integer statusCode, Integer code, String message,
								List<ApiError> errors, Object body) {
		this.statusCode = statusCode;
		this.code = code;
		this.message = message;
		this.errors = errors;
		this.body = body;
	}


	public RestApiError<? extends RestErrorResponse> toRestApiError() {
		return new RestApiError<>(this);
	}

	public Map<String, Object> toAttributes() {
		Map<String, Object> attrs = new LinkedHashMap<>();

		attrs.put("statusCode", getStatusCode());
		attrs.put("code", getCode());
		attrs.put("message", getMessage());
		if (getErrors() != null) attrs.put("errors", getErrors());
		if (getBody() != null) attrs.put("body", getBody());

		return attrs;
	}


	public Integer getStatusCode() {
		return statusCode;
	}

	public Integer getCode() {
		return code;
	}

	public String getMessage() {
		return message;
	}

	public List<ApiError> getErrors() {
		return errors;
	}

	public Object getBody() {
		return body;
	}

}
