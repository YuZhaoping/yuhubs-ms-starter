package com.yuhubs.ms.security.web.jwt;

import com.yuhubs.ms.security.web.SecurityHandlerSupplier;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.server.context.ServerSecurityContextRepository;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

public class JwtSecurityContextRepository implements ServerSecurityContextRepository {

	private static final String AUTHORIZATION_HEADER = "Authorization";
	private static final String TOKEN_PREFIX = "Bearer ";

	private final SecurityHandlerSupplier supplier;


	public JwtSecurityContextRepository(SecurityHandlerSupplier supplier) {
		this.supplier = supplier;
	}


	@Override
	public Mono<Void> save(ServerWebExchange exchange, SecurityContext context) {
		return Mono.empty();
	}

	@Override
	public Mono<SecurityContext> load(ServerWebExchange exchange) {
		Authentication authentication = null;

		try {
			authentication = getAuthentication(exchange.getRequest());
		} catch (AuthenticationException e) {
			return Mono.error(e);
		}

		if (authentication == null) {
			return Mono.empty();
		}

		SecurityContext	context = SecurityContextHolder.getContext();

		context.setAuthentication(authentication);

		return Mono.just(context);
	}


	private Authentication getAuthentication(ServerHttpRequest request)
			throws AuthenticationException {
		String authHeader = request.getHeaders().getFirst(AUTHORIZATION_HEADER);

		if (StringUtils.isEmpty(authHeader)) {
			return null;
		}

		if (!StringUtils.substringMatch(authHeader, 0, TOKEN_PREFIX)) {
			return null;
		}

		String jwtToken = authHeader.substring(TOKEN_PREFIX.length());

		return this.supplier.jwtTokenService().parseJwtToken(jwtToken);
	}

}
