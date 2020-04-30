package com.yuhubs.ms.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ResourceLoader;

@Configuration
public class RedisStandaloneConfig extends RedisConfigurationSupport {

	private static final String PROPERTIES_SOURCE_PATH = "classpath:redis.properties";

	@Autowired
	private ResourceLoader resourceLoader;


	@Bean
	public LettuceConnectionManager redisStandaloneConnectionManager() {
		return super.lettuceConnectionManagerBean();
	}

	@Bean
	public ReactiveRedisTemplateProvider standaloneReactiveRedisTemplateProvider(
			LettuceConnectionManager redisStandaloneConnectionManager) {
		return new ReactiveRedisTemplateProvider(redisStandaloneConnectionManager);
	}

	@Bean
	public RedisTemplateProvider standaloneRedisTemplateProvider(
			LettuceConnectionManager redisStandaloneConnectionManager) {
		return new RedisTemplateProvider(redisStandaloneConnectionManager);
	}


	@Override
	protected LettuceConfigProperties createLettuceConfigProperties() {
		LettuceConfigProperties config = new LettuceConfigProperties();

		try {
			config.load(resourceLoader, PROPERTIES_SOURCE_PATH);
		} catch (Exception e) {
			// TODO
		}

		return config;
	}

}
