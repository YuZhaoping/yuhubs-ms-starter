package com.yuhubs.ms.redis.tests;

import com.yuhubs.ms.redis.ConfiguredTestBase;
import com.yuhubs.ms.redis.ReactiveRedisTemplateProvider;
import com.yuhubs.ms.redis.RedisTemplateProvider;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.junit.Assert.*;

public class RedisStandaloneConfigTest extends ConfiguredTestBase {

	@Autowired
	private ReactiveRedisTemplateProvider standaloneReactiveRedisTemplateProvider;

	@Autowired
	private RedisTemplateProvider standaloneRedisTemplateProvider;


	@Test
	public void testStringRedisTemplate() {
		StringRedisTemplate redisTemplate =
				standaloneRedisTemplateProvider.createStringRedisTemplate();
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
				standaloneReactiveRedisTemplateProvider.createReactiveStringRedisTemplate();
		assertNotNull(reactiveRedisTemplate);

		final String key = "test-string:1";
		final String value = "test-string:1:value";

		StepVerifier.create(reactiveRedisTemplate.hasKey(key))
				.expectNext(false)
				.expectComplete().verify();

		ReactiveValueOperations<String, String> valueOps = reactiveRedisTemplate.opsForValue();

		StepVerifier.create(
				Flux.first(valueOps.setIfAbsent(key, value))
						.concatWith(reactiveRedisTemplate.hasKey(key))
		)
				.expectNext(true)
				.expectNext(true)
				.expectComplete().verify();

		StepVerifier.create(valueOps.get(key))
				.expectNext(value)
				.expectComplete().verify();

		StepVerifier.create(
				Flux.first(valueOps.setIfAbsent(key, value))
						.concatWith(reactiveRedisTemplate.delete(key).flatMap(c -> Mono.just(c > 0)))
						.concatWith(reactiveRedisTemplate.hasKey(key))
		)
				.expectNext(false)
				.expectNext(true)
				.expectNext(false)
				.expectComplete().verify();
	}

	@Test
	public void testJdkRedisTemplate() {
		RedisTemplate<String, Object> redisTemplate =
				standaloneRedisTemplateProvider.jdkRedisTemplateSupplier().get();
		assertNotNull(redisTemplate);

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
				standaloneReactiveRedisTemplateProvider.createReactiveJdkRedisTemplate();
		assertNotNull(reactiveRedisTemplate);

		final String key = "test-jdk:1";
		final RedisTestEntity value = new RedisTestEntity("test-jdk:1:value", 0);

		StepVerifier.create(reactiveRedisTemplate.hasKey(key))
				.expectNext(false)
				.expectComplete().verify();

		ReactiveValueOperations<String, Object> valueOps = reactiveRedisTemplate.opsForValue();

		StepVerifier.create(
				Flux.first(valueOps.setIfAbsent(key, value))
						.concatWith(reactiveRedisTemplate.hasKey(key))
		)
				.expectNext(true)
				.expectNext(true)
				.expectComplete().verify();

		StepVerifier.create(valueOps.get(key))
				.expectNext(value)
				.expectComplete().verify();

		StepVerifier.create(
				Flux.first(valueOps.setIfAbsent(key, value))
						.concatWith(reactiveRedisTemplate.delete(key).flatMap(c -> Mono.just(c > 0)))
						.concatWith(reactiveRedisTemplate.hasKey(key))
		)
				.expectNext(false)
				.expectNext(true)
				.expectNext(false)
				.expectComplete().verify();
	}

	@Test
	public void testJsonRedisTemplate() {
		RedisTemplate<String, Object> redisTemplate =
				standaloneRedisTemplateProvider.jsonRedisTemplateSupplier().get();
		assertNotNull(redisTemplate);

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
				standaloneReactiveRedisTemplateProvider.createReactiveJsonRedisTemplate();
		assertNotNull(reactiveRedisTemplate);

		final String key = "test-json:1";
		final RedisTestEntity value = new RedisTestEntity("test-json:1:value", 0);

		StepVerifier.create(reactiveRedisTemplate.hasKey(key))
				.expectNext(false)
				.expectComplete().verify();

		ReactiveValueOperations<String, Object> valueOps = reactiveRedisTemplate.opsForValue();

		StepVerifier.create(
				Flux.first(valueOps.setIfAbsent(key, value))
						.concatWith(reactiveRedisTemplate.hasKey(key))
		)
				.expectNext(true)
				.expectNext(true)
				.expectComplete().verify();

		StepVerifier.create(valueOps.get(key))
				.expectNext(value)
				.expectComplete().verify();

		StepVerifier.create(
				Flux.first(valueOps.setIfAbsent(key, value))
						.concatWith(reactiveRedisTemplate.delete(key).flatMap(c -> Mono.just(c > 0)))
						.concatWith(reactiveRedisTemplate.hasKey(key))
		)
				.expectNext(false)
				.expectNext(true)
				.expectNext(false)
				.expectComplete().verify();
	}

}
