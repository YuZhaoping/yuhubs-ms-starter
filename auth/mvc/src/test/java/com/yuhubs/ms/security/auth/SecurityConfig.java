package com.yuhubs.ms.security.auth;

import com.yuhubs.ms.auth.mock.MockAuthUserService;
import com.yuhubs.ms.security.auth.web.AuthConfigurationSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@EnableWebSecurity
@Configuration
public class SecurityConfig extends AuthConfigurationSupport {

	@Autowired
	private MockAuthUserService authUserService;


	@Override
	protected AuthUserService getAuthUserService() {
		return this.authUserService;
	}

}
