package com.yuhubs.ms.security.auth.web;

import com.yuhubs.ms.security.web.SecurityConfigurationSupport;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import static org.springframework.http.HttpMethod.POST;
import static org.springframework.http.HttpMethod.PUT;

@EnableWebSecurity
@Configuration
public class AuthConfigurationSupport extends SecurityConfigurationSupport implements AuthApiEndpoints {

	private static final String SIGNOUT_SUCCESS_URL = SIGNIN_ENDPOINT + "?logout";

	private static final String VERIFY_EMAIL_PATTERN =
			SIGNUP_ENDPOINT + "/.*/verify_email/.*";

	private static final String RESET_PASSWORD_PATTERN =
			SIGNUP_ENDPOINT + "/.*/reset_password/.*";


	protected final AuthWebSecurityContext context;


	public AuthConfigurationSupport() {
		super();
		this.context = new AuthWebSecurityContext(this);
	}


	@Bean(name="authenticationManager")
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}


	@Bean
	public AuthWebSecurityContext authWebSecurityContext() {
		return this.context;
	}


	@Override
	protected void configureRequestAuthorization(HttpSecurity http) throws Exception {
		http.logout()
				.logoutUrl(SIGNOUT_ENDPOINT)
				.logoutSuccessUrl(SIGNOUT_SUCCESS_URL)
				.permitAll();

		http.authorizeRequests()
				.antMatchers(POST, SIGNIN_ENDPOINT).permitAll()
				.antMatchers(POST, SIGNUP_ENDPOINT).permitAll()
				.antMatchers(PUT, RESET_PASSWORD_ENDPOINT).permitAll()
				.regexMatchers(VERIFY_EMAIL_PATTERN).permitAll()
				.regexMatchers(RESET_PASSWORD_PATTERN).permitAll();

		super.configureRequestAuthorization(http);
	}


}
