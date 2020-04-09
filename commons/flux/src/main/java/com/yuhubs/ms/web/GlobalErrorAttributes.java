package com.yuhubs.ms.web;

import org.springframework.boot.web.reactive.error.DefaultErrorAttributes;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebExchangeBindException;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.server.ResponseStatusException;

import java.util.Map;

@Component
public class GlobalErrorAttributes extends DefaultErrorAttributes {

	private final RestExceptionHandler restExceptionHandler;


	GlobalErrorAttributes(RestExceptionHandler restExceptionHandler) {
		super(false);

		this.restExceptionHandler = restExceptionHandler;
	}


	@Override
	public Map<String, Object> getErrorAttributes(ServerRequest request, boolean includeStackTrace) {
		Throwable error = getError(request);

		if (error == null) {
			return super.getErrorAttributes(request, includeStackTrace);
		}

		RestErrorResponse response =
				RestErrorResponse.of(determineHttpStatus(error),
						determineException(error), determineMessage(error));

		return response.toRestApiError().toAttributes();
	}


	private final HttpStatus determineHttpStatus(Throwable error) {
		if (error instanceof ResponseStatusException) {
			return ((ResponseStatusException) error).getStatus();
		}

		HttpStatus status = this.restExceptionHandler.determineStatus(error);
		if (status == null) {
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}

		return status;
	}

	private final Throwable determineException(Throwable error) {
		if (error instanceof ResponseStatusException) {
			return (error.getCause() != null) ? error.getCause() : error;
		}
		return error;
	}

	private final String determineMessage(Throwable error) {
		if (error instanceof WebExchangeBindException) {
			return error.getMessage();
		}
		if (error instanceof ResponseStatusException) {
			return ((ResponseStatusException) error).getReason();
		}
		return error.getMessage();
	}

}
