package com.yuhubs.ms.web;

import org.springframework.boot.web.reactive.error.DefaultErrorAttributes;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebExchangeBindException;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.server.ResponseStatusException;

import java.net.URI;
import java.util.LinkedHashMap;
import java.util.Map;

@Component
public class GlobalErrorAttributes extends DefaultErrorAttributes {

	static final String REDIRECT_URI_ATTR = "redirect-uri";


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

		HttpStatus status = determineHttpStatus(error);
		String message = determineMessage(error);

		if (status.value() == HttpStatus.NOT_FOUND.value() &&
				this.restExceptionHandler.getNoMatchingHandlerExceptionMessage().equals(message)) {
			URI redirectUri = this.restExceptionHandler
					.getNotFoundRedirectUri(request, request.methodName(), request.path());

			if (redirectUri != null) {
				return redirectAttributes(redirectUri);
			}
		}

		RestErrorResponse response = RestErrorResponse.of(status, determineException(error), message);

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

	private static Throwable determineException(Throwable error) {
		if (error instanceof ResponseStatusException) {
			return (error.getCause() != null) ? error.getCause() : error;
		}
		return error;
	}

	private static String determineMessage(Throwable error) {
		if (error instanceof WebExchangeBindException) {
			return error.getMessage();
		}
		if (error instanceof ResponseStatusException) {
			return ((ResponseStatusException) error).getReason();
		}
		return error.getMessage();
	}


	private static Map<String, Object> redirectAttributes(URI redirectUri) {
		Map<String, Object> attributes = new LinkedHashMap<>();

		attributes.put(REDIRECT_URI_ATTR, redirectUri);

		return attributes;
	}

	RestExceptionHandler getRestExceptionHandler() {
		return this.restExceptionHandler;
	}

}
