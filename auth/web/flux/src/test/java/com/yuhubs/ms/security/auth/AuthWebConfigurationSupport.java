package com.yuhubs.ms.security.auth;

import com.yuhubs.ms.security.auth.web.AuthUserController;
import com.yuhubs.ms.security.auth.web.AuthWebExceptionHandler;
import com.yuhubs.ms.security.auth.web.AuthWebSecurityContext;
import com.yuhubs.ms.web.RestConfigurationSupport;
import com.yuhubs.ms.web.RestExceptionHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AuthWebConfigurationSupport extends RestConfigurationSupport {

	@Autowired
	private AuthWebSecurityContext securityContext;


	@Override
	protected RestExceptionHandler createRestExceptionHandler() {
		return new AuthWebExceptionHandler();
	}


	@Bean
	public AuthUserController authUserController() {
		return this.securityContext.createAuthUserController();
	}

}
