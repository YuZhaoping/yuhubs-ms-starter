package com.yuhubs.ms.web;

import com.yuhubs.ms.exceptions.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.handler.WebFluxResponseStatusExceptionHandler;

public class RestExceptionHandler extends WebFluxResponseStatusExceptionHandler {

	@Override
	public HttpStatus determineStatus(Throwable ex) {
		if (ex instanceof BadRequestException) {
			return HttpStatus.BAD_REQUEST;
		}

		HttpStatus status = super.determineStatus(ex);

		if (status != null) {
			return status;
		}

		if (ex instanceof RuntimeException) {
			return HttpStatus.INTERNAL_SERVER_ERROR;
		}

		return null;
	}

}
