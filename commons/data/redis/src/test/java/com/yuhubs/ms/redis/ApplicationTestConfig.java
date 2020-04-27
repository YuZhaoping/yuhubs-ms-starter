package com.yuhubs.ms.redis;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({
		RedisStandaloneConfig.class,
		RedisClusterConfig.class
})
public class ApplicationTestConfig {
}
