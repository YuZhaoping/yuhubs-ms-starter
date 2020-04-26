package com.yuhubs.ms.redis;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;

public interface LettuceClientBuildTweaker {

	LettuceClientBuildTweaker DEFAULT = new LettuceClientBuildTweaker() {};


	default void tweakLettuceClientBuilder(LettuceClientConfiguration.LettuceClientConfigurationBuilder builder) {
	}

	default void tweakLettuceSslClientBuilder(LettuceClientConfiguration.LettuceSslClientConfigurationBuilder builder) {
	}

	default void tweakLettuceClientPoolConfig(GenericObjectPoolConfig poolConfig) {
	}

}
