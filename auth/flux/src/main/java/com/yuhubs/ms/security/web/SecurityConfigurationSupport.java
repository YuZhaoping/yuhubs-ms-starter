package com.yuhubs.ms.security.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yuhubs.ms.security.SecurityProperties;
import com.yuhubs.ms.security.jwt.JwtTokenService;
import com.yuhubs.ms.security.jwt.JwtTokenServiceContext;
import com.yuhubs.ms.security.web.jwt.JwtAuthenticationManager;
import com.yuhubs.ms.security.web.jwt.JwtSecurityContextRepository;
import com.yuhubs.ms.web.JsonMapperBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.DelegatingReactiveAuthenticationManager;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.ServerAuthenticationEntryPoint;
import org.springframework.security.web.server.authentication.ServerAuthenticationFailureHandler;
import org.springframework.security.web.server.authentication.ServerAuthenticationSuccessHandler;
import org.springframework.security.web.server.authorization.ServerAccessDeniedHandler;

import java.util.LinkedList;
import java.util.List;

@EnableWebFluxSecurity
@Configuration
public class SecurityConfigurationSupport {

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
	public ServerAuthenticationEntryPoint unauthorizedEntryPoint(SecurityHandlerSupplier supplier) {
		return supplier.unauthorizedEntryPoint();
	}

	@Bean
	public ServerAccessDeniedHandler accessDeniedHandler(SecurityHandlerSupplier supplier) {
		return supplier.accessDeniedHandler();
	}

	@Bean
	public ServerAuthenticationSuccessHandler authenticationSuccessHandler(SecurityHandlerSupplier supplier) {
		return supplier.authenticationSuccessHandler();
	}

	@Bean
	public ServerAuthenticationFailureHandler authenticationFailureHandler(SecurityHandlerSupplier supplier) {
		return supplier.authenticationFailureHandler();
	}


	@Bean
	public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
		return configure(http).build();
	}

	protected final ServerHttpSecurity configure(ServerHttpSecurity http) {
		http.csrf().disable()
				.exceptionHandling()
				.authenticationEntryPoint(this.handlerSupplier.unauthorizedEntryPoint())
				.accessDeniedHandler(this.handlerSupplier.accessDeniedHandler())
				.and()
				.authenticationManager(configureAuthenticationManager())
				.securityContextRepository(createSecurityContextRepository());

		configureRequestAuthorization(http);

		configureFilters(http);

		return http;
	}

	private final ReactiveAuthenticationManager configureAuthenticationManager() {
		List<ReactiveAuthenticationManager> entryPoints = new LinkedList<>();

		entryPoints.add(new JwtAuthenticationManager());

		configureAuthenticationManager(entryPoints);

		return new DelegatingReactiveAuthenticationManager(entryPoints);
	}

	private final JwtSecurityContextRepository createSecurityContextRepository() {
		return new JwtSecurityContextRepository(this.handlerSupplier);
	}


	protected void configureAuthenticationManager(List<ReactiveAuthenticationManager> entryPoints) {
	}

	protected void configureRequestAuthorization(ServerHttpSecurity http) {
		http.authorizeExchange()
				.pathMatchers("/").permitAll()
				.anyExchange().authenticated();
	}

	protected void configureFilters(ServerHttpSecurity http) {
	}


	protected JwtTokenServiceContext createJwtTokenServiceContext() {
		return new JwtTokenServiceContext();
	}

	public final JwtTokenServiceContext getJwtTokenServiceContext() {
		return this.jwtTokenServiceContext;
	}


	public final SecurityHandlerSupplier getSecurityHandlerSupplier() {
		return this.handlerSupplier;
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
