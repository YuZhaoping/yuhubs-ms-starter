package com.yuhubs.ms.security.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yuhubs.ms.security.SecurityProperties;
import com.yuhubs.ms.security.jwt.JwtTokenService;
import com.yuhubs.ms.security.jwt.JwtTokenServiceContext;
import com.yuhubs.ms.security.web.handler.AccessForbiddenHandler;
import com.yuhubs.ms.security.web.handler.DefaultAuthenticationFailureHandler;
import com.yuhubs.ms.security.web.handler.TokenByAuthenticationSuccessHandler;
import com.yuhubs.ms.security.web.handler.UnauthorizedEntryPoint;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.server.ServerAuthenticationEntryPoint;
import org.springframework.security.web.server.authentication.ServerAuthenticationFailureHandler;
import org.springframework.security.web.server.authentication.ServerAuthenticationSuccessHandler;
import org.springframework.security.web.server.authorization.ServerAccessDeniedHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class SecurityHandlerSupplier {

	protected final SecurityConfigurationSupport support;

	protected final ObjectMapper objectMapper;

	private final ServerAuthenticationEntryPoint unauthorizedEntryPoint;
	private final ServerAccessDeniedHandler accessDeniedHandler;
	private final ServerAuthenticationSuccessHandler authenticationSuccessHandler;
	private final ServerAuthenticationFailureHandler authenticationFailureHandler;


	public SecurityHandlerSupplier(SecurityConfigurationSupport support,
								   ObjectMapper objectMapper) {
		this.support = support;
		this.objectMapper = objectMapper;

		this.unauthorizedEntryPoint = createUnauthorizedEntryPoint();
		this.accessDeniedHandler = createAccessDeniedHandler();
		this.authenticationSuccessHandler = createAuthenticationSuccessHandler();
		this.authenticationFailureHandler = createAuthenticationFailureHandler();
	}


	public Mono<Void> onAuthenticationSuccess(ServerWebExchange exchange, Authentication authentication) {
		return ((TokenByAuthenticationSuccessHandler)authenticationSuccessHandler)
				.doAuthenticationSuccess(exchange, authentication);
	}

	public final ServerAuthenticationEntryPoint unauthorizedEntryPoint() {
		return this.unauthorizedEntryPoint;
	}

	public final ServerAccessDeniedHandler accessDeniedHandler() {
		return this.accessDeniedHandler;
	}

	public final ServerAuthenticationSuccessHandler authenticationSuccessHandler() {
		return this.authenticationSuccessHandler;
	}

	public final ServerAuthenticationFailureHandler authenticationFailureHandler() {
		return this.authenticationFailureHandler;
	}

	public final ReactiveAuthenticationManager authenticationManager() {
		return this.support.getAuthenticationManager();
	}


	protected ServerAuthenticationEntryPoint createUnauthorizedEntryPoint() {
		return new UnauthorizedEntryPoint(this);
	}

	protected ServerAccessDeniedHandler createAccessDeniedHandler() {
		return new AccessForbiddenHandler(this);
	}

	protected ServerAuthenticationSuccessHandler createAuthenticationSuccessHandler() {
		return new TokenByAuthenticationSuccessHandler(this);
	}

	protected ServerAuthenticationFailureHandler createAuthenticationFailureHandler() {
		return new DefaultAuthenticationFailureHandler(this);
	}


	public final ObjectMapper objectMapper() {
		return this.objectMapper;
	}

	public final JwtTokenServiceContext jwtTokenServiceContext() {
		return this.support.getJwtTokenServiceContext();
	}

	public final SecurityProperties securityProperties() {
		return jwtTokenServiceContext().getSecurityProperties();
	}

	public final JwtTokenService jwtTokenService() {
		return jwtTokenServiceContext().getJwtTokenService();
	}

}
