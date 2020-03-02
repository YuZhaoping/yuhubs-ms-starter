package com.yuhubs.ms.security.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yuhubs.ms.security.JwtTokenServiceContext;
import com.yuhubs.ms.security.SecurityProperties;
import com.yuhubs.ms.security.token.JwtTokenService;
import com.yuhubs.ms.security.web.token.JwtAuthenticationFilter;
import com.yuhubs.ms.security.web.token.JwtAuthenticationProvider;
import com.yuhubs.ms.web.JsonMapperBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutFilter;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@EnableWebSecurity
@Configuration
public class SecurityConfigurationSupport extends WebSecurityConfigurerAdapter {

	protected final JwtTokenServiceContext jwtTokenServiceContext;

	protected final SecurityHandlerSupplier handlerSupplier;


	public SecurityConfigurationSupport() {
		this.jwtTokenServiceContext = createJwtTokenServiceContext();
		this.handlerSupplier = createSecurityHandlerSupplier();
	}


	@Bean
	public JwtTokenServiceContext jwtTokenServiceContext() {
		return this.jwtTokenServiceContext;
	}

	@Bean
	public SecurityProperties securityProperties() {
		return this.jwtTokenServiceContext.securityProperties();
	}

	@Bean
	public JwtTokenService jwtTokenService(SecurityProperties properties) {
		return this.jwtTokenServiceContext.jwtTokenService(properties);
	}

	@Bean
	public SecurityHandlerSupplier securityHandlerSupplier() {
		return this.handlerSupplier;
	}

	@Bean
	public AuthenticationEntryPoint unauthorizedEntryPoint(SecurityHandlerSupplier supplier) {
		return supplier.unauthorizedEntryPoint();
	}

	@Bean
	public AccessDeniedHandler accessDeniedHandler(SecurityHandlerSupplier supplier) {
		return supplier.accessDeniedHandler();
	}

	@Bean
	public AuthenticationSuccessHandler authenticationSuccessHandler(SecurityHandlerSupplier supplier) {
		return supplier.authenticationSuccessHandler();
	}

	@Bean
	public AuthenticationFailureHandler authenticationFailureHandler(SecurityHandlerSupplier supplier) {
		return supplier.authenticationFailureHandler();
	}


	@Override
	protected final void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(new JwtAuthenticationProvider());
		configureAuthenticationManager(auth);
	}

	protected void configureAuthenticationManager(AuthenticationManagerBuilder auth)
			throws Exception {
	}


	@Override
	protected final void configure(HttpSecurity http) throws Exception {
		final SecurityHandlerSupplier supplier = this.handlerSupplier;

		http.csrf().disable()
				.exceptionHandling()
				.authenticationEntryPoint(supplier.unauthorizedEntryPoint())
				.accessDeniedHandler(supplier.accessDeniedHandler())
				.and()
				.sessionManagement().sessionCreationPolicy(STATELESS);

		configureRequestAuthorization(http);

		// JwtAuthenticationFilter must precede LogoutFilter,
		// otherwise LogoutHandler wouldn't know who logs out.
		http.addFilterBefore(newJwtAuthenticationFilter(), LogoutFilter.class);

		configureFilters(http);
	}

	private final JwtAuthenticationFilter newJwtAuthenticationFilter() {
		return new JwtAuthenticationFilter(this.handlerSupplier);
	}


	protected void configureRequestAuthorization(HttpSecurity http) throws Exception {
		http.authorizeRequests()
				.antMatchers("/").permitAll()
				.anyRequest().authenticated();
	}

	protected void configureFilters(HttpSecurity http) throws Exception {
	}


	protected JwtTokenServiceContext createJwtTokenServiceContext() {
		return new JwtTokenServiceContext();
	}

	public final JwtTokenServiceContext getJwtTokenServiceContext() {
		return this.jwtTokenServiceContext;
	}


	protected SecurityHandlerSupplier createSecurityHandlerSupplier(ObjectMapper objectMapper) {
		return new SecurityHandlerSupplier(this, objectMapper);
	}

	protected ObjectMapper createObjectMapper() {
		return JsonMapperBuilder.buildJacksonMapper();
	}

	private final SecurityHandlerSupplier createSecurityHandlerSupplier() {
		return createSecurityHandlerSupplier(createObjectMapper());
	}

}
