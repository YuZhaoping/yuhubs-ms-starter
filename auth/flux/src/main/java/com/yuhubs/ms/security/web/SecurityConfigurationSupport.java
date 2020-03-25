package com.yuhubs.ms.security.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yuhubs.ms.security.SecurityProperties;
import com.yuhubs.ms.security.jwt.JwtTokenService;
import com.yuhubs.ms.security.jwt.JwtTokenServiceContext;
import com.yuhubs.ms.web.JsonMapperBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.ServerAuthenticationEntryPoint;
import org.springframework.security.web.server.authentication.ServerAuthenticationFailureHandler;
import org.springframework.security.web.server.authentication.ServerAuthenticationSuccessHandler;
import org.springframework.security.web.server.authorization.ServerAccessDeniedHandler;
import org.springframework.security.web.server.context.NoOpServerSecurityContextRepository;

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
				.securityContextRepository(NoOpServerSecurityContextRepository.getInstance());
		return http;
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
