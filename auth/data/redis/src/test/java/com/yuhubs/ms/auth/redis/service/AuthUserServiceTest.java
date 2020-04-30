package com.yuhubs.ms.auth.redis.service;

import com.yuhubs.ms.auth.redis.ConfiguredTestBase;
import com.yuhubs.ms.redis.RedisTemplateProvider;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class AuthUserServiceTest extends ConfiguredTestBase {

	@Autowired
	private RedisTemplateProvider authRedisTemplateProvider;

	private RedisAuthUserService authUserService;


	@Before
	public void setUp() {
		authUserService = new RedisAuthUserService(authRedisTemplateProvider);
	}

	@Test
	public void test() {

	}

}
