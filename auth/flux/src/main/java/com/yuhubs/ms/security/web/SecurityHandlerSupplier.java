package com.yuhubs.ms.security.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yuhubs.ms.security.SecurityProperties;
import com.yuhubs.ms.security.jwt.JwtTokenService;
import com.yuhubs.ms.security.jwt.JwtTokenServiceContext;
import org.springframework.stereotype.Component;

@Component
public class SecurityHandlerSupplier {

	protected final SecurityConfigurationSupport support;

	protected final ObjectMapper objectMapper;


	public SecurityHandlerSupplier(SecurityConfigurationSupport support,
								   ObjectMapper objectMapper) {
		this.support = support;
		this.objectMapper = objectMapper;
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
