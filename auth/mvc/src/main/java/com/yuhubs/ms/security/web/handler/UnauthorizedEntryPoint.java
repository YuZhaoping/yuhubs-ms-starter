package com.yuhubs.ms.security.web.handler;

import com.yuhubs.ms.security.web.SecurityHandlerSupplier;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class UnauthorizedEntryPoint
		extends DefaultAuthenticationFailureHandler implements AuthenticationEntryPoint {

	public UnauthorizedEntryPoint(SecurityHandlerSupplier supplier) {
		super(supplier);
	}


	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
						 AuthenticationException exception)
			throws IOException, ServletException {

		onAuthenticationFailure(request, response, exception);
	}

}
