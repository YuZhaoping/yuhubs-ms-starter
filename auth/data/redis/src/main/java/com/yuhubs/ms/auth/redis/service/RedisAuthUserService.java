package com.yuhubs.ms.auth.redis.service;

import com.yuhubs.ms.auth.redis.RedisAuthUserServiceBase;
import com.yuhubs.ms.redis.RedisTemplateProvider;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.function.Supplier;

public final class RedisAuthUserService extends RedisAuthUserServiceBase {

	private final Supplier<StringRedisTemplate> stringTemplateSupplier;
	private final Supplier<RedisTemplate<String, Object>> objectTemplateSupplier;


	public RedisAuthUserService(RedisTemplateProvider templateProvider) {
		this.stringTemplateSupplier = templateProvider.stringRedisTemplateSupplier();
		this.objectTemplateSupplier = templateProvider.jdkRedisTemplateSupplier();
	}


	private StringRedisTemplate stringTemplate() {
		return this.stringTemplateSupplier.get();
	}

	private RedisTemplate<String, Object> objectTemplate() {
		return this.objectTemplateSupplier.get();
	}

}
