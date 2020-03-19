package com.yuhubs.ms.security.web;

import com.yuhubs.ms.security.SecurityProperties;
import com.yuhubs.ms.security.jwt.JwtTokenService;
import com.yuhubs.ms.security.jwt.JwtTokenServiceContext;
import org.springframework.stereotype.Component;

@Component
public class SecurityHandlerSupplier {

	protected final SecurityConfigurationSupport support;


	public SecurityHandlerSupplier(SecurityConfigurationSupport support) {
		this.support = support;
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
