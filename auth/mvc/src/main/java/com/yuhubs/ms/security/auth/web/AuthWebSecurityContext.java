package com.yuhubs.ms.security.auth.web;

import com.yuhubs.ms.security.auth.AuthSecurityContext;

public class AuthWebSecurityContext extends AuthSecurityContext {

	private final AuthConfigurationSupport configurationSupport;


	AuthWebSecurityContext(AuthConfigurationSupport support) {
		super(support.getJwtTokenServiceContext());
		this.configurationSupport = support;
	}

}
