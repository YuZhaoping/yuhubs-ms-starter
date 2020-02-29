package com.yuhubs.ms.web;

import com.yuhubs.ms.web.tests.DefaultController;
import com.yuhubs.ms.web.tests.DefaultExceptionHandler;
import com.yuhubs.ms.web.tests.MockErrorController;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WebTestConfig extends RestConfigurationSupport {

	@Bean
	public Object controllerAdvice() {
		return new DefaultExceptionHandler();
	}

	@Bean
	public Object defaultHandler() {
		return new DefaultController();
	}


	@Bean
	public Object mockErrorController() {
		return new MockErrorController();
	}

}
