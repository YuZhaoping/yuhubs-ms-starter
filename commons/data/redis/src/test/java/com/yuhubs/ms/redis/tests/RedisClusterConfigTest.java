package com.yuhubs.ms.redis.tests;

import com.yuhubs.ms.redis.ConfiguredTestBase;
import com.yuhubs.ms.redis.ReactiveRedisTemplateProvider;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.assertNotNull;

public class RedisClusterConfigTest extends ConfiguredTestBase {

	@Autowired
	private ReactiveRedisTemplateProvider clusterRedisTemplateProvider;


	@Test
	public void test() {
		assertNotNull(clusterRedisTemplateProvider.createReactiveStringRedisTemplate());
		assertNotNull(clusterRedisTemplateProvider.createReactiveJsonRedisTemplate());
		assertNotNull(clusterRedisTemplateProvider.createReactiveJdkRedisTemplate());
	}

}
