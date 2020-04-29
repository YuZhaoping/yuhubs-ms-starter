package com.yuhubs.ms.security.web.handler;

import com.yuhubs.ms.security.web.SecurityHandlerSupplier;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.server.authorization.ServerAccessDeniedHandler;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import static org.springframework.http.HttpStatus.FORBIDDEN;

public class AccessForbiddenHandler extends SecurityErrorHandlerBase implements ServerAccessDeniedHandler {

	public AccessForbiddenHandler(SecurityHandlerSupplier supplier) {
		super(supplier);
	}


	@Override
	public Mono<Void> handle(ServerWebExchange exchange, AccessDeniedException denied) {

		HttpStatus httpStatus = translateAccessDeniedException(denied);

		return writeResponse(exchange.getResponse(), httpStatus, denied);
	}


	protected HttpStatus translateAccessDeniedException(AccessDeniedException denied) {
		return FORBIDDEN;
	}

}
