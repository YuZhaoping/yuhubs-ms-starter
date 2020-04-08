package com.yuhubs.ms.security.auth.web;

import com.yuhubs.ms.security.auth.AuthUserService;
import com.yuhubs.ms.security.auth.details.AuthUserDetailsService;
import com.yuhubs.ms.security.auth.service.NoOpAuthUserService;
import com.yuhubs.ms.security.auth.web.login.LoginAuthenticationProvider;
import com.yuhubs.ms.security.auth.web.login.LoginProcessingFilter;
import com.yuhubs.ms.security.web.SecurityConfigurationSupport;
import com.yuhubs.ms.security.web.SecurityHandlerSupplier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

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

	protected final UserDetailsService userDetailsService;


	public AuthConfigurationSupport() {
		super();
		this.context = new AuthWebSecurityContext(this);
		this.userDetailsService = createUserDetailsService();
	}


	@Bean
	public AuthWebSecurityContext authSecurityContext() {
		return this.context;
	}

	@Bean
	public UserDetailsService userDetailsService() {
		return this.userDetailsService;
	}


	@Bean(name="authenticationManager")
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
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

	@Override
	protected void configureAuthenticationManager(AuthenticationManagerBuilder auth) throws Exception {
		super.configureAuthenticationManager(auth);
		setupLoginProvider(auth, this.userDetailsService);
	}

	@Override
	protected void configureFilters(HttpSecurity http) throws Exception {
		super.configureFilters(http);
		setupLoginFilter(http);
	}


	protected AuthUserService getAuthUserService() {
		return NoOpAuthUserService.getInstance();
	}


	protected UserDetailsService createUserDetailsService() {
		return new AuthUserDetailsService(this.context.userServiceProvider());
	}


	protected final void setupLoginProvider(AuthenticationManagerBuilder auth,
											UserDetailsService userDetailsService) {
		AuthenticationProvider loginProvider =
				new LoginAuthenticationProvider(this.context, userDetailsService);

		auth.authenticationProvider(loginProvider);
	}


	protected final void setupLoginFilter(HttpSecurity http) throws Exception {
		LoginProcessingFilter loginFilter = createLoginFilter();

		http.addFilterBefore(loginFilter, UsernamePasswordAuthenticationFilter.class);
	}

	private final LoginProcessingFilter createLoginFilter() throws Exception {
		final SecurityHandlerSupplier supplier = this.handlerSupplier;

		LoginProcessingFilter loginFilter =
				new LoginProcessingFilter(SIGNIN_ENDPOINT, supplier.objectMapper());

		AuthenticationManager authenticationManager =
				lookup("authenticationManager");

		loginFilter.setAuthenticationManager(authenticationManager);

		loginFilter.setAuthenticationSuccessHandler(
				supplier.authenticationSuccessHandler());

		loginFilter.setAuthenticationFailureHandler(
				supplier.authenticationFailureHandler());

		return loginFilter;
	}

}
