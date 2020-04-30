package com.yuhubs.ms.redis;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.util.Properties;

@Configuration
@PropertySource(value = {"classpath:redis-cluster.properties"})
@ConfigurationProperties(prefix = "spring")
public class RedisClusterConfig extends RedisConfigurationSupport {

	private Properties redis;


	public RedisClusterConfig() {
		this.redis = new Properties();
	}


	@Bean
	public LettuceConnectionManager redisClusterConnectionManager() {
		return super.lettuceConnectionManagerBean();
	}

	@Bean
	public ReactiveRedisTemplateProvider clusterRedisTemplateProvider(LettuceConnectionManager redisClusterConnectionManager) {
		return new ReactiveRedisTemplateProvider(redisClusterConnectionManager);
	}


	@Override
	protected LettuceConfigProperties createLettuceConfigProperties() {
		return new LettuceConfigProperties(this.redis);
	}


	public Properties getRedis() {
		return redis;
	}

	public void setRedis(Properties redis) {
		this.redis = redis;
	}

}
