package com.yuhubs.ms.security.auth.web;

import com.yuhubs.ms.security.auth.AuthUserService;
import com.yuhubs.ms.security.auth.details.AuthUserDetailsService;
import com.yuhubs.ms.security.auth.service.NoOpAuthUserService;
import com.yuhubs.ms.security.auth.web.login.LoginAuthenticationConverter;
import com.yuhubs.ms.security.auth.web.login.LoginAuthenticationManager;
import com.yuhubs.ms.security.web.SecurityConfigurationSupport;
import com.yuhubs.ms.security.web.SecurityHandlerSupplier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.web.server.authentication.AuthenticationWebFilter;
import org.springframework.security.web.server.authentication.logout.RedirectServerLogoutSuccessHandler;
import org.springframework.security.web.server.authentication.logout.SecurityContextServerLogoutHandler;
import org.springframework.security.web.server.util.matcher.ServerWebExchangeMatchers;

import java.net.URI;

@EnableWebFluxSecurity
@Configuration
public class AuthConfigurationSupport extends SecurityConfigurationSupport implements AuthApiEndpoints {

	private static final String SIGNOUT_SUCCESS_URL = SIGNIN_ENDPOINT + "?logout";

	private static final String VERIFY_EMAIL_PATTERN =
			SIGNUP_ENDPOINT + "/*/verify_email/*";

	private static final String RESET_PASSWORD_PATTERN =
			SIGNUP_ENDPOINT + "/*/reset_password/*";


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


	@Override
	protected void configureRequestAuthorization(ServerHttpSecurity http) {
		http.authorizeExchange()
				.pathMatchers(SIGNOUT_ENDPOINT).permitAll()
				.pathMatchers(HttpMethod.POST, SIGNIN_ENDPOINT).permitAll()
				.pathMatchers(HttpMethod.POST, SIGNUP_ENDPOINT).permitAll()
				.pathMatchers(HttpMethod.PUT, RESET_PASSWORD_ENDPOINT).permitAll()
				.pathMatchers(VERIFY_EMAIL_PATTERN).permitAll()
				.pathMatchers(RESET_PASSWORD_PATTERN).permitAll();

		super.configureRequestAuthorization(http);
	}

	@Override
	protected void configureFilters(ServerHttpSecurity http) {
		super.configureFilters(http);
		setupLogout(http);
		setupLoginFilter(http);
	}


	protected AuthUserService getAuthUserService() {
		return NoOpAuthUserService.getInstance();
	}


	protected ReactiveUserDetailsService createUserDetailsService() {
		return new AuthUserDetailsService(this.context.userServiceProvider());
	}


	protected final void setupLogout(ServerHttpSecurity http) {
		final SecurityContextServerLogoutHandler logoutHandler =
				new SecurityContextServerLogoutHandler();
		logoutHandler.setSecurityContextRepository(this.securityContextRepository);

		final RedirectServerLogoutSuccessHandler logoutSuccessHandler =
				new RedirectServerLogoutSuccessHandler();
		logoutSuccessHandler.setLogoutSuccessUrl(URI.create(SIGNOUT_SUCCESS_URL));

		http.logout()
				.requiresLogout(ServerWebExchangeMatchers.pathMatchers(SIGNOUT_ENDPOINT))
				.logoutHandler(logoutHandler)
				.logoutSuccessHandler(logoutSuccessHandler);
	}

	protected final void setupLoginFilter(ServerHttpSecurity http) {
		AuthenticationWebFilter loginFilter = createLoginFilter();

		http.addFilterAt(loginFilter, SecurityWebFiltersOrder.FORM_LOGIN);
	}

	private final AuthenticationWebFilter createLoginFilter() {
		final SecurityHandlerSupplier supplier = this.handlerSupplier;

		final LoginAuthenticationManager authenticationManager =
				new LoginAuthenticationManager(this.context, this.userDetailsService);

		final AuthenticationWebFilter loginFilter =
				new AuthenticationWebFilter(authenticationManager);

		final LoginAuthenticationConverter authenticationConverter =
				new LoginAuthenticationConverter(supplier.objectMapper());

		loginFilter.setServerAuthenticationConverter(authenticationConverter);

		loginFilter.setRequiresAuthenticationMatcher(
				ServerWebExchangeMatchers.pathMatchers(SIGNIN_ENDPOINT)
		);

		loginFilter.setAuthenticationSuccessHandler(
				supplier.authenticationSuccessHandler()
		);

		loginFilter.setAuthenticationFailureHandler(
				supplier.authenticationFailureHandler()
		);

		return loginFilter;
	}

}
