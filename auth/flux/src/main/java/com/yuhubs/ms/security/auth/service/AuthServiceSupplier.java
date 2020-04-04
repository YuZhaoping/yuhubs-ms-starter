package com.yuhubs.ms.security.auth.service;

import com.yuhubs.ms.security.auth.AuthSecurityContext;
import com.yuhubs.ms.security.auth.AuthUserService;
import com.yuhubs.ms.security.auth.event.AuthConfirmUrlsBuilder;
import com.yuhubs.ms.security.auth.event.AuthEventPublisher;

public final class AuthServiceSupplier {

	private final AuthSecurityContext securityContext;
	private final AuthUserService.Provider userServiceProvider;
	private final AuthEventPublisher eventPublisher;


	public AuthServiceSupplier(AuthSecurityContext context,
							   AuthUserService.Provider userServiceProvider,
							   AuthConfirmUrlsBuilder urlsBuilder) {
		this.securityContext = context;
		this.userServiceProvider = userServiceProvider;
		this.eventPublisher = new AuthEventPublisher(context, urlsBuilder);
	}


	public AuthSecurityContext authSecurityContext() {
		return this.securityContext;
	}

	public AuthUserService authUserService() {
		return this.userServiceProvider.authUserService();
	}

	public AuthEventPublisher authEventPublisher() {
		return this.eventPublisher;
	}

}
