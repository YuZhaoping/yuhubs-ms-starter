package com.yuhubs.ms.security.web.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yuhubs.ms.security.web.SecurityErrorResponse;
import com.yuhubs.ms.security.web.SecurityHandlerSupplier;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import reactor.core.publisher.Mono;

import static org.springframework.http.MediaType.APPLICATION_JSON;

public class SecurityErrorHandlerBase {

	protected final SecurityHandlerSupplier supplier;


	public SecurityErrorHandlerBase(SecurityHandlerSupplier supplier) {
		this.supplier = supplier;
	}


	protected Mono<Void> writeResponse(ServerHttpResponse response,
									   HttpStatus httpStatus,
									   Exception exception) {
		response.setStatusCode(httpStatus);
		response.getHeaders().setContentType(APPLICATION_JSON);

		DataBuffer dataBuffer = response.bufferFactory().allocateBuffer();

		SecurityErrorResponse errorResponse = SecurityErrorResponse.of(httpStatus, exception);

		try {
			objectMapper().writeValue(dataBuffer.asOutputStream(), errorResponse.toRestApiError());
		} catch (Exception ex) {
			DataBufferUtils.release(dataBuffer);
			return Mono.error(ex);
		}

		return response.writeWith(Mono.just(dataBuffer));
	}

	protected final ObjectMapper objectMapper() {
		return this.supplier.objectMapper();
	}

}
