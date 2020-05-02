package com.yuhubs.ms.auth.redis.service;

import com.yuhubs.ms.auth.model.AuthUserGeneralValue;
import com.yuhubs.ms.auth.model.AuthUserProfileValue;
import com.yuhubs.ms.auth.redis.RedisAuthUserServiceBase;
import com.yuhubs.ms.redis.RedisSequence;
import com.yuhubs.ms.redis.RedisSequenceSupplier;
import com.yuhubs.ms.redis.RedisTemplateProvider;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.function.Supplier;

public final class RedisAuthUserService extends RedisAuthUserServiceBase {

	private final Supplier<StringRedisTemplate> stringTemplateSupplier;

	private final UserGeneralValueTemplateSupplier generalValueTemplateSupplier;
	private final UserProfileTemplateSupplier profileTemplateSupplier;

	private final RedisSequenceSupplier sequenceSupplier;


	public RedisAuthUserService(RedisTemplateProvider templateProvider) {
		this.stringTemplateSupplier = templateProvider.stringRedisTemplateSupplier();

		this.generalValueTemplateSupplier =
				new UserGeneralValueTemplateSupplier(templateProvider.redisConnectionFactory());
		this.profileTemplateSupplier =
				new UserProfileTemplateSupplier(templateProvider.redisConnectionFactory());

		this.sequenceSupplier = templateProvider.getRedisSequenceSupplier(
				USER_ID_SEQ_NAME,
				USER_ID_SEQ_INIT_VALUE
		);
	}


	private StringRedisTemplate stringTemplate() {
		return this.stringTemplateSupplier.get();
	}

	private RedisTemplate<String, AuthUserGeneralValue> generalValueTemplate() {
		return this.generalValueTemplateSupplier.get();
	}

	private RedisTemplate<String, AuthUserProfileValue> profileValueTemplate() {
		return this.profileTemplateSupplier.get();
	}

	private RedisSequence userIdSequence() {
		return this.sequenceSupplier.get();
	}

}
