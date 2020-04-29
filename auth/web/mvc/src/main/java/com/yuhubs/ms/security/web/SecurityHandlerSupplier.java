package com.yuhubs.ms.security.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yuhubs.ms.security.SecurityProperties;
import com.yuhubs.ms.security.jwt.JwtTokenService;
import com.yuhubs.ms.security.jwt.JwtTokenServiceContext;
import com.yuhubs.ms.security.web.handler.AccessForbiddenHandler;
import com.yuhubs.ms.security.web.handler.DefaultAuthenticationFailureHandler;
import com.yuhubs.ms.security.web.handler.TokenByAuthenticationSuccessHandler;
import com.yuhubs.ms.security.web.handler.UnauthorizedEntryPoint;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

@Component
public class SecurityHandlerSupplier {

	protected final SecurityConfigurationSupport support;

	protected final ObjectMapper objectMapper;


	private final AuthenticationEntryPoint unauthorizedEntryPoint;
	private final AccessDeniedHandler accessDeniedHandler;
	private final AuthenticationSuccessHandler authenticationSuccessHandler;
	private final AuthenticationFailureHandler authenticationFailureHandler;


	public SecurityHandlerSupplier(SecurityConfigurationSupport support,
								   ObjectMapper objectMapper) {
		this.support = support;
		this.objectMapper = objectMapper;

		this.unauthorizedEntryPoint = createUnauthorizedEntryPoint();
		this.accessDeniedHandler = createAccessDeniedHandler();
		this.authenticationSuccessHandler = createAuthenticationSuccessHandler();
		this.authenticationFailureHandler = createAuthenticationFailureHandler();
	}


	public final AuthenticationEntryPoint unauthorizedEntryPoint() {
		return this.unauthorizedEntryPoint;
	}

	public final AccessDeniedHandler accessDeniedHandler() {
		return this.accessDeniedHandler;
	}

	public final AuthenticationSuccessHandler authenticationSuccessHandler() {
		return this.authenticationSuccessHandler;
	}

	public final AuthenticationFailureHandler authenticationFailureHandler() {
		return this.authenticationFailureHandler;
	}


	protected AuthenticationEntryPoint createUnauthorizedEntryPoint() {
		return new UnauthorizedEntryPoint(this);
	}

	protected AccessDeniedHandler createAccessDeniedHandler() {
		return new AccessForbiddenHandler(this);
	}

	protected AuthenticationSuccessHandler createAuthenticationSuccessHandler() {
		return new TokenByAuthenticationSuccessHandler(this);
	}

	protected AuthenticationFailureHandler createAuthenticationFailureHandler() {
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
