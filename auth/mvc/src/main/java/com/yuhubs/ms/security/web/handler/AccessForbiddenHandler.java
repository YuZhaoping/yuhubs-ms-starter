package com.yuhubs.ms.security.web.handler;

import com.yuhubs.ms.security.web.SecurityHandlerSupplier;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

import static org.springframework.http.HttpStatus.FORBIDDEN;

public class AccessForbiddenHandler extends SecurityErrorHandlerBase implements AccessDeniedHandler {

	public AccessForbiddenHandler(SecurityHandlerSupplier supplier) {
		super(supplier);
	}


	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response,
					   AccessDeniedException exception)
			throws IOException, ServletException {

		HttpStatus httpStatus = translateAccessDeniedException(exception);

		writeResponse(response, httpStatus, exception);
	}

	protected HttpStatus translateAccessDeniedException(AccessDeniedException exception) {
		return FORBIDDEN;
	}

}
