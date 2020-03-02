package com.yuhubs.ms.security.auth.web;

import com.yuhubs.ms.security.web.SecurityConfigurationSupport;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@EnableWebSecurity
@Configuration
public class AuthConfigurationSupport extends SecurityConfigurationSupport implements AuthApiEndpoints {

	public AuthConfigurationSupport() {
		super();
	}


	@Bean(name="authenticationManager")
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

}
