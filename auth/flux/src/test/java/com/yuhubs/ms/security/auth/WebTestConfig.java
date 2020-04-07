package com.yuhubs.ms.security.auth;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WebTestConfig extends AuthWebConfigurationSupport {

	@Bean
	public MockController mockController() {
		return new MockController();
	}

}
