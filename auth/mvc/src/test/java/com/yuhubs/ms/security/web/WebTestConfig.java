package com.yuhubs.ms.security.web;

import com.yuhubs.ms.web.RestConfigurationSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WebTestConfig extends RestConfigurationSupport {

	@Autowired
	private SecurityHandlerSupplier securityHandlerSupplier;


	@Bean
	public TestController testController() {
		return new TestController(this.securityHandlerSupplier);
	}

}
