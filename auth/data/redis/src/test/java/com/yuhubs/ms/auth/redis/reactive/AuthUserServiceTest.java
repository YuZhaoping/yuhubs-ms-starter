package com.yuhubs.ms.auth.redis.reactive;

import com.yuhubs.ms.auth.redis.ConfiguredTestBase;
import com.yuhubs.ms.redis.ReactiveRedisTemplateProvider;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class AuthUserServiceTest extends ConfiguredTestBase {

	@Autowired
	private ReactiveRedisTemplateProvider authReactiveRedisTemplateProvider;

	private RedisAuthUserService authUserService;


	@Before
	public void setUp() {
		authUserService = new RedisAuthUserService(authReactiveRedisTemplateProvider);
	}

	@Test
	public void test() {

	}

}
