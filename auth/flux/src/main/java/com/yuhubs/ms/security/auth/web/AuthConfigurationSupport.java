package com.yuhubs.ms.security.auth.web;

import com.yuhubs.ms.security.auth.AuthUserService;
import com.yuhubs.ms.security.auth.details.AuthUserDetailsService;
import com.yuhubs.ms.security.web.SecurityConfigurationSupport;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;

@EnableWebFluxSecurity
@Configuration
public abstract class AuthConfigurationSupport extends SecurityConfigurationSupport implements AuthApiEndpoints {

	private static final String SIGNOUT_SUCCESS_URL = SIGNIN_ENDPOINT + "?logout";

	private static final String VERIFY_EMAIL_PATTERN =
			SIGNUP_ENDPOINT + "/.*/verify_email/.*";

	private static final String RESET_PASSWORD_PATTERN =
			SIGNUP_ENDPOINT + "/.*/reset_password/.*";


	protected final AuthWebSecurityContext context;

	protected final ReactiveUserDetailsService userDetailsService;


	public AuthConfigurationSupport() {
		this.context = new AuthWebSecurityContext(this);
		this.userDetailsService = createUserDetailsService();
	}


	@Bean
	public AuthWebSecurityContext authSecurityContext() {
		return this.context;
	}

	@Bean
	public ReactiveUserDetailsService userDetailsService() {
		return this.userDetailsService;
	}


	protected abstract AuthUserService getAuthUserService();


	protected ReactiveUserDetailsService createUserDetailsService() {
		return new AuthUserDetailsService(this.context.userServiceProvider());
	}

}
