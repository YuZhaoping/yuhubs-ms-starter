package com.yuhubs.ms.security.web.handler;

import com.yuhubs.ms.security.web.SecurityHandlerSupplier;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

import static org.springframework.http.HttpStatus.UNAUTHORIZED;

public class DefaultAuthenticationFailureHandler
		extends SecurityErrorHandlerBase implements AuthenticationFailureHandler {

	public DefaultAuthenticationFailureHandler(SecurityHandlerSupplier supplier) {
		super(supplier);
	}


	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
										AuthenticationException exception)
			throws IOException, ServletException {

		HttpStatus httpStatus = translateAuthenticationException(exception);

		writeResponse(response, httpStatus, exception);
	}

	protected HttpStatus translateAuthenticationException(AuthenticationException exception) {
		return UNAUTHORIZED;
	}

}
