package com.yuhubs.ms.security.auth.web;

import com.yuhubs.ms.security.auth.AuthSecurityContext;
import com.yuhubs.ms.security.auth.AuthUserService;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

public class AuthWebSecurityContext extends AuthSecurityContext {

	private final AuthConfigurationSupport support;


	AuthWebSecurityContext(AuthConfigurationSupport support) {
		super(support.getJwtTokenServiceContext());
		this.support = support;
	}


	public AuthUserController createAuthUserController() {
		return new AuthUserController(this);
	}


	public AuthenticationSuccessHandler authenticationSuccessHandler() {
		return this.support.getSecurityHandlerSupplier().authenticationSuccessHandler();
	}

	public AuthenticationFailureHandler authenticationFailureHandler() {
		return this.support.getSecurityHandlerSupplier().authenticationFailureHandler();
	}


	public AuthUserService authUserService() {
		return this.support.getAuthUserService();
	}

}
