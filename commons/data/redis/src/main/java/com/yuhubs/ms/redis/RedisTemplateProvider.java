package com.yuhubs.ms.redis;

import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.util.function.Supplier;

public class RedisTemplateProvider extends RedisTemplateProviderBase {

	public RedisTemplateProvider(LettuceConnectionManager connectionManager) {
		super(connectionManager);
	}


	public StringRedisTemplate createStringRedisTemplate() {
		return new StringRedisTemplate(redisConnectionFactory());
	}

	public Supplier<StringRedisTemplate> stringRedisTemplateSupplier() {
		return new RedisTemplateSupplier<>(this::createStringRedisTemplate);
	}

	public RedisTemplate<String, Object> createJsonRedisTemplate() {
		RedisTemplate<String, Object> template = new RedisTemplate();

		template.setConnectionFactory(redisConnectionFactory());
		template.setKeySerializer(StringRedisSerializer.UTF_8);
		template.setValueSerializer(new GenericJackson2JsonRedisSerializer(createJackson2ObjectMapper()));

		return template;
	}

	public Supplier<RedisTemplate<String, Object>> jsonRedisTemplateSupplier() {
		return new RedisTemplateSupplier<>(() -> {
			RedisTemplate<String, Object> template = this.createJsonRedisTemplate();
			template.afterPropertiesSet();
			return template;
		});
	}

	public RedisTemplate<String, Object> createJdkRedisTemplate() {
		RedisTemplate<String, Object> template = new RedisTemplate();

		template.setConnectionFactory(redisConnectionFactory());
		template.setKeySerializer(StringRedisSerializer.UTF_8);
		template.setValueSerializer(new JdkSerializationRedisSerializer());

		return template;
	}

	public Supplier<RedisTemplate<String, Object>> jdkRedisTemplateSupplier() {
		return new RedisTemplateSupplier<>(() -> {
			RedisTemplate<String, Object> template = this.createJdkRedisTemplate();
			template.afterPropertiesSet();
			return template;
		});
	}


	public RedisSequenceSupplier getRedisSequenceSupplier(String seqName, long initValue) {
		return new RedisSequenceSupplier(redisConnectionFactory(), seqName, initValue);
	}


	public final RedisConnectionFactory redisConnectionFactory() {
		return this.connectionManager.getConnectionFactory();
	}

}
