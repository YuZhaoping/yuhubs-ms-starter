package com.yuhubs.ms.security.auth.web;

import com.yuhubs.ms.security.auth.AuthSecurityContext;
import com.yuhubs.ms.security.auth.AuthUserService;

public class AuthWebSecurityContext extends AuthSecurityContext implements AuthUserService.Provider {

	private final AuthConfigurationSupport support;


	AuthWebSecurityContext(AuthConfigurationSupport support) {
		super(support.getJwtTokenServiceContext());
		this.support = support;
	}


	public AuthUserController createAuthUserController() {
		return new AuthUserController(this);
	}


	public AuthUserService.Provider userServiceProvider() {
		return this;
	}

	@Override
	public AuthUserService authUserService() {
		return this.support.getAuthUserService();
	}

}
