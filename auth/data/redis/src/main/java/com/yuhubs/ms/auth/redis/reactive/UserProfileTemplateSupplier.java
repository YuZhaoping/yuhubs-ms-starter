package com.yuhubs.ms.auth.redis.reactive;

import com.yuhubs.ms.auth.model.AuthUserProfileValue;
import com.yuhubs.ms.redis.RedisTemplateSupplier;
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.util.function.Supplier;

class UserProfileTemplateSupplier
		extends RedisTemplateSupplier<ReactiveRedisTemplate<String, AuthUserProfileValue>> {

	UserProfileTemplateSupplier(ReactiveRedisConnectionFactory connectionFactory) {
		super(new Helper(connectionFactory));
	}


	private static final class Helper implements Supplier<ReactiveRedisTemplate<String, AuthUserProfileValue>> {

		private final ReactiveRedisConnectionFactory connectionFactory;

		Helper(ReactiveRedisConnectionFactory connectionFactory) {
			this.connectionFactory = connectionFactory;
		}


		@Override
		public ReactiveRedisTemplate<String, AuthUserProfileValue> get() {
			return createJdkRedisTemplate();
		}

		ReactiveRedisTemplate<String, AuthUserProfileValue> createJdkRedisTemplate() {
			return new ReactiveRedisTemplate<>(this.connectionFactory,
					createJdkSerializationContext()
			);
		}

		RedisSerializationContext<String, AuthUserProfileValue> createJdkSerializationContext() {
			RedisSerializationContext.RedisSerializationContextBuilder<String, AuthUserProfileValue> builder =
					RedisSerializationContext.newSerializationContext(RedisSerializer.java());

			builder.key(StringRedisSerializer.UTF_8);

			return builder.build();
		}

	}

}
