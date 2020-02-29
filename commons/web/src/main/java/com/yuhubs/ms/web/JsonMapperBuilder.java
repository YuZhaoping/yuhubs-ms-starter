package com.yuhubs.ms.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_ABSENT;
import static com.fasterxml.jackson.databind.SerializationFeature.WRITE_DATES_AS_TIMESTAMPS;

public final class JsonMapperBuilder {

	private JsonMapperBuilder() {
	}


	public static ObjectMapper buildJacksonMapper() {
		// Registers com.fasterxml.jackson.datatype.jsr310.JavaTimeModule automatically
		return Jackson2ObjectMapperBuilder
				.json()
				.indentOutput(true)
				.featuresToDisable(WRITE_DATES_AS_TIMESTAMPS)
				.serializationInclusion(NON_ABSENT)
				.build();
	}

}
