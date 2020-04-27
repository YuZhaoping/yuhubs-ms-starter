package com.yuhubs.ms.redis;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.data.redis.connection.RedisClusterConfiguration;
import org.springframework.data.redis.connection.RedisNode;
import org.springframework.data.redis.connection.RedisPassword;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettucePoolingClientConfiguration;

import java.time.Duration;
import java.util.Arrays;
import java.util.Properties;

public final class LettuceConfigProperties extends RedisConfigProperties {

	public LettuceConfigProperties(Properties props) {
		super(props);
	}

	public LettuceConfigProperties() {
		super();
	}


	public boolean clusterConfigEnabled() {
		return this.props.keySet().stream().map(key -> key.toString())
				.anyMatch(key -> key.equals("cluster.nodes"));
	}

	public RedisClusterConfiguration buildClusterConfiguration() {
		RedisClusterConfiguration config = new RedisClusterConfiguration();

		this.props.keySet().stream().map(key -> key.toString())
				.filter(key -> isClusterProperty(key))
				.forEach(key -> doSetClusterConfig(config, key));

		return config;
	}

	private static boolean isClusterProperty(String key) {
		switch (key) {
			case "cluster.nodes":
			case "cluster.max-redirects":
			case "password":
				return true;
		}

		return false;
	}

	private void doSetClusterConfig(RedisClusterConfiguration config, String key) {
		String value = this.props.getProperty(key);

		switch (key) {
			case "cluster.nodes":
				setClusterNodes(config, value);
				break;
			case "cluster.max-redirects":
				config.setMaxRedirects(Integer.parseInt(value));
				break;
			case "password":
				config.setPassword(RedisPassword.of(value));
				break;
		}
	}

	private static void setClusterNodes(RedisClusterConfiguration config, String nodes) {
		Arrays.stream(nodes.split(","))
				.forEach(node -> addClusterNode(config, node.trim()));
	}

	private static void addClusterNode(RedisClusterConfiguration config, String node) {
		String[] hostPort = node.split(":");
		RedisNode redisNode = new RedisNode(hostPort[0].trim(), Integer.parseInt(hostPort[1].trim()));
		config.addClusterNode(redisNode);
	}


	public RedisStandaloneConfiguration buildStandaloneConfiguration() {
		RedisStandaloneConfiguration config = defaultStandaloneConfiguration();

		this.props.keySet().stream().map(key -> key.toString())
				.filter(key -> isStandaloneProperty(key))
				.forEach(key -> doSetStandaloneConfig(config, key));

		return config;
	}

	private RedisStandaloneConfiguration defaultStandaloneConfiguration() {
		RedisStandaloneConfiguration config = new RedisStandaloneConfiguration();

		config.setHostName("localhost");
		config.setPort(6379);
		config.setDatabase(0);

		return config;
	}

	private static boolean isStandaloneProperty(String key) {
		switch (key) {
			case "host":
			case "port":
			case "database":
			case "password":
				return true;
		}

		return false;
	}

	private void doSetStandaloneConfig(RedisStandaloneConfiguration config, String key) {
		String value = this.props.getProperty(key);

		switch (key) {
			case "host":
				config.setHostName(value);
				break;
			case "port":
				config.setPort(Integer.parseInt(value));
				break;
			case "database":
				config.setDatabase(Integer.parseInt(value));
				break;
			case "password":
				config.setPassword(RedisPassword.of(value));
				break;
		}
	}


	public LettuceClientConfiguration buildClientConfiguration(LettuceClientBuildTweaker tweaker) {
		if (poolConfigEnabled()) {
			return buildPoolingClientConfiguration(tweaker);
		}

		LettuceClientConfiguration.LettuceClientConfigurationBuilder builder =
				LettuceClientConfiguration.builder();

		configureClientBuilder(builder, tweaker);

		return builder.build();
	}

	public LettuceClientConfiguration buildClientConfiguration() {
		return buildClientConfiguration(LettuceClientBuildTweaker.DEFAULT);
	}

	private LettucePoolingClientConfiguration buildPoolingClientConfiguration(LettuceClientBuildTweaker tweaker) {
		LettucePoolingClientConfiguration.LettucePoolingClientConfigurationBuilder builder =
				LettucePoolingClientConfiguration.builder();

		GenericObjectPoolConfig poolConfig = buildPoolConfig();

		tweaker.tweakLettuceClientPoolConfig(poolConfig);
		builder.poolConfig(poolConfig);

		configureClientBuilder(builder, tweaker);

		return builder.build();
	}

	private void configureClientBuilder(LettuceClientConfiguration.LettuceClientConfigurationBuilder builder,
										LettuceClientBuildTweaker tweaker) {
		builder.shutdownTimeout(Duration.ofMillis(100));

		this.props.keySet().stream().map(key -> key.toString())
				.filter(key -> isClientProperty(key))
				.forEach(key -> doSetClientBuilder(builder, key, tweaker));

		tweaker.tweakLettuceClientBuilder(builder);
	}

	private static boolean isClientProperty(String key) {
		switch (key) {
			case "client-name":
			case "lettuce.shutdown-timeout":
			case "ssl":
				return true;
		}

		return false;
	}

	private void doSetClientBuilder(LettuceClientConfiguration.LettuceClientConfigurationBuilder builder,
									String key,
									LettuceClientBuildTweaker tweaker) {
		String value = this.props.getProperty(key);

		switch (key) {
			case "client-name":
				builder.clientName(value);
				break;
			case "lettuce.shutdown-timeout":
				builder.shutdownTimeout(parseDuration(value));
				break;
			case "ssl":
				if (Boolean.parseBoolean(value)) {
					tweaker.tweakLettuceSslClientBuilder(builder.useSsl());
				}
				break;
		}
	}


	public boolean poolConfigEnabled() {
		return this.props.keySet().stream().map(key -> key.toString())
				.anyMatch(key -> key.startsWith("lettuce.pool."));
	}

	private GenericObjectPoolConfig buildPoolConfig() {
		final GenericObjectPoolConfig poolConfig = defaultPoolConfig();

		this.props.keySet().stream().map(key -> key.toString())
				.filter(key -> key.startsWith("lettuce.pool."))
				.forEach(key -> doSetPoolConfig(poolConfig, key));

		return poolConfig;
	}

	private static GenericObjectPoolConfig defaultPoolConfig() {
		GenericObjectPoolConfig poolConfig = new GenericObjectPoolConfig();

		poolConfig.setMaxTotal(8);
		poolConfig.setMaxIdle(8);
		poolConfig.setMinIdle(0);
		poolConfig.setBlockWhenExhausted(true);

		return poolConfig;
	}

	private void doSetPoolConfig(GenericObjectPoolConfig poolConfig, String key) {
		String value = this.props.getProperty(key);

		key = key.substring("lettuce.pool.".length());

		switch (key) {
			case "max-active": poolConfig.setMaxTotal(Integer.parseInt(value)); break;
			case "max-idle": poolConfig.setMaxIdle(Integer.parseInt(value)); break;
			case "max-wait": {
				Duration duration = parseDuration(value);
				if (!duration.isNegative()) {
					poolConfig.setBlockWhenExhausted(false);
					poolConfig.setMaxWaitMillis(duration.toMillis());
				}
				break;
			}
			case "min-idle": poolConfig.setMinIdle(Integer.parseInt(value)); break;
			case "time-between-eviction-runs": {
				Duration duration = parseDuration(value);
				poolConfig.setTimeBetweenEvictionRunsMillis(duration.toMillis());
				break;
			}
		}
	}

}
