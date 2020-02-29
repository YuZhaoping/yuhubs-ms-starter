package com.yuhubs.ms.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.http.codec.json.Jackson2JsonDecoder;
import org.springframework.http.codec.json.Jackson2JsonEncoder;
import org.springframework.web.reactive.accept.RequestedContentTypeResolverBuilder;
import org.springframework.web.reactive.config.WebFluxConfigurationSupport;

@Configuration
public class RestConfigurationSupport extends WebFluxConfigurationSupport {

	private ObjectMapper objectMapper;

	protected final RestExceptionHandler restExceptionHandler;


	public RestConfigurationSupport() {
		this.restExceptionHandler = createRestExceptionHandler();
	}


	@Bean
	public ObjectMapper objectMapper() {
		return getObjectMapper();
	}


	@Override
	protected void configureContentTypeResolver(RequestedContentTypeResolverBuilder builder) {
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


	protected RestExceptionHandler createRestExceptionHandler() {
		return new RestExceptionHandler();
	}


	public static ObjectMapper createObjectMapper() {
		return JsonMapperBuilder.buildJacksonMapper();
	}


	protected final <T> T lookup(String beanName) {
		return (T) getApplicationContext().getBean(beanName);
	}

}
