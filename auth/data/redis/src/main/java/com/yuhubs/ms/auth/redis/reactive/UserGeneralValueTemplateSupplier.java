package com.yuhubs.ms.auth.redis.reactive;

import com.yuhubs.ms.auth.model.AuthUserGeneralValue;
import com.yuhubs.ms.redis.RedisTemplateSupplier;
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.util.function.Supplier;

class UserGeneralValueTemplateSupplier
		extends RedisTemplateSupplier<ReactiveRedisTemplate<String, AuthUserGeneralValue>> {

	UserGeneralValueTemplateSupplier(ReactiveRedisConnectionFactory connectionFactory) {
		super(new Helper(connectionFactory));
	}


	private static final class Helper implements Supplier<ReactiveRedisTemplate<String, AuthUserGeneralValue>> {

		private final ReactiveRedisConnectionFactory connectionFactory;

		Helper(ReactiveRedisConnectionFactory connectionFactory) {
			this.connectionFactory = connectionFactory;
		}


		@Override
		public ReactiveRedisTemplate<String, AuthUserGeneralValue> get() {
			return createJdkRedisTemplate();
		}

		ReactiveRedisTemplate<String, AuthUserGeneralValue> createJdkRedisTemplate() {
			return new ReactiveRedisTemplate<>(this.connectionFactory,
					createJdkSerializationContext()
			);
		}

		RedisSerializationContext<String, AuthUserGeneralValue> createJdkSerializationContext() {
			RedisSerializationContext.RedisSerializationContextBuilder<String, AuthUserGeneralValue> builder =
					RedisSerializationContext.newSerializationContext(RedisSerializer.java());

			builder.key(StringRedisSerializer.UTF_8);

			return builder.build();
		}

	}

}
