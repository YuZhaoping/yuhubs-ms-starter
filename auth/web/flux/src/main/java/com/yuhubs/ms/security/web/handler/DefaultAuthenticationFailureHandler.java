package com.yuhubs.ms.security.web.handler;

import com.yuhubs.ms.security.web.SecurityHandlerSupplier;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.server.WebFilterExchange;
import org.springframework.security.web.server.authentication.ServerAuthenticationFailureHandler;
import reactor.core.publisher.Mono;

import static org.springframework.http.HttpStatus.UNAUTHORIZED;

public class DefaultAuthenticationFailureHandler
		extends SecurityErrorHandlerBase implements ServerAuthenticationFailureHandler {

	public DefaultAuthenticationFailureHandler(SecurityHandlerSupplier supplier) {
		super(supplier);
	}


	@Override
	public Mono<Void> onAuthenticationFailure(WebFilterExchange exchange, AuthenticationException exception) {

		HttpStatus httpStatus = translateAuthenticationException(exception);

		return writeResponse(exchange.getExchange().getResponse(), httpStatus, exception);
	}


	protected HttpStatus translateAuthenticationException(AuthenticationException exception) {
		return UNAUTHORIZED;
	}

}
