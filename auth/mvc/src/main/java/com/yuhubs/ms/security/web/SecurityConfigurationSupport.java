package com.yuhubs.ms.security.web;

import com.yuhubs.ms.security.JwtTokenServiceContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableWebSecurity
@Configuration
public class SecurityConfigurationSupport extends WebSecurityConfigurerAdapter {


	protected final JwtTokenServiceContext jwtTokenServiceContext;


	public SecurityConfigurationSupport() {
		this.jwtTokenServiceContext = new JwtTokenServiceContext();
	}


	@Bean
	public JwtTokenServiceContext jwtTokenServiceContext() {
		return this.jwtTokenServiceContext;
	}


	protected final JwtTokenServiceContext getJwtTokenServiceContext() {
		return this.jwtTokenServiceContext;
	}

}
