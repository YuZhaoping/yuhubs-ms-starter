package com.yuhubs.ms.auth.redis.reactive;

import com.yuhubs.ms.auth.redis.RedisAuthUserServiceBase;
import com.yuhubs.ms.redis.ReactiveRedisTemplateProvider;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.core.ReactiveStringRedisTemplate;

import java.util.function.Supplier;

public final class RedisAuthUserService extends RedisAuthUserServiceBase {

	private final Supplier<ReactiveStringRedisTemplate> stringTemplateSupplier;
	private final Supplier<ReactiveRedisTemplate<String, Object>> objectTemplateSupplier;


	public RedisAuthUserService(ReactiveRedisTemplateProvider templateProvider) {
		this.stringTemplateSupplier = templateProvider.stringRedisTemplateSupplier();
		this.objectTemplateSupplier = templateProvider.jdkRedisTemplateSupplier();
	}


	private ReactiveStringRedisTemplate stringTemplate() {
		return this.stringTemplateSupplier.get();
	}

	private ReactiveRedisTemplate<String, Object> objectTemplate() {
		return this.objectTemplateSupplier.get();
	}

}
