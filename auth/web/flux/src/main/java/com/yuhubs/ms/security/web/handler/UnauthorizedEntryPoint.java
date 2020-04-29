package com.yuhubs.ms.security.web.handler;

import com.yuhubs.ms.security.web.SecurityHandlerSupplier;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.server.ServerAuthenticationEntryPoint;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import static org.springframework.http.HttpStatus.UNAUTHORIZED;

public class UnauthorizedEntryPoint extends SecurityErrorHandlerBase implements ServerAuthenticationEntryPoint {

	public UnauthorizedEntryPoint(SecurityHandlerSupplier supplier) {
		super(supplier);
	}


	@Override
	public Mono<Void> commence(ServerWebExchange exchange, AuthenticationException exception) {

		HttpStatus httpStatus = translateAuthenticationException(exception);

		return writeResponse(exchange.getResponse(), httpStatus, exception);
	}


	protected HttpStatus translateAuthenticationException(AuthenticationException exception) {
		return UNAUTHORIZED;
	}

}
