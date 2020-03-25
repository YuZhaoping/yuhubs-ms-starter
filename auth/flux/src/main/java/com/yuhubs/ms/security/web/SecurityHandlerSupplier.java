package com.yuhubs.ms.security.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yuhubs.ms.security.SecurityProperties;
import com.yuhubs.ms.security.jwt.JwtTokenService;
import com.yuhubs.ms.security.jwt.JwtTokenServiceContext;
import com.yuhubs.ms.security.web.handler.AccessForbiddenHandler;
import com.yuhubs.ms.security.web.handler.UnauthorizedEntryPoint;
import org.springframework.security.web.server.ServerAuthenticationEntryPoint;
import org.springframework.security.web.server.authorization.ServerAccessDeniedHandler;
import org.springframework.stereotype.Component;

@Component
public class SecurityHandlerSupplier {

	protected final SecurityConfigurationSupport support;

	protected final ObjectMapper objectMapper;

	private final ServerAuthenticationEntryPoint unauthorizedEntryPoint;
	private final ServerAccessDeniedHandler accessDeniedHandler;


	public SecurityHandlerSupplier(SecurityConfigurationSupport support,
								   ObjectMapper objectMapper) {
		this.support = support;
		this.objectMapper = objectMapper;

		this.unauthorizedEntryPoint = createUnauthorizedEntryPoint();
		this.accessDeniedHandler = createAccessDeniedHandler();
	}


	public final ServerAuthenticationEntryPoint unauthorizedEntryPoint() {
		return this.unauthorizedEntryPoint;
	}

	public final ServerAccessDeniedHandler accessDeniedHandler() {
		return this.accessDeniedHandler;
	}


	protected ServerAuthenticationEntryPoint createUnauthorizedEntryPoint() {
		return new UnauthorizedEntryPoint(this);
	}

	protected ServerAccessDeniedHandler createAccessDeniedHandler() {
		return new AccessForbiddenHandler(this);
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
