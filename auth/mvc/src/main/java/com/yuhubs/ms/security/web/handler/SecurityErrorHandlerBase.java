package com.yuhubs.ms.security.web.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yuhubs.ms.security.web.SecurityErrorResponse;
import com.yuhubs.ms.security.web.SecurityHandlerSupplier;
import org.springframework.http.HttpStatus;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

public class SecurityErrorHandlerBase {

	protected final SecurityHandlerSupplier supplier;


	public SecurityErrorHandlerBase(SecurityHandlerSupplier supplier) {
		this.supplier = supplier;
	}


	protected void writeResponse(HttpServletResponse response,
								 HttpStatus httpStatus, Exception exception) throws IOException {

		response.setStatus(httpStatus.value());
		response.setContentType(APPLICATION_JSON_VALUE);

		SecurityErrorResponse errorResponse = SecurityErrorResponse.of(httpStatus, exception);

		objectMapper().writeValue(response.getWriter(), errorResponse.toRestApiError());
	}


	protected final ObjectMapper objectMapper() {
		return this.supplier.objectMapper();
	}

}
