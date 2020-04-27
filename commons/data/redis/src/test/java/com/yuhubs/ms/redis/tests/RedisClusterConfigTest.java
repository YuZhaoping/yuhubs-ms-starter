package com.yuhubs.ms.redis.tests;

import com.yuhubs.ms.redis.ConfiguredTestBase;
import com.yuhubs.ms.redis.ReactiveRedisTemplateSupplier;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.assertNotNull;

public class RedisClusterConfigTest extends ConfiguredTestBase {

	@Autowired
	private ReactiveRedisTemplateSupplier clusterRedisTemplateSupplier;


	@Test
	public void test() {
		assertNotNull(clusterRedisTemplateSupplier.createReactiveStringRedisTemplate());
		assertNotNull(clusterRedisTemplateSupplier.createReactiveJsonRedisTemplate());
		assertNotNull(clusterRedisTemplateSupplier.createReactiveJdkRedisTemplate());
	}

}
