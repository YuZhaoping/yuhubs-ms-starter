package com.yuhubs.ms.security.auth.service;

import com.yuhubs.ms.security.auth.AuthSecurityContext;
import com.yuhubs.ms.security.auth.AuthUserService;
import com.yuhubs.ms.security.auth.event.AuthConfirmUrlsBuilder;
import com.yuhubs.ms.security.auth.event.AuthEventPublisher;

public final class AuthServiceSupplier {

	private final AuthSecurityContext securityContext;
	private final AuthUserService authUserService;
	private final AuthEventPublisher eventPublisher;


	public AuthServiceSupplier(AuthSecurityContext context,
							   AuthUserService service,
							   AuthConfirmUrlsBuilder urlsBuilder) {
		this.securityContext = context;
		this.authUserService = service;
		this.eventPublisher = new AuthEventPublisher(context, urlsBuilder);
	}


	public AuthSecurityContext authSecurityContext() {
		return this.securityContext;
	}

	public AuthUserService authUserService() {
		return this.authUserService;
	}

	public AuthEventPublisher authEventPublisher() {
		return this.eventPublisher;
	}

}
