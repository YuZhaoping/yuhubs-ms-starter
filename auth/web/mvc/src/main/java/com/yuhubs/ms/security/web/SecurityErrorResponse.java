package com.yuhubs.ms.security.web;

import com.yuhubs.ms.exceptions.api.ApiError;
import com.yuhubs.ms.exceptions.api.RestApiCodeError;
import com.yuhubs.ms.web.RestErrorResponse;
import com.yuhubs.ms.web.api.RestApiError;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.List;

public class SecurityErrorResponse extends RestErrorResponse {

	public static SecurityErrorResponse of(HttpStatus status,
										   Throwable ex,
										   String message,
										   Object body) {
		if (ex == null) {
			return new SecurityErrorResponse(
					status.value(),
					status.value(),
					(message == null) ? status.getReasonPhrase() : message,
					null,
					body);
		} else  if (ex instanceof RestApiCodeError) {
			RestApiCodeError codeError = (RestApiCodeError)ex;
			int code = codeError.getCode();
			if (code < 0) {
				code = status.value();
			}
			return new SecurityErrorResponse(
					status.value(),
					code,
					(message == null) ? ex.getMessage() : message,
					codeError.getErrors(),
					body);
		} else {
			List<ApiError> errors = new ArrayList<>();
			errors.add(ApiError.of(ex));
			return new SecurityErrorResponse(
					status.value(),
					status.value(),
					(message == null) ? ex.getMessage() : message,
					errors,
					body);
		}
	}

	public static SecurityErrorResponse of(HttpStatus status, Throwable ex, String message) {
		return of(status, ex, message, null);
	}

	public static SecurityErrorResponse of(HttpStatus status, Throwable ex) {
		return of(status, ex, null, null);
	}

	public static SecurityErrorResponse of(HttpStatus status) {
		return of(status, null, null, null);
	}


	protected SecurityErrorResponse(Integer statusCode, Integer code, String message,
									List<ApiError> errors, Object body) {
		super(statusCode, code, message, errors, body);
	}


	@Override
	public RestApiError<SecurityErrorResponse> toRestApiError() {
		return new RestApiError<>(this);
	}

}
