package com.yuhubs.ms.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.autoconfigure.web.ResourceProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.MediaType;
import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.http.codec.json.Jackson2JsonDecoder;
import org.springframework.http.codec.json.Jackson2JsonEncoder;
import org.springframework.web.reactive.accept.RequestedContentTypeResolverBuilder;
import org.springframework.web.reactive.config.WebFluxConfigurationSupport;

@Configuration
public class RestConfigurationSupport extends WebFluxConfigurationSupport {

	protected final RestExceptionHandler restExceptionHandler;

	private ObjectMapper objectMapper;


	public RestConfigurationSupport() {
		this.restExceptionHandler = createRestExceptionHandler();
		this.restExceptionHandler.init();
	}


	@Bean
	public GlobalErrorAttributes globalErrorAttributes() {
		return new GlobalErrorAttributes(this.restExceptionHandler);
	}

	@Bean
	@Order(-2)
	public GlobalErrorWebExceptionHandler globalErrorWebExceptionHandler(
			GlobalErrorAttributes globalErrorAttributes, ServerCodecConfigurer serverCodecConfigurer) {
		return new GlobalErrorWebExceptionHandler(
				globalErrorAttributes,
				getResourceProperties(),
				getApplicationContext(),
				serverCodecConfigurer
		);
	}

	@Bean
	public ObjectMapper objectMapper() {
		return getObjectMapper();
	}


	protected RestExceptionHandler createRestExceptionHandler() {
		return new RestExceptionHandler();
	}

	protected ResourceProperties getResourceProperties() {
		return new ResourceProperties();
	}


	@Override
	protected void configureContentTypeResolver(RequestedContentTypeResolverBuilder builder) {
		builder.headerResolver();
		builder.fixedResolver(MediaType.APPLICATION_JSON);
	}

	@Override
	protected void configureHttpMessageCodecs(ServerCodecConfigurer configurer) {
		ObjectMapper objectMapper = getObjectMapper();
		Jackson2JsonEncoder encoder = new Jackson2JsonEncoder(objectMapper);
		Jackson2JsonDecoder decoder = new Jackson2JsonDecoder(objectMapper);
		configurer.defaultCodecs().jackson2JsonEncoder(encoder);
		configurer.defaultCodecs().jackson2JsonDecoder(decoder);
	}


	protected final ObjectMapper getObjectMapper() {
		if (this.objectMapper == null) {
			this.objectMapper = customizeObjectMapper(createObjectMapper());
		}
		return this.objectMapper;
	}

	protected ObjectMapper customizeObjectMapper(ObjectMapper objectMapper) {
		return objectMapper;
	}


	public static ObjectMapper createObjectMapper() {
		return JsonMapperBuilder.buildJacksonMapper();
	}


	protected final <T> T lookup(String beanName) {
		return (T) getApplicationContext().getBean(beanName);
	}

}
