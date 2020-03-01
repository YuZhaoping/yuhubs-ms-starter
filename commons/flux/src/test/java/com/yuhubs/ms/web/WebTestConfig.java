package com.yuhubs.ms.web;

import com.yuhubs.ms.web.tests.DefaultHandler;
import com.yuhubs.ms.web.tests.DefaultExceptionHandler;
import com.yuhubs.ms.web.tests.MockErrorController;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WebTestConfig extends RestConfigurationSupport {

	@Override
	protected RestExceptionHandler createRestExceptionHandler() {
		return new DefaultExceptionHandler();
	}


	@Bean
	public Object defaultHandler() {
		return new DefaultHandler();
	}

	@Bean
	public Object mockErrorController() {
		return new MockErrorController();
	}

}
