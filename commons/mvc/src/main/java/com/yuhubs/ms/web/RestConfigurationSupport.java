package com.yuhubs.ms.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import java.util.List;

@Configuration
public class RestConfigurationSupport extends WebMvcConfigurationSupport {

	private ObjectMapper objectMapper;


	@Bean
	public ObjectMapper objectMapper() {
		return getObjectMapper();
	}


	@Override
	protected void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
		configurer.favorPathExtension(false);
		configurer.favorParameter(false);
		configurer.defaultContentType(MediaType.APPLICATION_JSON);
	}

	@Override
	protected void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
		ObjectMapper objectMapper = getObjectMapper();
		converters.add(new MappingJackson2HttpMessageConverter(objectMapper));
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
