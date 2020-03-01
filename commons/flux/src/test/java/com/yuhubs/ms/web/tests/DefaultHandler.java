package com.yuhubs.ms.web.tests;

import com.yuhubs.ms.web.RestErrorResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.*;
import reactor.core.publisher.Mono;

@Component
public class DefaultHandler {

	@Bean
	public RouterFunction<ServerResponse> routeRequest() {
		return RouterFunctions.route(RequestPredicates.path("/"),
				this::handleUnmappedRequest);
	}

	public Mono<ServerResponse> handleUnmappedRequest(ServerRequest request) {
		return ServerResponse
				.status(HttpStatus.NOT_FOUND)
				.bodyValue(RestErrorResponse
						.of(HttpStatus.NOT_FOUND)
						.toRestApiError());
	}

}
