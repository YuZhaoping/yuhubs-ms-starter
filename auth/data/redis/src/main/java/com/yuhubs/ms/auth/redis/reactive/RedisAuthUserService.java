package com.yuhubs.ms.auth.redis.reactive;

import com.yuhubs.ms.auth.model.AuthUserGeneralValue;
import com.yuhubs.ms.auth.model.AuthUserProfileValue;
import com.yuhubs.ms.auth.redis.RedisAuthUserServiceBase;
import com.yuhubs.ms.redis.ReactiveRedisTemplateProvider;
import com.yuhubs.ms.redis.RedisSequence;
import com.yuhubs.ms.redis.RedisSequenceSupplier;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.core.ReactiveStringRedisTemplate;

import java.util.function.Supplier;

public final class RedisAuthUserService extends RedisAuthUserServiceBase {

	private final Supplier<ReactiveStringRedisTemplate> stringTemplateSupplier;

	private final UserGeneralValueTemplateSupplier generalValueTemplateSupplier;
	private final UserProfileTemplateSupplier profileTemplateSupplier;

	private final RedisSequenceSupplier sequenceSupplier;


	public RedisAuthUserService(ReactiveRedisTemplateProvider templateProvider) {
		this.stringTemplateSupplier = templateProvider.stringRedisTemplateSupplier();

		this.generalValueTemplateSupplier =
				new UserGeneralValueTemplateSupplier(templateProvider.reactiveRedisConnectionFactory());
		this.profileTemplateSupplier =
				new UserProfileTemplateSupplier(templateProvider.reactiveRedisConnectionFactory());

		this.sequenceSupplier = templateProvider.getRedisSequenceSupplier(
				USER_ID_SEQ_NAME,
				USER_ID_SEQ_INIT_VALUE
		);
	}


	private ReactiveStringRedisTemplate stringTemplate() {
		return this.stringTemplateSupplier.get();
	}

	private ReactiveRedisTemplate<String, AuthUserGeneralValue> generalValueTemplate() {
		return this.generalValueTemplateSupplier.get();
	}

	private ReactiveRedisTemplate<String, AuthUserProfileValue> profileValueTemplate() {
		return this.profileTemplateSupplier.get();
	}

	private RedisSequence userIdSequence() {
		return this.sequenceSupplier.get();
	}

}
