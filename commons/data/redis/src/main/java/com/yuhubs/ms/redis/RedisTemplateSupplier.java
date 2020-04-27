package com.yuhubs.ms.redis;

import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

public class RedisTemplateSupplier extends RedisTemplateSupplierBase {

	public RedisTemplateSupplier(LettuceConnectionManager connectionManager) {
		super(connectionManager);
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


	protected final RedisConnectionFactory redisConnectionFactory() {
		return this.connectionManager.getConnectionFactory();
	}

}
