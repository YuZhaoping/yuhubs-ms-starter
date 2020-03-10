package com.yuhubs.ms.security.auth.service;

import com.yuhubs.ms.security.auth.AuthSecurityContext;
import com.yuhubs.ms.security.auth.AuthUserService;
import com.yuhubs.ms.security.auth.event.AuthConfirmUrlsBuilder;

public final class AuthServiceSupplier {

	private final AuthSecurityContext securityContext;
	private final AuthUserService authUserService;
	private final AuthConfirmUrlsBuilder urlsBuilder;


	public AuthServiceSupplier(AuthSecurityContext context,
							   AuthUserService service,
							   AuthConfirmUrlsBuilder urlsBuilder) {
		this.securityContext = context;
		this.authUserService = service;
		this.urlsBuilder = urlsBuilder;
	}


	public AuthSecurityContext authSecurityContext() {
		return this.securityContext;
	}

	public AuthUserService authUserService() {
		return this.authUserService;
	}

	public AuthConfirmUrlsBuilder authConfirmUrlsBuilder() {
		return this.urlsBuilder;
	}

}
