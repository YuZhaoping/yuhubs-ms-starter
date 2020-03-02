package com.yuhubs.ms.security.auth.web;

import com.yuhubs.ms.security.web.SecurityConfigurationSupport;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@EnableWebSecurity
@Configuration
public class AuthConfigurationSupport extends SecurityConfigurationSupport implements AuthApiEndpoints {


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

}
