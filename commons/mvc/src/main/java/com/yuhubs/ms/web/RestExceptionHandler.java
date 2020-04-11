package com.yuhubs.ms.web;

import com.yuhubs.ms.exceptions.BadRequestException;
import org.slf4j.Logger;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.net.URI;

import static org.springframework.http.HttpStatus.*;

public class RestExceptionHandler extends ResponseEntityExceptionHandler {

	@Override
	protected ResponseEntity<Object> handleExceptionInternal(
			Exception ex, Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {
		logError(request, status, ex);
		RestErrorResponse response = RestErrorResponse.of(status, ex);
		return super.handleExceptionInternal(ex,
				response.toRestApiError(),
				headers, status, request);
	}


	protected void logError(WebRequest request, HttpStatus status, Throwable throwable) {
		if (throwable != null) {
			Logger logger = getLogger();
			if (logger != null) {
				logger.error("{} for {}\n{}",
						formatHttpStatus(status), formatRequest(request),
						formatThrowable(throwable));
			}
		}
	}

	protected Logger getLogger() {
		return null;
	}


	private final String formatHttpStatus(HttpStatus status) {
		return status.value() + " " + status.getReasonPhrase();
	}

	private final String formatRequest(WebRequest request) {
		if (request instanceof ServletWebRequest) {
			ServletWebRequest servletRequest = (ServletWebRequest)request;
			return "HTTP " + servletRequest.getRequest().getMethod() +
					" \"" + servletRequest.getRequest().getRequestURI() + "\"";
		}
		return "HTTP ";
	}

	private final String formatThrowable(Throwable throwable) {
		return throwable.getClass().getName() + ": " + throwable.getMessage();
	}


	protected ResponseEntity<Object> handleExceptionInternal(
			Exception ex, HttpStatus status, WebRequest request) {
		HttpHeaders headers = new HttpHeaders();
		return handleExceptionInternal(ex, null, headers, status, request);
	}


	@Override
	protected ResponseEntity<Object> handleNoHandlerFoundException(
			NoHandlerFoundException ex, HttpHeaders headers, HttpStatus status,
			WebRequest request) {
		URI redirectUri = getNotFoundRedirectUri(request, ex.getHttpMethod(), ex.getRequestURL());
		if (redirectUri != null) {
			headers.setLocation(redirectUri);
			return new ResponseEntity<>(headers, MOVED_PERMANENTLY);
		}
		return handleExceptionInternal(ex, null, headers, status, request);
	}


	protected URI getNotFoundRedirectUri(WebRequest request,
										 String httpMethod,
										 String requestURL) {
		return null;
	}


	@ExceptionHandler(BadRequestException.class)
	public ResponseEntity<Object> handleBadRequestException(
			final BadRequestException ex, WebRequest request) {
		return handleExceptionInternal(ex, BAD_REQUEST, request);
	}

	@ExceptionHandler(RuntimeException.class)
	public ResponseEntity<Object> handleRuntimeException(
			final RuntimeException ex, WebRequest request) {
		return handleExceptionInternal(ex, INTERNAL_SERVER_ERROR, request);
	}

}
