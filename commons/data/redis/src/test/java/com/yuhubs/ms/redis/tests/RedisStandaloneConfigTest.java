package com.yuhubs.ms.redis.tests;

import com.yuhubs.ms.redis.ConfiguredTestBase;
import com.yuhubs.ms.redis.RedisTemplateSupplier;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.assertNotNull;

public class RedisStandaloneConfigTest extends ConfiguredTestBase {

	@Autowired
	private RedisTemplateSupplier standaloneRedisTemplateSupplier;


	@Test
	public void test() {
		assertNotNull(standaloneRedisTemplateSupplier.createStringRedisTemplate());
		assertNotNull(standaloneRedisTemplateSupplier.createJsonRedisTemplate());
		assertNotNull(standaloneRedisTemplateSupplier.createJdkRedisTemplate());
	}

}
