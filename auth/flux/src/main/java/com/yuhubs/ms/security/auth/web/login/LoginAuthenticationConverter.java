package com.yuhubs.ms.security.auth.web.login;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yuhubs.ms.security.auth.web.dto.LoginRequestDto;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.server.authentication.ServerAuthenticationConverter;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.io.InputStream;
import java.io.SequenceInputStream;
import java.util.Optional;

public final class LoginAuthenticationConverter implements ServerAuthenticationConverter {

	private final ObjectMapper objectMapper;


	public LoginAuthenticationConverter(ObjectMapper objectMapper) {
		this.objectMapper = objectMapper;
	}


	@Override
	public Mono<Authentication> convert(ServerWebExchange exchange) {
		ServerHttpRequest request = exchange.getRequest();

		if (!request.getMethod().matches("POST")) {
			return Mono.error(new AuthenticationServiceException(
					"Authentication method not supported: " + request.getMethodValue()
			));
		}

		return request.getBody().reduce(
				new InputStream() {
					public int read() { return -1; }
				},
				(InputStream s, DataBuffer d) -> new SequenceInputStream(s, d.asInputStream())
		).flatMap(this::toAuthenticationToken);
	}

	private Mono<Authentication> toAuthenticationToken(InputStream inputStream) {
		LoginRequestDto dto = null;

		try {
			dto = this.objectMapper.readValue(inputStream, LoginRequestDto.class);
		} catch (Exception e) {
			return Mono.error(e);
		}

		String username = Optional
				.ofNullable(dto.getUsername())
				.map(String::trim)
				.orElse("");

		String password = Optional
				.ofNullable(dto.getPassword())
				.map(String::trim)
				.orElse("");

		UsernamePasswordAuthenticationToken token =
				new UsernamePasswordAuthenticationToken(username, password);

		return Mono.just(token);
	}

}
