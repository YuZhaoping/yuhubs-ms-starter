package com.yuhubs.ms.security.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yuhubs.ms.security.JwtTokenServiceContext;
import com.yuhubs.ms.web.JsonMapperBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableWebSecurity
@Configuration
public class SecurityConfigurationSupport extends WebSecurityConfigurerAdapter {

	protected final JwtTokenServiceContext jwtTokenServiceContext;

	protected final SecurityHandlerSupplier handlerSupplier;


	public SecurityConfigurationSupport() {
		this.jwtTokenServiceContext = new JwtTokenServiceContext();
		this.handlerSupplier = createSecurityHandlerSupplier();
	}


	@Bean
	public JwtTokenServiceContext jwtTokenServiceContext() {
		return this.jwtTokenServiceContext;
	}

	@Bean
	public SecurityHandlerSupplier securityHandlerSupplier() {
		return this.handlerSupplier;
	}


	protected final JwtTokenServiceContext getJwtTokenServiceContext() {
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
