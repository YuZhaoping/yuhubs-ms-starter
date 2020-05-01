package com.yuhubs.ms.auth.redis.service;

import com.yuhubs.ms.auth.model.AuthUserGeneralValue;
import com.yuhubs.ms.redis.RedisTemplateSupplier;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.util.function.Supplier;

class UserGeneralValueTemplateSupplier
		extends RedisTemplateSupplier<RedisTemplate<String, AuthUserGeneralValue>> {

	UserGeneralValueTemplateSupplier(RedisConnectionFactory connectionFactory) {
		super(new Helper(connectionFactory));
	}


	private static final class Helper
			implements Supplier<RedisTemplate<String, AuthUserGeneralValue>> {

		private final RedisConnectionFactory connectionFactory;

		Helper(RedisConnectionFactory connectionFactory) {
			this.connectionFactory = connectionFactory;
		}


		@Override
		public RedisTemplate<String, AuthUserGeneralValue> get() {
			return createJdkRedisTemplate();
		}

		private RedisTemplate<String, AuthUserGeneralValue> createJdkRedisTemplate() {
			RedisTemplate<String, AuthUserGeneralValue> template = new RedisTemplate();

			template.setConnectionFactory(this.connectionFactory);
			template.setKeySerializer(StringRedisSerializer.UTF_8);
			template.setValueSerializer(new JdkSerializationRedisSerializer());

			template.afterPropertiesSet();

			return template;
		}

	}

}
