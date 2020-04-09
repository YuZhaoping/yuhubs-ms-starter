package com.yuhubs.ms.web;

import org.springframework.boot.autoconfigure.web.ResourceProperties;
import org.springframework.boot.autoconfigure.web.reactive.error.AbstractErrorWebExceptionHandler;
import org.springframework.boot.web.reactive.error.ErrorAttributes;
import org.springframework.context.ApplicationContext;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.*;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.Map;

@Component
@Order(-2)
public class GlobalErrorWebExceptionHandler extends AbstractErrorWebExceptionHandler {

	GlobalErrorWebExceptionHandler(ErrorAttributes errorAttributes,
								   ResourceProperties resourceProperties,
								   ApplicationContext applicationContext,
								   ServerCodecConfigurer serverCodecConfigurer) {
		super(errorAttributes, resourceProperties, applicationContext);

		super.setMessageReaders(serverCodecConfigurer.getReaders());
		super.setMessageWriters(serverCodecConfigurer.getWriters());
	}


	@Override
	protected RouterFunction<ServerResponse> getRoutingFunction(ErrorAttributes errorAttributes) {
		return RouterFunctions.route(RequestPredicates.all(), this::renderErrorResponse);
	}

	private final Mono<ServerResponse> renderErrorResponse(final ServerRequest request) {
		final Map<String, Object> errorAttrs = getErrorAttributes(request, false);

		final URI redirectUri = getRedirectAttribute(errorAttrs);
		if (redirectUri != null) {
			return ServerResponse.permanentRedirect(redirectUri)
					.contentType(MediaType.TEXT_PLAIN)
					.bodyValue(redirectUri.toASCIIString());
		}

		return ServerResponse.status(determineStatus(errorAttrs))
				.contentType(MediaType.APPLICATION_JSON)
				.body(BodyInserters.fromValue(errorAttrs));
	}

	private static HttpStatus determineStatus(Map<String, Object> errorAttrs) {
		HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;

		Object error = errorAttrs.get("error");
		if (error != null && error instanceof RestErrorResponse) {
			RestErrorResponse restError = (RestErrorResponse)error;
			status = HttpStatus.valueOf(restError.getStatusCode());
		}

		return status;
	}

	private static URI getRedirectAttribute(Map<String, Object> errorAttrs) {
		Object obj = errorAttrs.get(GlobalErrorAttributes.REDIRECT_URI_ATTR);
		if (obj != null) {
			return (URI) obj;
		}
		return null;
	}

}
