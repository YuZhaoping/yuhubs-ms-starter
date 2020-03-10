package com.yuhubs.ms.security.auth;

import com.yuhubs.ms.security.auth.web.AuthUserController;
import com.yuhubs.ms.security.auth.web.AuthWebExceptionHandler;
import com.yuhubs.ms.security.auth.web.AuthWebSecurityContext;
import com.yuhubs.ms.web.RestConfigurationSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AuthWebConfigurationSupport extends RestConfigurationSupport {

	@Autowired
	private AuthWebSecurityContext securityContext;


	@Bean
	public AuthUserController authUserController() {
		return this.securityContext.createAuthUserController();
	}

	@Bean
	public Object controllerAdvice() {
		return new AuthWebExceptionHandler();
	}

}
