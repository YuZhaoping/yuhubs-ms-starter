package com.yuhubs.ms.web.tests;

import com.yuhubs.ms.web.RestExceptionHandler;
import com.yuhubs.ms.web.annotation.ExceptionStatusMapper;
import org.springframework.http.HttpStatus;

public class DefaultExceptionHandler extends RestExceptionHandler {

	@ExceptionStatusMapper(Exception.class)
	public HttpStatus mapBaseException() {
		return HttpStatus.INTERNAL_SERVER_ERROR;
	}

	@ExceptionStatusMapper(UnsupportedOperationException.class)
	public HttpStatus mapUnsupportedOperationException() {
		return HttpStatus.NOT_IMPLEMENTED;
	}

}
