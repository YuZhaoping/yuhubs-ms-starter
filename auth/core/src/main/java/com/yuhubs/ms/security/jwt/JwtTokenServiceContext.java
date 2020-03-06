package com.yuhubs.ms.security.jwt;

import com.yuhubs.ms.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JwtTokenServiceContext {

	protected final SecurityProperties securityProperties;

	protected JwtTokenService jwtTokenService;


	public JwtTokenServiceContext() {
		this.securityProperties = new SecurityProperties();
	}


	protected JwtTokenService createJwtTokenService(SecurityProperties properties) {
		return new DefaultJwtTokenService(properties);
	}


	@Bean
	public SecurityProperties securityProperties() {
		return this.securityProperties;
	}

	@Bean
	public JwtTokenService jwtTokenService(SecurityProperties properties) {
		// lazy create for security properties
		if (this.jwtTokenService == null) {
			this.jwtTokenService = createJwtTokenService(properties);
		}
		return this.jwtTokenService;
	}


	public SecurityProperties getSecurityProperties() {
		return this.securityProperties;
	}

	public JwtTokenService getJwtTokenService() {
		return this.jwtTokenService;
	}

}
