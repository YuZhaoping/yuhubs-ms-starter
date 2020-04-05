package com.yuhubs.ms.security.web.handler;

import com.yuhubs.ms.security.jwt.JwtTokenService;
import com.yuhubs.ms.security.web.SecurityHandlerSupplier;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.server.WebFilterExchange;
import org.springframework.security.web.server.authentication.ServerAuthenticationSuccessHandler;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

public class TokenByAuthenticationSuccessHandler implements ServerAuthenticationSuccessHandler {

	private static final String X_SET_AUTHORIZATION_BEARER_HEADER = "X-Set-Authorization-Bearer";

	private final SecurityHandlerSupplier supplier;

	private volatile int expirationMinutes;


	public TokenByAuthenticationSuccessHandler(SecurityHandlerSupplier supplier) {
		this.supplier = supplier;
		this.expirationMinutes = -1;
	}


	@Override
	public Mono<Void> onAuthenticationSuccess(WebFilterExchange exchange, Authentication authentication) {
		doAuthenticationSuccess(exchange.getExchange(), authentication);

		return Mono.empty();
	}

	public void doAuthenticationSuccess(ServerWebExchange exchange, Authentication authentication) {

		int minutes = getExpirationMinutes();

		String jwtToken = jwtTokenService().createJwtToken(authentication, minutes);

		exchange.getResponse().getHeaders()
				.set(X_SET_AUTHORIZATION_BEARER_HEADER, jwtToken);
	}


	private final int getExpirationMinutes() {
		if (this.expirationMinutes < 0) {
			this.expirationMinutes = this.supplier.securityProperties().getJwtTokenExpiration();
		}
		return this.expirationMinutes;
	}

	private final JwtTokenService jwtTokenService() {
		return this.supplier.jwtTokenService();
	}

}
