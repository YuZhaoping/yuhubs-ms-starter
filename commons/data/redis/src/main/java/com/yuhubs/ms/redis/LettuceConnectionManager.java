package com.yuhubs.ms.redis;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory;
import org.springframework.data.redis.connection.RedisClusterConfiguration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;

import static com.yuhubs.ms.redis.RedisConnectionType.REDIS_CLUSTER_CONNECTION;
import static com.yuhubs.ms.redis.RedisConnectionType.REDIS_STANDALONE_CONNECTION;

public final class LettuceConnectionManager implements InitializingBean, DisposableBean {

	private volatile LettuceConnectionFactory lettuceFactory;
	private volatile RedisConnectionType connectionType;
	private final Object lock;

	private final LettuceConfigProperties config;

	private final LettuceClientBuildTweaker tweaker;


	public LettuceConnectionManager(LettuceConfigProperties config, LettuceClientBuildTweaker tweaker) {
		this.lock = new Object();
		this.connectionType = REDIS_STANDALONE_CONNECTION;

		this.config = config;
		this.tweaker = tweaker;
	}

	public LettuceConnectionManager(LettuceConfigProperties config) {
		this(config, LettuceClientBuildTweaker.DEFAULT);
	}


	@Override
	public void afterPropertiesSet() throws Exception {
		LettuceConnectionFactory factory = getLettuceFactory();
		factory.afterPropertiesSet();
	}

	@Override
	public void destroy() throws Exception {
		LettuceConnectionFactory factory = getLettuceFactory();
		factory.destroy();
	}


	public RedisConnectionFactory getConnectionFactory() {
		LettuceConnectionFactory factory = getLettuceFactory();
		return factory;
	}

	public ReactiveRedisConnectionFactory getReactiveConnectionFactory() {
		LettuceConnectionFactory factory = getLettuceFactory();
		return factory;
	}

	public RedisConnectionType getConnectionType() {
		LettuceConnectionFactory factory = getLettuceFactory();
		return this.connectionType;
	}


	private LettuceConnectionFactory createLettuceFactory() {
		LettuceClientConfiguration clientConfig = this.config.buildClientConfiguration(this.tweaker);

		if (this.config.clusterConfigEnabled()) {
			RedisClusterConfiguration clusterConfig = this.config.buildClusterConfiguration();

			this.connectionType = REDIS_CLUSTER_CONNECTION;

			return new LettuceConnectionFactory(clusterConfig, clientConfig);
		} else {
			RedisStandaloneConfiguration standaloneConfig = this.config.buildStandaloneConfiguration();

			return new LettuceConnectionFactory(standaloneConfig, clientConfig);
		}
	}

	private LettuceConnectionFactory getLettuceFactory() {
		LettuceConnectionFactory factory = this.lettuceFactory;
		if (factory == null) {
			synchronized (this.lock) {
				factory = this.lettuceFactory;
				if (factory == null) {
					factory = createLettuceFactory();
					this.lettuceFactory = factory;
				}
			}
		}
		return factory;
	}

}
