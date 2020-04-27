package com.yuhubs.ms.redis;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.core.ReactiveStringRedisTemplate;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

public class RedisTemplateSupplier {

	protected final LettuceConnectionManager connectionManager;


	public RedisTemplateSupplier(LettuceConnectionManager connectionManager) {
		this.connectionManager = connectionManager;
	}


	public ReactiveStringRedisTemplate createReactiveStringRedisTemplate() {
		return new ReactiveStringRedisTemplate(reactiveRedisConnectionFactory(),
				createStringSerializationContext()
		);
	}

	public ReactiveRedisTemplate<String, Object> createReactiveJsonRedisTemplate() {
		return new ReactiveRedisTemplate<>(reactiveRedisConnectionFactory(),
				createJsonSerializationContext()
		);
	}

	public ReactiveRedisTemplate<String, Object> createReactiveJdkRedisTemplate() {
		return new ReactiveRedisTemplate<>(reactiveRedisConnectionFactory(),
				createJdkSerializationContext()
		);
	}


	public StringRedisTemplate createStringRedisTemplate() {
		return new StringRedisTemplate(redisConnectionFactory());
	}

	public RedisTemplate<String, Object> createJsonRedisTemplate() {
		RedisTemplate<String, Object> template = new RedisTemplate();

		template.setConnectionFactory(redisConnectionFactory());
		template.setKeySerializer(StringRedisSerializer.UTF_8);
		template.setValueSerializer(new GenericJackson2JsonRedisSerializer(createJackson2ObjectMapper()));

		return template;
	}

	public RedisTemplate<String, Object> createJdkRedisTemplate() {
		RedisTemplate<String, Object> template = new RedisTemplate();

		template.setConnectionFactory(redisConnectionFactory());
		template.setKeySerializer(StringRedisSerializer.UTF_8);
		template.setValueSerializer(new JdkSerializationRedisSerializer());

		return template;
	}


	protected RedisSerializationContext<String, String> createStringSerializationContext() {
		return RedisSerializationContext.string();
	}

	protected RedisSerializationContext<String, Object> createJdkSerializationContext() {
		RedisSerializationContext.RedisSerializationContextBuilder<String, Object> builder =
				RedisSerializationContext.newSerializationContext();

		builder.key(StringRedisSerializer.UTF_8).value(new JdkSerializationRedisSerializer());

		return builder.build();
	}

	protected RedisSerializationContext<String, Object> createJsonSerializationContext() {
		RedisSerializationContext.RedisSerializationContextBuilder<String, Object> builder =
				RedisSerializationContext.newSerializationContext();

		builder.key(StringRedisSerializer.UTF_8)
				.value(new GenericJackson2JsonRedisSerializer(createJackson2ObjectMapper()));

		return builder.build();
	}

	protected ObjectMapper createJackson2ObjectMapper() {
		ObjectMapper objectMapper = new ObjectMapper();

		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		objectMapper.configure(DeserializationFeature.FAIL_ON_NULL_FOR_PRIMITIVES, false);
		objectMapper.configure(DeserializationFeature.FAIL_ON_NUMBERS_FOR_ENUMS, false);

		objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
		objectMapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);

		return objectMapper;
	}


	protected final RedisConnectionFactory redisConnectionFactory() {
		return this.connectionManager.getConnectionFactory();
	}

	protected final ReactiveRedisConnectionFactory reactiveRedisConnectionFactory() {
		return this.connectionManager.getReactiveConnectionFactory();
	}

}
