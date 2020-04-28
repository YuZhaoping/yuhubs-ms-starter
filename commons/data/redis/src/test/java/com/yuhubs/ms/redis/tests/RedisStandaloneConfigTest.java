package com.yuhubs.ms.redis.tests;

import com.yuhubs.ms.redis.ConfiguredTestBase;
import com.yuhubs.ms.redis.ReactiveRedisTemplateSupplier;
import com.yuhubs.ms.redis.RedisTemplateSupplier;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.*;

import static org.junit.Assert.*;

public class RedisStandaloneConfigTest extends ConfiguredTestBase {

	@Autowired
	private ReactiveRedisTemplateSupplier standaloneReactiveRedisTemplateSupplier;

	@Autowired
	private RedisTemplateSupplier standaloneRedisTemplateSupplier;


	@Test
	public void testStringRedisTemplate() {
		StringRedisTemplate redisTemplate =
				standaloneRedisTemplateSupplier.createStringRedisTemplate();
		assertNotNull(redisTemplate);

		final String key = "test-string:0";
		final String value = "test-string:0:value";

		assertFalse(redisTemplate.hasKey(key));

		ValueOperations<String, String> valueOps = redisTemplate.opsForValue();

		assertTrue(valueOps.setIfAbsent(key, value));

		assertTrue(redisTemplate.hasKey(key));

		assertEquals(value, valueOps.get(key));

		assertFalse(valueOps.setIfAbsent(key, value));

		assertTrue(redisTemplate.delete(key));

		assertFalse(redisTemplate.hasKey(key));
	}

	@Test
	public void testReactiveStringRedisTemplate() {
		ReactiveStringRedisTemplate reactiveRedisTemplate =
				standaloneReactiveRedisTemplateSupplier.createReactiveStringRedisTemplate();
		assertNotNull(reactiveRedisTemplate);

		final String key = "test-string:1";
		final String value = "test-string:1:value";

	}

	@Test
	public void testJdkRedisTemplate() {
		RedisTemplate<String, Object> redisTemplate =
				standaloneRedisTemplateSupplier.createJdkRedisTemplate();
		assertNotNull(redisTemplate);

		redisTemplate.afterPropertiesSet();

		final String key = "test-jdk:0";
		final RedisTestEntity value = new RedisTestEntity("test-jdk:0:value", 0);

		assertFalse(redisTemplate.hasKey(key));

		ValueOperations<String, Object> valueOps = redisTemplate.opsForValue();

		assertTrue(valueOps.setIfAbsent(key, value));

		assertTrue(redisTemplate.hasKey(key));

		assertEquals(value, valueOps.get(key));

		assertFalse(valueOps.setIfAbsent(key, value));

		assertTrue(redisTemplate.delete(key));

		assertFalse(redisTemplate.hasKey(key));
	}

	@Test
	public void testReactiveJdkRedisTemplate() {
		ReactiveRedisTemplate<String, Object> reactiveRedisTemplate =
				standaloneReactiveRedisTemplateSupplier.createReactiveJdkRedisTemplate();
		assertNotNull(reactiveRedisTemplate);

		final String key = "test-jdk:1";
		final RedisTestEntity value = new RedisTestEntity("test-jdk:1:value", 0);

	}

	@Test
	public void testJsonRedisTemplate() {
		RedisTemplate<String, Object> redisTemplate =
				standaloneRedisTemplateSupplier.createJsonRedisTemplate();
		assertNotNull(redisTemplate);

		redisTemplate.afterPropertiesSet();

		final String key = "test-json:0";
		final RedisTestEntity value = new RedisTestEntity("test-json:0:value", 0);

		assertFalse(redisTemplate.hasKey(key));

		ValueOperations<String, Object> valueOps = redisTemplate.opsForValue();

		assertTrue(valueOps.setIfAbsent(key, value));

		assertTrue(redisTemplate.hasKey(key));

		assertEquals(value, valueOps.get(key));

		assertFalse(valueOps.setIfAbsent(key, value));

		assertTrue(redisTemplate.delete(key));

		assertFalse(redisTemplate.hasKey(key));
	}

	@Test
	public void testReactiveJsonRedisTemplate() {
		ReactiveRedisTemplate<String, Object> reactiveRedisTemplate =
				standaloneReactiveRedisTemplateSupplier.createReactiveJsonRedisTemplate();
		assertNotNull(reactiveRedisTemplate);

		final String key = "test-json:1";
		final RedisTestEntity value = new RedisTestEntity("test-json:1:value", 0);

	}

}
