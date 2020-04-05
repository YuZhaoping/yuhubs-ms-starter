package com.yuhubs.ms.security.auth.service;

import com.yuhubs.ms.security.auth.AuthSecurityContext;
import com.yuhubs.ms.security.auth.AuthUserService;
import com.yuhubs.ms.security.auth.event.AuthConfirmUrlsBuilder;
import com.yuhubs.ms.security.auth.event.AuthEventPublisher;

public abstract class AuthServiceBase {

	protected final AuthServiceSupplier supplier;


	public AuthServiceBase(AuthServiceSupplier supplier) {
		this.supplier = supplier;
	}


	protected final AuthSecurityContext authSecurityContext() {
		return this.supplier.authSecurityContext();
	}

	protected final AuthUserService authUserService() {
		return this.supplier.authUserService();
	}

	protected final AuthEventPublisher authEventPublisher() {
		return this.supplier.authEventPublisher();
	}

	protected final AuthConfirmUrlsBuilder authUrlsBuilder() {
		return authEventPublisher().authConfirmUrlsBuilder();
	}

}
